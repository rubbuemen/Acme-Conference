
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Actor;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private SponsorService			sponsorService;


	// Simple CRUD methods
	//R30.1
	public Sponsorship create() {
		Sponsorship result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginSponsor(actorLogged);

		result = new Sponsorship();

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

	//R30.1
	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginSponsor(actorLogged);

		final Sponsor sponsorLogged = (Sponsor) actorLogged;

		Sponsorship result;

		if (!sponsorship.getCreditCard().getNumber().isEmpty())
			Assert.isTrue(this.isNumeric(sponsorship.getCreditCard().getNumber()), "Invalid credit card");
		if (sponsorship.getCreditCard().getExpirationYear() != null && sponsorship.getCreditCard().getExpirationMonth() != null && sponsorship.getCreditCard().getExpirationYear() >= 0)
			Assert.isTrue(this.checkCreditCard(sponsorship.getCreditCard()), "Expired credit card");

		if (sponsorship.getId() == 0) {
			result = this.sponsorshipRepository.save(sponsorship);
			final Collection<Sponsorship> sponsorshipsSponsor = sponsorLogged.getSponsorships();
			sponsorshipsSponsor.add(result);
			sponsorLogged.setSponsorships(sponsorshipsSponsor);
			this.sponsorService.save(sponsorLogged);
		} else {
			final Sponsor sponsorOwner = this.sponsorService.findSponsorBySponsorshipId(sponsorship.getId());
			Assert.isTrue(actorLogged.equals(sponsorOwner), "The logged actor is not the owner of this entity");
			result = this.sponsorshipRepository.save(sponsorship);
		}

		return result;
	}

	//R30.1
	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginSponsor(actorLogged);

		final Sponsor sponsorLogged = (Sponsor) actorLogged;

		final Sponsor sponsorOwner = this.sponsorService.findSponsorBySponsorshipId(sponsorship.getId());
		Assert.isTrue(actorLogged.equals(sponsorOwner), "The logged actor is not the owner of this entity");

		final Collection<Sponsorship> sponsorshipsSponsor = sponsorLogged.getSponsorships();
		sponsorshipsSponsor.remove(sponsorship);
		sponsorLogged.setSponsorships(sponsorshipsSponsor);
		this.sponsorService.save(sponsorLogged);

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods
	//R28
	public Sponsorship findRandomSponsorship() {
		Sponsorship result = null;
		final Random r = new Random();
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAll();
		if (!sponsorships.isEmpty()) {
			final int i = r.nextInt(sponsorships.size());
			result = (Sponsorship) sponsorships.toArray()[i];
		}
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

	//R30.1
	public Collection<Sponsorship> findSponsorshipsBySponsorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginSponsor(actorLogged);

		Collection<Sponsorship> result;

		final Sponsor sponsorLogged = (Sponsor) actorLogged;

		result = sponsorLogged.getSponsorships();
		Assert.notNull(result);

		return result;
	}

	public Sponsorship findSponsorshipSponsorLogged(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginSponsor(actorLogged);

		final Sponsor sponsorOwner = this.sponsorService.findSponsorBySponsorshipId(sponsorshipId);
		Assert.isTrue(actorLogged.equals(sponsorOwner), "The logged actor is not the owner of this entity");

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(result);

		return result;
	}

}
