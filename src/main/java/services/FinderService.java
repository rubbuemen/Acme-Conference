
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Author;
import domain.Category;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed repository
	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConferenceService	conferenceService;


	// Simple CRUD methods
	public Finder create() {
		Finder result;

		result = new Finder();
		final Collection<Conference> conferences = new HashSet<>();

		result.setConferences(conferences);

		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Assert.isTrue(finderId != 0);

		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		Finder result;

		result = this.finderRepository.save(finder);

		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	// Other business methods
	public Finder findFinderAuthorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		final Author authorLogged = (Author) actorLogged;

		Finder result;

		result = authorLogged.getFinder();

		return result;
	}

	//R24.1
	public Finder cleanFinder(final Finder finder) {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		Finder result;

		final Collection<Conference> conferences = this.conferenceService.findConferencesFinalMode();
		finder.setKeyWord("");
		finder.setMinDate(null);
		finder.setMaxDate(null);
		finder.setMaxFee(null);
		finder.setCategory(null);

		finder.setConferences(conferences);
		result = this.finderRepository.save(finder);

		return result;
	}

	//R24.1
	public void updateCriteria(String keyWord, Date minDate, Date maxDate, Double maxFee, final Category category) {
		Finder result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		final Author author = (Author) actorLogged;

		result = author.getFinder();

		final Calendar cal = Calendar.getInstance();

		if (keyWord.isEmpty() && minDate == null && maxDate == null && maxFee == null && category == null)
			result = this.cleanFinder(result);
		else {
			keyWord = keyWord.toLowerCase();
			result.setKeyWord(keyWord);

			if (minDate == null) {
				cal.set(1000, 0, 1);
				minDate = cal.getTime();
			}
			result.setMinDate(minDate);

			if (maxDate == null) {
				cal.set(3000, 0, 1);
				maxDate = cal.getTime();
			}
			result.setMaxDate(maxDate);

			if (maxFee == null)
				maxFee = 0.0;
			result.setMaxFee(maxFee);

			int categoryId = 0;
			if (category == null)
				result.setCategory(null);
			else {
				result.setCategory(category);
				categoryId = category.getId();
			}

			final Collection<Conference> conferencesFinder = this.conferenceService.findConferencesFromFinder(keyWord, minDate, maxDate, maxFee, categoryId);
			result.setConferences(conferencesFinder);

			cal.setTime(result.getMinDate());
			if (cal.get(Calendar.YEAR) == 1000)
				result.setMinDate(null);
			cal.setTime(result.getMaxDate());
			if (cal.get(Calendar.YEAR) == 3000)
				result.setMaxDate(null);
			if (result.getMaxFee() == 0.0)
				result.setMaxFee(null);
		}

		result = this.finderRepository.save(result);
	}

	public Collection<Finder> findFindersByCategory(final Category category) {
		Collection<Finder> result;

		result = this.finderRepository.findFindersByCategoryId(category.getId());

		return result;
	}

}
