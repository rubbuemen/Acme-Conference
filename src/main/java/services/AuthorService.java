
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import domain.Actor;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	// Managed repository
	@Autowired
	private AuthorRepository	authorRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Author create() {
		Author result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Author();

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

	public Author save(final Author author) {
		Assert.notNull(author);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Author result;

		if (author.getId() == 0)
			result = this.authorRepository.save(author);
		else
			result = this.authorRepository.save(author);

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
