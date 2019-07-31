
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Commentable;

@Service
@Transactional
public class CommentService {

	// Managed repository
	@Autowired
	private CommentRepository	commentRepository;

	// Supporting services
	@Autowired
	private CommentableService	commentableService;


	// Simple CRUD methods
	//R22.2
	public Comment create() {
		Comment result;

		result = new Comment();
		final Date moment = new Date(System.currentTimeMillis() - 1);
		result.setMoment(moment);

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Comment findOne(final int commentId) {
		Assert.isTrue(commentId != 0);

		Comment result;

		result = this.commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	//R22.2
	public Comment save(final Comment comment, final Commentable commentable) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() == 0); //Un comentario unicamente se creará, nunca se editará

		Comment result;

		result = this.commentRepository.save(comment);
		commentable.getComments().add(result);
		this.commentableService.save(commentable);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);
		Assert.isTrue(this.commentRepository.exists(comment.getId()));

		this.commentRepository.delete(comment);
	}

	// Other business methods
	public Collection<Comment> findCommentsByCommentable(final Commentable commentable) {
		Collection<Comment> result;

		result = this.commentRepository.findCommentsByCommentableId(commentable.getId());

		return result;
	}

}
