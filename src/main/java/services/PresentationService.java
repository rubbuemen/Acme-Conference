
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.Actor;
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


	// Simple CRUD methods
	public Presentation create() {
		Presentation result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

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

	public Presentation save(final Presentation presentation) {
		Assert.notNull(presentation);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Presentation result;

		if (presentation.getId() == 0)
			result = this.presentationRepository.save(presentation);
		else
			result = this.presentationRepository.save(presentation);

		return result;
	}

	public void delete(final Presentation presentation) {
		Assert.notNull(presentation);
		Assert.isTrue(presentation.getId() != 0);
		Assert.isTrue(this.presentationRepository.exists(presentation.getId()));

		this.presentationRepository.delete(presentation);
	}

	// Other business methods

}
