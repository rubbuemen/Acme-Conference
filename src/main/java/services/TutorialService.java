
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Actor;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository
	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Tutorial create() {
		Tutorial result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Tutorial();

		return result;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tutorial findOne(final int tutorialId) {
		Assert.isTrue(tutorialId != 0);

		Tutorial result;

		result = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(result);

		return result;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Tutorial result;

		if (tutorial.getId() == 0)
			result = this.tutorialRepository.save(tutorial);
		else
			result = this.tutorialRepository.save(tutorial);

		return result;
	}

	public void delete(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		Assert.isTrue(this.tutorialRepository.exists(tutorial.getId()));

		this.tutorialRepository.delete(tutorial);
	}

	// Other business methods

}
