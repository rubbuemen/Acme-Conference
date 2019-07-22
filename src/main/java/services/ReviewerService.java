
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import security.Authority;
import security.UserAccount;
import domain.Message;
import domain.Report;
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

	@Autowired
	private UserAccountService	userAccountService;


	// Simple CRUD methods
	//R11.6
	public Reviewer create() {
		Reviewer result;

		result = new Reviewer();
		final Collection<Message> messages = new HashSet<>();
		final Collection<Report> reports = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.REVIEWER);
		userAccount.addAuthority(auth);
		result.setMessages(messages);
		result.setReports(reports);
		result.setUserAccount(userAccount);

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

	//R11.6
	public Reviewer save(final Reviewer reviewer) {
		Assert.notNull(reviewer);

		Reviewer result;

		result = (Reviewer) this.actorService.save(reviewer);
		result = this.reviewerRepository.save(result);

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
