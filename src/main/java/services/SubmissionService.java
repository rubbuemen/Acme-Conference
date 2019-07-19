
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Actor;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	// Managed repository
	@Autowired
	private SubmissionRepository	submissionRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods
	public Submission create() {
		Submission result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Submission();

		return result;
	}

	public Collection<Submission> findAll() {
		Collection<Submission> result;

		result = this.submissionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Submission findOne(final int submissionId) {
		Assert.isTrue(submissionId != 0);

		Submission result;

		result = this.submissionRepository.findOne(submissionId);
		Assert.notNull(result);

		return result;
	}

	public Submission save(final Submission submission) {
		Assert.notNull(submission);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Submission result;

		if (submission.getId() == 0)
			result = this.submissionRepository.save(submission);
		else
			result = this.submissionRepository.save(submission);

		return result;
	}

	public void delete(final Submission submission) {
		Assert.notNull(submission);
		Assert.isTrue(submission.getId() != 0);
		Assert.isTrue(this.submissionRepository.exists(submission.getId()));

		this.submissionRepository.delete(submission);
	}

	// Other business methods

}
