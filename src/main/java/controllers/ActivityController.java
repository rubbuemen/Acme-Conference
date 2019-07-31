/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import domain.Activity;
import domain.Conference;
import domain.Panel;
import domain.Presentation;
import domain.Tutorial;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {

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


	@RequestMapping(value = "/listGeneric", method = RequestMethod.GET)
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
			result = new ModelAndView("activity/listGeneric");
			result.addObject("panels", panels);
			result.addObject("presentations", presentations);
			result.addObject("tutorials", tutorials);
			result.addObject("conference", conference);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("Activities can only be managed if the conference is in final mode"))
				result = this.createEditModelAndView(null, "activity.error.conferenceNotFinalMode", conference);
			else
				result = this.createEditModelAndView(null, "commit.error", conference);
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

		result = new ModelAndView("redirect:/welcome/index.do");
		result.addObject("conference", conference);
		result.addObject("activity", activity);
		result.addObject("message", message);

		return result;
	}
}
