
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository
	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Category create() {
		Category result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Category();

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Category findOne(final int categoryId) {
		Assert.isTrue(categoryId != 0);

		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Category result;

		if (category.getId() == 0)
			result = this.categoryRepository.save(category);
		else
			result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));

		this.categoryRepository.delete(category);
	}

	// Other business methods

}
