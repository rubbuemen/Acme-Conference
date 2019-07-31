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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.ConferenceService;
import services.PanelService;
import services.PaperService;
import services.PresentationService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Activity;
import domain.Conference;
import domain.Panel;
import domain.Paper;
import domain.Presentation;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/activity/administrator")
public class AdministratorActivityController extends AbstractController {

	@Autowired
	ActivityService		activityService;

	@Autowired
	PanelService		panelService;

	@Autowired
	PresentationService	presentationService;

	@Autowired
	TutorialService		tutorialService;

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	PaperService		paperService;

	@Autowired
	SectionService		sectionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int conferenceId) {
		ModelAndView result;
		Collection<Panel> panels;
		Collection<Presentation> presentations;
		Collection<Tutorial> tutorials;
		final Conference conference = this.conferenceService.findOne(conferenceId);

		try {
			panels = this.panelService.findPanelsByConference(conference);
			presentations = this.presentationService.findPresentationsByConference(conference);
			tutorials = this.tutorialService.findTutorialsByConference(conference);
			result = new ModelAndView("activity/list");
			result.addObject("panels", panels);
			result.addObject("presentations", presentations);
			result.addObject("tutorials", tutorials);
			result.addObject("conference", conference);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
				result = this.createEditModelAndView(null, "activity.error.conferenceNotFinalMode", conference);
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(null, "hacking.logged.error", conference);
			else
				result = this.createEditModelAndView(null, "commit.error", conference);
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId, @RequestParam final String type) {
		ModelAndView result;
		Activity activity;
		Section section = null;

		final Conference conference = this.conferenceService.findOne(conferenceId);
		final Collection<Panel> panels = this.panelService.findPanelsByConference(conference);
		final Collection<Presentation> presentations = this.presentationService.findPresentationsByConference(conference);
		final Collection<Tutorial> tutorials = this.tutorialService.findTutorialsByConference(conference);

		if (type.equals("panel"))
			activity = this.panelService.create();
		else if (type.equals("presentation"))
			activity = this.presentationService.create();
		else if (type.equals("tutorial")) {
			activity = this.tutorialService.create();
			section = this.sectionService.create();
		} else {
			activity = null;
			section = null;
		}

		result = this.createEditModelAndView(activity, conference);
		if (type.equals("tutorial"))
			result.addObject("section", section);
		result.addObject("panels", panels);
		result.addObject("presentations", presentations);
		result.addObject("tutorials", tutorials);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int conferenceId, @RequestParam final int activityId) {
		ModelAndView result;
		Activity activity = null;
		final Conference conference = this.conferenceService.findOne(conferenceId);

		try {
			activity = this.activityService.findOne(activityId);
			result = this.createEditModelAndView(activity, conference);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
				result = this.createEditModelAndView(activity, "activity.error.conferenceNotFinalMode", conference);
			else if (oops.getMessage().equals("Activities can only be managed if the start date of the conference has not passed"))
				result = this.createEditModelAndView(activity, "activity.error.startDatePassed", conference);
			else if (oops.getMessage().equals("The start moment of an activity must be equal to or later than the start date of the conference and lower than the end date of the conference"))
				result = this.createEditModelAndView(activity, "activity.error.startMomentOutOfRange", conference);
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(activity, "hacking.logged.error", conference);
			else
				result = this.createEditModelAndView(activity, "commit.error", conference);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveTutorial")
	public ModelAndView createOrEdit(@ModelAttribute("activity") @Valid final Tutorial activity, final BindingResult binding, @RequestParam final int conferenceId, final @Valid Section section, final BindingResult binding2) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (activity.getId() == 0 && (binding.hasErrors() || binding2.hasErrors()))
			result = this.createEditModelAndView(activity, conference);
		else if (activity.getId() != 0 && binding.hasErrors())
			result = this.createEditModelAndView(activity, conference);
		else
			try {
				this.tutorialService.save(activity, conference, section);
				result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + conferenceId);
				final Collection<Panel> panels = this.panelService.findPanelsByConference(conference);
				final Collection<Presentation> presentations = this.presentationService.findPresentationsByConference(conference);
				final Collection<Tutorial> tutorials = this.tutorialService.findTutorialsByConference(conference);
				result.addObject("panels", panels);
				result.addObject("presentations", presentations);
				result.addObject("tutorials", tutorials);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
					result = this.createEditModelAndView(activity, "activity.error.conferenceNotFinalMode", conference);
				else if (oops.getMessage().equals("Activities can only be managed if the start date of the conference has not passed"))
					result = this.createEditModelAndView(activity, "activity.error.startDatePassed", conference);
				else if (oops.getMessage().equals("The start moment of an activity must be equal to or later than the start date of the conference and lower than the end date of the conference"))
					result = this.createEditModelAndView(activity, "activity.error.startMomentOutOfRange", conference);
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(activity, "hacking.logged.error", conference);
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error", conference);
				else
					result = this.createEditModelAndView(activity, "commit.error", conference);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePanel")
	public ModelAndView createOrEdit(@ModelAttribute("activity") @Valid final Panel activity, final BindingResult binding, @RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(activity, conference);
		else
			try {
				this.panelService.save(activity, conference);
				result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + conferenceId);
				final Collection<Panel> panels = this.panelService.findPanelsByConference(conference);
				final Collection<Presentation> presentations = this.presentationService.findPresentationsByConference(conference);
				final Collection<Tutorial> tutorials = this.tutorialService.findTutorialsByConference(conference);
				result.addObject("panels", panels);
				result.addObject("presentations", presentations);
				result.addObject("tutorials", tutorials);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
					result = this.createEditModelAndView(activity, "activity.error.conferenceNotFinalMode", conference);
				else if (oops.getMessage().equals("Activities can only be managed if the start date of the conference has not passed"))
					result = this.createEditModelAndView(activity, "activity.error.startDatePassed", conference);
				else if (oops.getMessage().equals("The start moment of an activity must be equal to or later than the start date of the conference and lower than the end date of the conference"))
					result = this.createEditModelAndView(activity, "activity.error.startMomentOutOfRange", conference);
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(activity, "hacking.logged.error", conference);
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error", conference);
				else
					result = this.createEditModelAndView(activity, "commit.error", conference);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePresentation")
	public ModelAndView createOrEdit(@ModelAttribute("activity") @Valid final Presentation activity, final BindingResult binding, @RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(activity, conference);
		else
			try {
				this.presentationService.save(activity, conference);
				result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + conferenceId);
				final Collection<Panel> panels = this.panelService.findPanelsByConference(conference);
				final Collection<Presentation> presentations = this.presentationService.findPresentationsByConference(conference);
				final Collection<Tutorial> tutorials = this.tutorialService.findTutorialsByConference(conference);
				result.addObject("panels", panels);
				result.addObject("presentations", presentations);
				result.addObject("tutorials", tutorials);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
					result = this.createEditModelAndView(activity, "activity.error.conferenceNotFinalMode", conference);
				else if (oops.getMessage().equals("Activities can only be managed if the start date of the conference has not passed"))
					result = this.createEditModelAndView(activity, "activity.error.startDatePassed", conference);
				else if (oops.getMessage().equals("The start moment of an activity must be equal to or later than the start date of the conference and lower than the end date of the conference"))
					result = this.createEditModelAndView(activity, "activity.error.startMomentOutOfRange", conference);
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(activity, "hacking.logged.error", conference);
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error", conference);
				else
					result = this.createEditModelAndView(activity, "commit.error", conference);
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int activityId, @RequestParam final int conferenceId) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(conferenceId);
		final Activity activity = this.activityService.findOne(activityId);

		try {
			this.activityService.delete(activity, conference);
			result = new ModelAndView("redirect:/activity/administrator/list.do?conferenceId=" + conferenceId);
			result.addObject("conference", conference);

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
				result = this.createEditModelAndView(activity, "activity.error.conferenceNotFinalMode", conference);
			else if (oops.getMessage().equals("Activities can only be managed if the start date of the conference has not passed"))
				result = this.createEditModelAndView(activity, "activity.error.startDatePassed", conference);
			else if (oops.getMessage().equals("The start moment of an activity must be equal to or later than the start date of the conference and lower than the end date of the conference"))
				result = this.createEditModelAndView(activity, "activity.error.startMomentOutOfRange", conference);
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(activity, "hacking.logged.error", conference);
			else
				result = this.createEditModelAndView(activity, "commit.error", conference);
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Activity activity, final Conference conference) {
		ModelAndView result;
		result = this.createEditModelAndView(activity, null, conference);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Activity activity, final String message, final Conference conference) {
		ModelAndView result;

		if (activity == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (activity.getId() == 0) {
			if (activity instanceof Panel) {
				result = new ModelAndView("panel/create");
				result.addObject("type", "panel");
			} else if (activity instanceof Presentation) {
				result = new ModelAndView("presentation/create");
				final Collection<Paper> papersCameraReady = this.paperService.findPapersCameraReadyByConferenceId(conference.getId());
				result.addObject("papers", papersCameraReady);
				result.addObject("type", "presentation");
			} else {
				result = new ModelAndView("tutorial/create");
				result.addObject("type", "tutorial");
			}
		} else if (activity instanceof Panel) {
			result = new ModelAndView("panel/edit");
			result.addObject("type", "panel");
		} else if (activity instanceof Presentation) {
			result = new ModelAndView("presentation/edit");
			final Collection<Paper> papersCameraReady = this.paperService.findPapersCameraReadyByConferenceId(conference.getId());
			result.addObject("papers", papersCameraReady);
			result.addObject("type", "presentation");
		} else {
			result = new ModelAndView("tutorial/edit");
			result.addObject("type", "tutorial");
		}

		result.addObject("conference", conference);
		result.addObject("activity", activity);
		result.addObject("actionURL", "activity/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}
}
