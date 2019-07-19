
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Actor;
import domain.Section;

@Service
@Transactional
public class SectionService {

	// Managed repository
	@Autowired
	private SectionRepository	sectionRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Section create() {
		Section result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

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

	public Section save(final Section section) {
		Assert.notNull(section);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Section result;

		if (section.getId() == 0)
			result = this.sectionRepository.save(section);
		else
			result = this.sectionRepository.save(section);

		return result;
	}

	public void delete(final Section section) {
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);
		Assert.isTrue(this.sectionRepository.exists(section.getId()));

		this.sectionRepository.delete(section);
	}

	// Other business methods

}
