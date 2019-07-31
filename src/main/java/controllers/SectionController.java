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

import services.ConferenceService;
import services.SectionService;
import services.TutorialService;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	SectionService		sectionService;

	@Autowired
	TutorialService		tutorialService;

	@Autowired
	ConferenceService	conferenceService;


	@RequestMapping(value = "/listGeneric", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		ModelAndView result;
		Collection<Section> sections;
		Conference conference;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		try {
			sections = this.sectionService.findSectionsByTutorial(tutorialId);
			conference = this.conferenceService.findConferenceByActivityId(tutorialId);
			result = new ModelAndView("section/listGeneric");
			result.addObject("sections", sections);
			result.addObject("requestURI", "section/listGeneric.do");
			result.addObject("tutorial", tutorial);
			result.addObject("conference", conference);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(null, "commit.error", tutorial);
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Section section, final Tutorial tutorial) {
		ModelAndView result;
		result = this.createEditModelAndView(section, null, tutorial);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section, final String message, final Tutorial tutorial) {
		ModelAndView result;

		result = new ModelAndView("redirect:/welcome/index.do");

		result.addObject("tutorial", tutorial);
		result.addObject("section", section);
		result.addObject("message", message);

		return result;
	}
}
