/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuthorService;
import services.ConferenceService;
import services.RegistrationService;
import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/registration/author")
public class AuthorRegistrationController extends AbstractController {

	@Autowired
	RegistrationService			registrationService;

	@Autowired
	AuthorService				authorService;

	@Autowired
	ActorService				actorService;

	@Autowired
	SystemConfigurationService	systemConfigurationService;

	@Autowired
	ConferenceService			conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Registration> registrations;

		registrations = this.registrationService.findRegistrationsByAuthorLogged();

		result = new ModelAndView("registration/list");

		result.addObject("registrations", registrations);
		result.addObject("requestURI", "registration/author/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Registration registration;

		registration = this.registrationService.create();

		result = this.createEditModelAndView(registration);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Registration registration, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(registration);
		else
			try {
				this.registrationService.save(registration);
				result = new ModelAndView("redirect:/registration/author/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Invalid credit card"))
					result = this.createEditModelAndView(registration, "creditCard.error.invalid");
				else if (oops.getMessage().equals("Expired credit card"))
					result = this.createEditModelAndView(registration, "creditCard.error.expired");
				else if (oops.getMessage().equals("To register for a conference, it must be in final mode"))
					result = this.createEditModelAndView(registration, "registration.error.conferenceFinalMode");
				else if (oops.getMessage().equals("You can't register for this conference because it's already started"))
					result = this.createEditModelAndView(registration, "registration.error.conferenceAlreadyStarted");
				else if (oops.getMessage().equals("You cannot register for a conference in which you are already registered"))
					result = this.createEditModelAndView(registration, "registration.error.conferenceAlreadyRegistered");
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(registration, "hacking.logged.error");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(registration, "commit.error");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Registration registration) {
		ModelAndView result;
		result = this.createEditModelAndView(registration, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Registration registration, final String message) {
		ModelAndView result;

		if (registration == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			final Collection<Conference> conferences = this.conferenceService.findConferencesFinalModeNotStartDateDeadlineNotRegistrated();
			final Collection<Conference> conferencesAlreadyRegistered = this.conferenceService.findConferencesRegistratedByAuthorId();
			conferences.removeAll(conferencesAlreadyRegistered);
			final Collection<String> creditCardBrands = this.systemConfigurationService.getConfiguration().getCreditCardBrands();
			if (registration.getId() == 0)
				result = new ModelAndView("registration/create");
			else
				result = new ModelAndView("registration/edit");
			result.addObject("conferences", conferences);
			result.addObject("creditCardBrands", creditCardBrands);
		}

		result.addObject("registration", registration);
		result.addObject("actionURL", "registration/author/edit.do");
		result.addObject("message", message);

		return result;
	}

}
