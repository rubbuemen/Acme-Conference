
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import domain.Actor;
import domain.Reviewer;

@Service
@Transactional
public class ReviewerService {

	// Managed repository
	@Autowired
	private ReviewerRepository	reviewerRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Reviewer create() {
		Reviewer result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Reviewer();

		return result;
	}

	public Collection<Reviewer> findAll() {
		Collection<Reviewer> result;

		result = this.reviewerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Reviewer findOne(final int reviewerId) {
		Assert.isTrue(reviewerId != 0);

		Reviewer result;

		result = this.reviewerRepository.findOne(reviewerId);
		Assert.notNull(result);

		return result;
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Reviewer result;

		if (reviewer.getId() == 0)
			result = this.reviewerRepository.save(reviewer);
		else
			result = this.reviewerRepository.save(reviewer);

		return result;
	}

	public void delete(final Reviewer reviewer) {
		Assert.notNull(reviewer);
		Assert.isTrue(reviewer.getId() != 0);
		Assert.isTrue(this.reviewerRepository.exists(reviewer.getId()));

		this.reviewerRepository.delete(reviewer);
	}

	// Other business methods

}
