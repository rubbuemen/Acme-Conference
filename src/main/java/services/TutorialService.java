
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.Actor;
import domain.Conference;
import domain.Section;
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

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private SectionService		sectionService;


	// Simple CRUD methods
	//R14.6
	public Tutorial create() {
		Tutorial result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Tutorial();
		final Collection<Section> sections = new HashSet<>();
		result.setSections(sections);

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

	//R14.6
	public Tutorial save(final Tutorial tutorial, final Conference conference, Section section) {
		Assert.notNull(tutorial);

		Tutorial result;

		if (tutorial.getId() == 0) {
			section = this.sectionService.save(section, tutorial);
			tutorial.getSections().add(section);
		}
		result = (Tutorial) this.activityService.save(tutorial, conference);
		result = this.tutorialRepository.save(result);

		return result;
	}

	public Tutorial saveAuxiliar(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Tutorial result;

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
	//R14.6
	public Collection<Tutorial> findTutorialsByConference(final Conference conference) {
		Collection<Tutorial> result;

		Assert.isTrue(conference.getIsFinalMode(), "Activities can only be managed if the conference is in final mode");

		result = this.tutorialRepository.findTutorialsByConferenceId(conference.getId());

		return result;

	}

}
