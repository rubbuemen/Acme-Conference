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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class AdministratorSubmissionController extends AbstractController {

	@Autowired
	SubmissionService	submissionService;

	@Autowired
	ConferenceService	conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> submissions;

		submissions = this.submissionService.findSubmissionsByStatus();
		final Map<Submission, Author> mapSubmissionAuthor = this.submissionService.getMapSubmissionAuthor(submissions);

		result = new ModelAndView("submission/list");

		result.addObject("submissions", submissions);
		result.addObject("mapSubmissionAuthor", mapSubmissionAuthor);
		result.addObject("requestURI", "submission/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assignSubmission(@RequestParam final int submissionId) {
		ModelAndView result;

		final Submission submission = this.submissionService.findOne(submissionId);

		try {
			this.submissionService.assignSubmission(submission);
			result = new ModelAndView("redirect:/submission/administrator/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The submission is already assigned"))
				result = this.createEditModelAndView(submission, "submission.error.assign");
			else if (oops.getMessage().equals("You cannot assign the submission because the conference start date has passed"))
				result = this.createEditModelAndView(submission, "submission.error.assignStartDatePassed");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(submission, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(submission, "commit.error");
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Submission submission) {
		ModelAndView result;
		result = this.createEditModelAndView(submission, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Submission submission, final String message) {
		ModelAndView result;

		if (submission == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = this.list();

		result.addObject("message", message);

		return result;
	}

}
