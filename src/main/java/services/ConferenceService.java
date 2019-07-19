
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

	public void delete(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		this.conferenceRepository.delete(conference);
	}

	// Other business methods

}
