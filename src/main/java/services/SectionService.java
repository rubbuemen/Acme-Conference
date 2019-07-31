
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Actor;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	// Managed repository
	@Autowired
	private SectionRepository	sectionRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private TutorialService		tutorialService;


	// Simple CRUD methods
	//R14.6
	public Section create() {
		Section result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Section();

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Section findOne(final int sectionId) {
		Assert.isTrue(sectionId != 0);

		Section result;

		result = this.sectionRepository.findOne(sectionId);
		Assert.notNull(result);

		return result;
	}

	//R14.6
	public Section save(final Section section, final Tutorial tutorial) {
		Assert.notNull(section);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Section result;

		if (section.getId() == 0) {
			result = this.sectionRepository.save(section);
			tutorial.getSections().add(result);
			this.tutorialService.saveAuxiliar(tutorial);
		} else
			result = this.sectionRepository.save(section);

		return result;
	}

	//R14.6
	public void delete(final Section section, final Tutorial tutorial) {
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);
		Assert.isTrue(this.sectionRepository.exists(section.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Section> sectionsTutorial = tutorial.getSections();
		Assert.isTrue(sectionsTutorial.size() != 1, "The tutorial must be at least one section");

		sectionsTutorial.remove(section);
		tutorial.setSections(sectionsTutorial);
		this.tutorialService.saveAuxiliar(tutorial);

		this.sectionRepository.delete(section);
	}

	// Other business methods
	public Collection<Section> findSectionsByTutorial(final int tutorialId) {
		Collection<Section> result;

		result = this.sectionRepository.findSectionsByTutorialId(tutorialId);

		return result;
	}
}
