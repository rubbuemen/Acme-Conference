
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Actor;
import domain.Category;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class CategoryService {

	// Managed repository
	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private FinderService		finderService;


	// Simple CRUD methods
	//R25.1
	public Category create() {
		Category result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Category();
		final Collection<Category> childCategories = new HashSet<>();
		result.setChildCategories(childCategories);

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

	//R25.1
	public Category save(final Category category, final Category oldParentCategory) {
		Assert.notNull(category);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Category result;

		Assert.isTrue(category.getParentCategory() != null, "The root category cannot be updated or deleted");

		if (category.getId() == 0)
			result = this.categoryRepository.save(category);
		else {
			if (category.getParentCategory() != oldParentCategory) {
				final Collection<Category> childOldParentCategories = oldParentCategory.getChildCategories();
				childOldParentCategories.remove(category);
				oldParentCategory.setChildCategories(childOldParentCategories);
				this.categoryRepository.save(oldParentCategory);
			}
			result = this.categoryRepository.save(category);
		}

		return result;
	}

	//R25.1
	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(category.getParentCategory() != null, "The root category cannot be updated or deleted");

		Category parentCategory = category.getParentCategory();
		parentCategory.getChildCategories().remove(category);

		if (!category.getChildCategories().isEmpty())
			for (final Category c : category.getChildCategories()) {
				c.setParentCategory(parentCategory);
				this.categoryRepository.save(c);
				parentCategory.getChildCategories().add(c);
			}

		parentCategory = this.categoryRepository.save(parentCategory);

		final Collection<Conference> conferencesCategory = this.conferenceService.findConferencesByCategory(category);
		final Collection<Finder> findersByCategory = this.finderService.findFindersByCategory(category);
		for (final Conference c : conferencesCategory) {
			c.setCategory(parentCategory);
			this.conferenceService.saveAuxiliar(c);
		}
		for (final Finder f : findersByCategory) {
			f.setCategory(parentCategory);
			this.finderService.save(f);
		}

		this.categoryRepository.delete(category);
	}

	// Other business methods
	public Collection<Category> findRootCategory() {
		Collection<Category> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.categoryRepository.findRootCategory();

		return result;
	}

	public Category findCategoryAdministratorLogged(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		Assert.isTrue(result.getParentCategory() != null, "The root category cannot be updated or deleted");

		return result;
	}

	public Collection<Category> findCategoriesToDelete(final int categoryId, final Collection<Category> ac) {

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Category categoryToRemove = this.categoryRepository.findOne(categoryId);
		final Collection<Category> childCategoriesToRemove = categoryToRemove.getChildCategories();

		for (final Category c : childCategoriesToRemove) {
			this.findCategoriesToDelete(c.getId(), ac);
			ac.add(c);
		}

		return ac;
	}

}
