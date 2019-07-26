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
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ConferenceService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Category;
import domain.Conference;
import domain.Sponsorship;

@Controller
@RequestMapping("/conference/administrator")
public class AdministratorConferenceController extends AbstractController {

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	SponsorshipService	sponsorshipService;

	@Autowired
	CategoryService		categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Conference> conferences, conferencesSubmissionDeadlineLastFiveDays, conferencesNotificationDeadlineInLessFiveDays, conferencesCameraReadyDeadlineInLessFiveDays, conferencesStartDateInLessFiveDays;
		conferences = this.conferenceService.findAll();
		conferencesSubmissionDeadlineLastFiveDays = this.conferenceService.findConferencesSubmissionDeadlineLastFiveDays();
		conferencesNotificationDeadlineInLessFiveDays = this.conferenceService.findConferencesNotificationDeadlineInLessFiveDays();
		conferencesCameraReadyDeadlineInLessFiveDays = this.conferenceService.findConferencesCameraReadyDeadlineInLessFiveDays();
		conferencesStartDateInLessFiveDays = this.conferenceService.findConferencesStartDateInLessFiveDays();

		final Map<Conference, Sponsorship> randomSponsorship = new HashMap<>();
		for (final Conference c : conferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship1 = new HashMap<>();
		for (final Conference c : conferencesSubmissionDeadlineLastFiveDays) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship1.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship2 = new HashMap<>();
		for (final Conference c : conferencesNotificationDeadlineInLessFiveDays) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship2.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship3 = new HashMap<>();
		for (final Conference c : conferencesCameraReadyDeadlineInLessFiveDays) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship3.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship4 = new HashMap<>();
		for (final Conference c : conferencesStartDateInLessFiveDays) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship3.put(c, sponsorship);
		}

		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("conference/list");

		result.addObject("conferences", conferences);
		result.addObject("conferencesSubmissionDeadlineLastFiveDays", conferencesSubmissionDeadlineLastFiveDays);
		result.addObject("conferencesNotificationDeadlineInLessFiveDays", conferencesNotificationDeadlineInLessFiveDays);
		result.addObject("conferencesCameraReadyDeadlineInLessFiveDays", conferencesCameraReadyDeadlineInLessFiveDays);
		result.addObject("conferencesStartDateInLessFiveDays", conferencesStartDateInLessFiveDays);
		result.addObject("randomSponsorship", randomSponsorship);
		result.addObject("randomSponsorship1", randomSponsorship1);
		result.addObject("randomSponsorship2", randomSponsorship2);
		result.addObject("randomSponsorship3", randomSponsorship3);
		result.addObject("randomSponsorship4", randomSponsorship4);
		result.addObject("language", language);
		result.addObject("requestURI", "conference/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Conference conference;

		conference = this.conferenceService.create();

		result = this.createEditModelAndView(conference);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId) {
		ModelAndView result;
		Conference conference = null;

		conference = this.conferenceService.findOne(conferenceId);
		result = this.createEditModelAndView(conference);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Conference conference, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(conference);
		else
			try {
				this.conferenceService.save(conference);
				result = new ModelAndView("redirect:/conference/administrator/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("You can only save conferences that are not in final mode"))
					result = this.createEditModelAndView(conference, "conference.error.save.finalMode");
				else if (oops.getMessage().equals("The submission deadline must be future"))
					result = this.createEditModelAndView(conference, "conference.error.submissionDeadlineNotFuture");
				else if (oops.getMessage().equals("The notification deadline must be future"))
					result = this.createEditModelAndView(conference, "conference.error.notificationDeadlineNotFuture");
				else if (oops.getMessage().equals("The camera-ready deadline must be future"))
					result = this.createEditModelAndView(conference, "conference.error.cameraReadyDeadlineNotFuture");
				else if (oops.getMessage().equals("The start date must be future"))
					result = this.createEditModelAndView(conference, "conference.error.startDateNotFuture");
				else if (oops.getMessage().equals("The end date must be future"))
					result = this.createEditModelAndView(conference, "conference.error.endDateNotFuture");
				else if (oops.getMessage().equals("The submission deadline must be before the notification deadline"))
					result = this.createEditModelAndView(conference, "conference.error.submissionDeadlineBeforeNotificationDeadline");
				else if (oops.getMessage().equals("The notification deadline must be before the camera-ready deadline"))
					result = this.createEditModelAndView(conference, "conference.error.notificationDeadlineBeforeCameraReadyDeadline");
				else if (oops.getMessage().equals("The camera-ready deadline must be before the start date"))
					result = this.createEditModelAndView(conference, "conference.error.cameraReadyDeadlineBeforeStartDate");
				else if (oops.getMessage().equals("The start date must be before the end date"))
					result = this.createEditModelAndView(conference, "conference.error.startDateBeforeEndDate");
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(conference, "hacking.logged.error");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(conference, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		try {
			this.conferenceService.delete(conference);
			result = new ModelAndView("redirect:/conference/administrator/list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("You can only delete conferences that are not in final mode"))
				result = this.createEditModelAndView(conference, "conference.error.delete.finalMode");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(conference, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(conference, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public ModelAndView changeFinalMode(@RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		try {
			this.conferenceService.changeFinalMode(conference);
			result = new ModelAndView("redirect:/conference/administrator/list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("This conference is already in final mode"))
				result = this.createEditModelAndView(conference, "conference.error.change.finalMode");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(conference, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(conference, "commit.error");
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Conference conference) {
		ModelAndView result;
		result = this.createEditModelAndView(conference, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Conference conference, final String message) {
		ModelAndView result;

		if (conference == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			if (conference.getId() == 0)
				result = new ModelAndView("conference/create");
			else
				result = new ModelAndView("conference/edit");
			final Collection<Category> categories = this.categoryService.findAll();
			final String language = LocaleContextHolder.getLocale().getLanguage();
			result.addObject("categories", categories);
			result.addObject("language", language);
		}

		result.addObject("conference", conference);
		result.addObject("actionURL", "conference/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
