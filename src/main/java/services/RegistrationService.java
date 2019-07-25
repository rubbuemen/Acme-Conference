
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationRepository;
import domain.Actor;
import domain.Author;
import domain.Conference;
import domain.CreditCard;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	// Managed repository
	@Autowired
	private RegistrationRepository	registrationRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ConferenceService		conferenceService;


	// Simple CRUD methods
	// R13.3
	public Registration create() {
		Registration result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = new Registration();

		result.setAuthor((Author) actorLogged);

		return result;
	}

	public Collection<Registration> findAll() {
		Collection<Registration> result;

		result = this.registrationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Registration findOne(final int registrationId) {
		Assert.isTrue(registrationId != 0);

		Registration result;

		result = this.registrationRepository.findOne(registrationId);
		Assert.notNull(result);

		return result;
	}

	//R13.3
	public Registration save(final Registration registration) {
		Assert.notNull(registration);
		Assert.isTrue(registration.getId() == 0); //Sólo se crearán, no se pueden editar

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		final Author authorLogged = (Author) actorLogged;

		Registration result;

		if (!registration.getCreditCard().getNumber().isEmpty())
			Assert.isTrue(this.isNumeric(registration.getCreditCard().getNumber()), "Invalid credit card");
		if (registration.getCreditCard().getExpirationYear() != null && registration.getCreditCard().getExpirationMonth() != null && registration.getCreditCard().getExpirationYear() >= 0)
			Assert.isTrue(this.checkCreditCard(registration.getCreditCard()), "Expired credit card");

		Assert.isTrue(registration.getConference().getIsFinalMode(), "To register for a conference, it must be in final mode");
		Assert.isTrue(registration.getConference().getStartDate().compareTo(new Date(System.currentTimeMillis())) >= 0, "You can't register for this conference because it's already started");
		for (final Registration r : authorLogged.getRegistrations())
			Assert.isTrue(!r.getConference().equals(registration.getConference()), "You cannot register for a conference in which you are already registered");

		result = this.registrationRepository.save(registration);
		final Conference conferenceRegistration = result.getConference();
		final Collection<Registration> registrationsConference = conferenceRegistration.getRegistrations();
		registrationsConference.add(result);
		conferenceRegistration.setRegistrations(registrationsConference);
		this.conferenceService.saveAuxiliar(conferenceRegistration);
		final Collection<Registration> registrationsAuthor = authorLogged.getRegistrations();
		registrationsAuthor.add(result);
		authorLogged.setRegistrations(registrationsAuthor);
		this.authorService.save(authorLogged);

		return result;
	}

	public void delete(final Registration registration) {
		Assert.notNull(registration);
		Assert.isTrue(registration.getId() != 0);
		Assert.isTrue(this.registrationRepository.exists(registration.getId()));

		this.registrationRepository.delete(registration);
	}

	// Other business methods
	// R13.3 
	public Collection<Registration> findRegistrationsByAuthorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		Collection<Registration> result;

		final Author authorLogged = (Author) actorLogged;

		result = this.registrationRepository.findRegistrationsByAuthorId(authorLogged.getId());
		Assert.notNull(result);

		return result;
	}

	public Registration findRegistrationAuthorLogged(final int registrationId) {
		Assert.isTrue(registrationId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		final Author authorOwner = this.authorService.findAuthorByRegistrationId(registrationId);
		Assert.isTrue(actorLogged.equals(authorOwner), "The logged actor is not the owner of this entity");

		Registration result;

		result = this.registrationRepository.findOne(registrationId);
		Assert.notNull(result);

		return result;
	}

	public boolean isNumeric(final String cadena) {

		boolean resultado;

		try {
			Long.parseLong(cadena);
			resultado = true;
		} catch (final NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}

	public boolean checkCreditCard(final CreditCard creditCard) {
		boolean result;
		Calendar calendar;
		int actualYear, actualMonth;

		result = false;
		calendar = new GregorianCalendar();
		actualYear = calendar.get(Calendar.YEAR);
		actualMonth = calendar.get(Calendar.MONTH) + 1;
		actualYear = actualYear % 100;
		if (creditCard.getExpirationYear() > actualYear)
			result = true;
		else if (creditCard.getExpirationYear() == actualYear && creditCard.getExpirationMonth() >= actualMonth)
			result = true;
		return result;
	}

}
