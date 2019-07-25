
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PaperRepository;
import domain.Actor;
import domain.Paper;

@Service
@Transactional
public class PaperService {

	// Managed repository
	@Autowired
	private PaperRepository	paperRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods
	//R11.5
	public Paper create() {
		Paper result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = new Paper();
		result.setIsCameraReadyVersion(false);

		return result;
	}

	public Collection<Paper> findAll() {
		Collection<Paper> result;

		result = this.paperRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Paper findOne(final int paperId) {
		Assert.isTrue(paperId != 0);

		Paper result;

		result = this.paperRepository.findOne(paperId);
		Assert.notNull(result);

		return result;
	}

	public Paper save(final Paper paper) {
		Assert.notNull(paper);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		Paper result;

		result = this.paperRepository.save(paper);

		return result;
	}

	public void delete(final Paper paper) {
		Assert.notNull(paper);
		Assert.isTrue(paper.getId() != 0);
		Assert.isTrue(this.paperRepository.exists(paper.getId()));

		this.paperRepository.delete(paper);
	}

	// Other business methods

}
