
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Actor;
import domain.Conference;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	// Managed repository
	@Autowired
	private PresentationRepository	presentationRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private ActivityService			activityService;


	// Simple CRUD methods
	public Presentation create() {
		Presentation result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Presentation();

		return result;
	}

	public Collection<Presentation> findAll() {
		Collection<Presentation> result;

		result = this.presentationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Presentation findOne(final int presentationId) {
		Assert.isTrue(presentationId != 0);

		Presentation result;

		result = this.presentationRepository.findOne(presentationId);
		Assert.notNull(result);

		return result;
	}

	//R14.6
	public Presentation save(final Presentation presentation, final Conference conference) {
		Assert.notNull(presentation);

		Presentation result;

		result = (Presentation) this.activityService.save(presentation, conference);
		result = this.presentationRepository.save(result);

		return result;
	}

	public void delete(final Presentation presentation) {
		Assert.notNull(presentation);
		Assert.isTrue(presentation.getId() != 0);
		Assert.isTrue(this.presentationRepository.exists(presentation.getId()));

		this.presentationRepository.delete(presentation);
	}

	// Other business methods
	//R14.6
	public Collection<Presentation> findPresentationsByConference(final Conference conference) {
		Collection<Presentation> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(conference.getIsFinalMode(), "Activities can only be managed if the conference is in final mode");

		result = this.presentationRepository.findPresentationsByConferenceId(conference.getId());

		return result;

	}
}
