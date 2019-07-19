/*
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.AuthorService;
import services.ReviewerService;
import services.SponsorService;
import services.UserAccountService;
import domain.Actor;
import domain.Author;
import domain.Reviewer;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	ActorService			actorService;

	@Autowired
	AuthorService			authorService;

	@Autowired
	ReviewerService			reviewerService;

	@Autowired
	SponsorService			sponsorService;

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	UserAccountService		userAccountService;


	@RequestMapping(value = "/register-author", method = RequestMethod.GET)
	public ModelAndView registerAuthor() {
		ModelAndView result;
		Author actor;

		actor = this.authorService.create();

		result = new ModelAndView("actor/register");

		result.addObject("actionURL", "actor/register-author.do");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/register-reviewer", method = RequestMethod.GET)
	public ModelAndView registerReviewer() {
		ModelAndView result;
		Author actor;

		actor = this.authorService.create();

		result = new ModelAndView("actor/register");

		result.addObject("authority", Authority.REVIEWER);
		result.addObject("actionURL", "actor/register-reviewer.do");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/register-sponsor", method = RequestMethod.GET)
	public ModelAndView registerSponsor() {
		ModelAndView result;
		Sponsor actor;

		actor = this.sponsorService.create();

		result = new ModelAndView("actor/register");

		result.addObject("actionURL", "actor/register-sponsor.do");
		result.addObject("actor", actor);

		return result;
	}

	@RequestMapping(value = "/register-author", method = RequestMethod.POST, params = "save")
	public ModelAndView registerAuthor(@ModelAttribute("actor") @Valid final Author actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.authorService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(actor, "actor.error.duplicate.user");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(actor, "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/register-reviewer", method = RequestMethod.POST, params = "save")
	public ModelAndView registerAuthor(@ModelAttribute("actor") @Valid final Reviewer actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.reviewerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(actor, "actor.error.duplicate.user");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(actor, "commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/register-sponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView registerAuthor(@ModelAttribute("actor") @Valid final Sponsor actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.sponsorService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(actor, "actor.error.duplicate.user");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(actor, "commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;
		if (actor == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = new ModelAndView("actor/register");

		if (actor instanceof Reviewer)
			result.addObject("authority", Authority.REVIEWER);
		result.addObject("actor", actor);
		result.addObject("message", message);

		return result;
	}

}
