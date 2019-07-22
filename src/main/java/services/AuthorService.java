
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.Authority;
import security.UserAccount;
import domain.Author;
import domain.Finder;
import domain.Message;
import domain.Registration;
import domain.Submission;

@Service
@Transactional
public class AuthorService {

	// Managed repository
	@Autowired
	private AuthorRepository	authorRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private FinderService		finderService;


	// Simple CRUD methods
	//R11.5
	public Author create() {
		Author result;

		result = new Author();
		final Collection<Message> messages = new HashSet<>();
		final Collection<Submission> submissions = new HashSet<>();
		final Collection<Registration> registrations = new HashSet<>();
		final Finder finder = this.finderService.create();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.AUTHOR);
		userAccount.addAuthority(auth);
		result.setMessages(messages);
		result.setSubmissions(submissions);
		result.setRegistrations(registrations);
		result.setFinder(finder);
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Author> findAll() {
		Collection<Author> result;

		result = this.authorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Author findOne(final int authorId) {
		Assert.isTrue(authorId != 0);

		Author result;

		result = this.authorRepository.findOne(authorId);
		Assert.notNull(result);

		return result;
	}

	//R11.5
	public Author save(final Author author) {
		Assert.notNull(author);

		Author result;

		if (author.getId() == 0) {
			final Finder finder = this.finderService.save(author.getFinder());
			author.setFinder(finder);
		}

		result = (Author) this.actorService.save(author);
		result = this.authorRepository.save(result);

		return result;
	}

	public void delete(final Author author) {
		Assert.notNull(author);
		Assert.isTrue(author.getId() != 0);
		Assert.isTrue(this.authorRepository.exists(author.getId()));

		this.authorRepository.delete(author);
	}

	// Other business methods

}
