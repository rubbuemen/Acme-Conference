
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Actor;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	// Managed repository
	@Autowired
	private ConferenceRepository	conferenceRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods
	public Conference create() {
		Conference result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Conference();

		return result;
	}

	public Collection<Conference> findAll() {
		Collection<Conference> result;

		result = this.conferenceRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Conference findOne(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);

		Conference result;

		result = this.conferenceRepository.findOne(conferenceId);
		Assert.notNull(result);

		return result;
	}

	public Conference save(final Conference conference) {
		Assert.notNull(conference);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Conference result;

		if (conference.getId() == 0)
			result = this.conferenceRepository.save(conference);
		else
			result = this.conferenceRepository.save(conference);

		return result;
	}

	public Conference saveAuxiliar(final Conference conference) {
		Assert.notNull(conference);

		Conference result;
		result = this.conferenceRepository.save(conference);

		return result;
	}

	public void delete(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		this.conferenceRepository.delete(conference);
	}

	// Other business methods
	//R11.1
	public Collection<Conference> findForthcomingConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findForthcomingConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.2
	public Collection<Conference> findPastConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findPastConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.3
	public Collection<Conference> findRunningConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findRunningConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.4
	public Collection<Conference> findConferencesFinalModeBySingleKeyWord(final String singleKeyWord) {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesFinalModeBySingleKeyWord(singleKeyWord);
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesToSubmit() {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesToSubmit();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesFinalModeNotStartDateDeadlineNotRegistrated() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = this.conferenceRepository.findConferencesFinalModeNotStartDateDeadlineNotRegistrated();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesRegistratedByAuthorId() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = this.conferenceRepository.findConferencesRegistratedByAuthorId(actorLogged.getId());
		Assert.notNull(result);

		return result;
	}

}
