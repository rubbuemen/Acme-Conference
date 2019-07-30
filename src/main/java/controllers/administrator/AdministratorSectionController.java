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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Conference;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section/administrator")
public class AdministratorSectionController extends AbstractController {

	@Autowired
	SectionService		sectionService;

	@Autowired
	TutorialService		tutorialService;

	@Autowired
	ConferenceService	conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		ModelAndView result;
		Collection<Section> sections;
		Conference conference;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		try {
			sections = this.sectionService.findSectionsByTutorial(tutorialId);
			conference = this.conferenceService.findConferenceByActivityId(tutorialId);
			result = new ModelAndView("section/list");
			result.addObject("sections", sections);
			result.addObject("requestURI", "section/administrator/list.do");
			result.addObject("tutorial", tutorial);
			result.addObject("conference", conference);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(null, "hacking.logged.error", tutorial);
			else
				result = this.createEditModelAndView(null, "commit.error", tutorial);
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;

		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Collection<Section> sections = this.sectionService.findSectionsByTutorial(tutorialId);
		section = this.sectionService.create();

		result = this.createEditModelAndView(section, tutorial);
		result.addObject("sections", sections);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId, @RequestParam final int sectionId) {
		ModelAndView result;
		Section section = null;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		try {
			this.sectionService.findSectionsByTutorial(tutorialId);
			section = this.sectionService.findOne(sectionId);
			result = this.createEditModelAndView(section, tutorial);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(section, "hacking.logged.error", tutorial);
			else
				result = this.createEditModelAndView(section, "commit.error", tutorial);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Section section, final BindingResult binding, @RequestParam final int tutorialId) {
		ModelAndView result;

		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(section, tutorial);
		else {
			this.sectionService.save(section, tutorial);
			result = new ModelAndView("redirect:/section/administrator/list.do?tutorialId=" + tutorialId);
			try {
				final Collection<Section> sections = this.sectionService.findSectionsByTutorial(tutorialId);
				result.addObject("sections", sections);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(section, "hacking.logged.error", tutorial);
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error", tutorial);
				else
					result = this.createEditModelAndView(section, "commit.error", tutorial);
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sectionId, @RequestParam final int tutorialId) {
		ModelAndView result;

		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		final Section section = this.sectionService.findOne(sectionId);

		try {
			this.sectionService.delete(section, tutorial);
			result = new ModelAndView("redirect:/section/administrator/list.do?tutorialId=" + tutorialId);
			result.addObject("tutorial", tutorial);

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The tutorial must be at least one section"))
				result = this.createEditModelAndView(section, "section.error.oneSection", tutorial);
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(section, "hacking.logged.error", tutorial);
			else
				result = this.createEditModelAndView(section, "commit.error", tutorial);
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

		if (section == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (section.getId() == 0)
			result = new ModelAndView("section/create");
		else
			result = new ModelAndView("section/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("section", section);
		result.addObject("actionURL", "section/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}
}
