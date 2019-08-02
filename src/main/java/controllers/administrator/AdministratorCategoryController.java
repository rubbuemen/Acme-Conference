/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class AdministratorCategoryController extends AbstractController {

	@Autowired
	CategoryService	categoryService;

	@Autowired
	ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String parentCategoryId) {
		ModelAndView result;
		Collection<Category> categories = null;
		Category parentCategory = null;

		try {
			final String language = LocaleContextHolder.getLocale().getLanguage();
			if (parentCategoryId == null)
				categories = this.categoryService.findRootCategory();
			else {
				parentCategory = this.categoryService.findOne(Integer.valueOf(parentCategoryId));
				categories = parentCategory.getChildCategories();
			}
			result = new ModelAndView("category/list");
			result.addObject("requestURI", "category/administrator/list.do");
			result.addObject("categories", categories);
			result.addObject("parentCategory", parentCategory);
			result.addObject("language", language);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(null, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createCategory() {
		ModelAndView result;
		Category category;

		category = this.categoryService.create();

		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category = null;

		try {
			category = this.categoryService.findCategoryAdministratorLogged(categoryId);
			result = this.createEditModelAndView(category);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The root category cannot be updated or deleted"))
				result = this.createEditModelAndView(category, "category.error.rootCategory");
			else
				result = this.createEditModelAndView(category, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Category category, final BindingResult binding, @RequestParam(required = false) final String categoryParentOld) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				Category oldParentCategory = null;
				if (!categoryParentOld.isEmpty())
					oldParentCategory = this.categoryService.findOne(Integer.valueOf(categoryParentOld));
				this.categoryService.save(category, oldParentCategory);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("The root category cannot be updated or deleted"))
					result = this.createEditModelAndView(category, "category.error.rootCategory");
				else if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(category, "category.error.duplicate.title");
				else
					result = this.createEditModelAndView(category, "commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId) {
		ModelAndView result;

		Category category = null;

		try {
			category = this.categoryService.findCategoryAdministratorLogged(categoryId);
			this.categoryService.delete(category);
			result = this.list(String.valueOf(category.getParentCategory().getId()));
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The root category cannot be updated or deleted"))
				result = this.createEditModelAndView(category, "category.error.rootCategory");
			else
				result = this.createEditModelAndView(category, "commit.error");

		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView res;
		res = this.createEditModelAndView(category, null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		if (category == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (category.getId() == 0) {
			final Collection<Category> categories = this.categoryService.findAll();
			result = new ModelAndView("category/create");
			result.addObject("categories", categories);
		} else {
			final Collection<Category> categoriesRec = this.categoryService.findAll();
			final Collection<Category> categoriesToDelete = this.categoryService.findCategoriesToDelete(category.getId(), new HashSet<Category>());
			categoriesRec.removeAll(categoriesToDelete);
			categoriesRec.remove(category);
			result = new ModelAndView("category/edit");
			result.addObject("categories", categoriesRec);
			result.addObject("categoryParentOld", category.getParentCategory().getId());
		}

		final String language = LocaleContextHolder.getLocale().getLanguage();
		result.addObject("language", language);
		result.addObject("category", category);
		result.addObject("actionURL", "category/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}
}
