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

import services.AdministratorService;
import services.TopicService;
import controllers.AbstractController;
import domain.Topic;

@Controller
@RequestMapping("/topic/administrator")
public class AdministratorTopicController extends AbstractController {

	@Autowired
	TopicService			topicService;

	@Autowired
	AdministratorService	administratorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Topic> topics;

		topics = this.topicService.findAll();
		final Collection<Topic> topicsUsed = this.topicService.findTopicsUsed();

		result = new ModelAndView("topic/list");

		result.addObject("topics", topics);
		result.addObject("topicsUsed", topicsUsed);
		result.addObject("requestURI", "topic/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Topic topic;

		topic = this.topicService.create();

		result = this.createEditModelAndView(topic);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int topicId) {
		ModelAndView result;
		Topic topic = null;

		try {
			topic = this.topicService.findOne(topicId);
			result = this.createEditModelAndView(topic);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(topic, "hacking.logged.error");
			else
				result = this.createEditModelAndView(topic, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Topic topic, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(topic);
		else
			try {
				this.topicService.save(topic);
				result = new ModelAndView("redirect:/topic/administrator/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					result = this.createEditModelAndView(topic, "topic.error.duplicate.name");
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(topic, "hacking.logged.error");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(topic, "commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int topicId) {
		ModelAndView result;

		final Topic topic = this.topicService.findOne(topicId);

		try {
			this.topicService.delete(topic);
			result = new ModelAndView("redirect:/topic/administrator/list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("This topic can not be deleted because it is in use"))
				result = this.createEditModelAndView(topic, "topic.error.occupied");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(topic, "hacking.logged.error");
			else
				result = this.createEditModelAndView(topic, "commit.error");
		}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Topic topic) {
		ModelAndView result;
		result = this.createEditModelAndView(topic, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Topic topic, final String message) {
		ModelAndView result;

		if (topic == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (topic.getId() == 0)
			result = new ModelAndView("topic/create");
		else
			result = new ModelAndView("topic/edit");

		result.addObject("topic", topic);
		result.addObject("actionURL", "topic/administrator/edit.do");
		result.addObject("message", message);

		return result;
	}

}
