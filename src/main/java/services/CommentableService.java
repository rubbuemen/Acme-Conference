
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentableRepository;
import domain.Commentable;

@Service
@Transactional
public class CommentableService {

	// Managed repository
	@Autowired
	private CommentableRepository	commentableRepository;


	// Supporting services

	// Simple CRUD methods
	public Collection<Commentable> findAll() {
		Collection<Commentable> result;

		result = this.commentableRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Commentable findOne(final int commentableId) {
		Assert.isTrue(commentableId != 0);

		Commentable result;

		result = this.commentableRepository.findOne(commentableId);
		Assert.notNull(result);

		return result;
	}

	public Commentable save(final Commentable commentable) {
		Assert.notNull(commentable);

		Commentable result;

		result = this.commentableRepository.save(commentable);

		return result;
	}

	public void delete(final Commentable commentable) {
		Assert.notNull(commentable);
		Assert.isTrue(commentable.getId() != 0);
		Assert.isTrue(this.commentableRepository.exists(commentable.getId()));

		this.commentableRepository.delete(commentable);
	}

	// Other business methods

}
