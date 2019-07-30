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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Report;
import domain.Submission;

@Controller
@RequestMapping("/report/author")
public class AuthorReportController extends AbstractController {

	@Autowired
	ReportService		reportService;

	@Autowired
	SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int submissionId) {
		ModelAndView result;
		Collection<Report> reports;
		final Submission submission = this.submissionService.findOne(submissionId);

		try {
			reports = this.reportService.findReportsBySubmissionIdAuthorLogged(submissionId);
			result = new ModelAndView("report/list");
			result.addObject("reports", reports);
			result.addObject("requestURI", "report/author/list.do");
			result.addObject("submission", submission);
		} catch (final Throwable oops) {
			if (oops.getMessage().equals("You can't see the reports because you haven't been notified about this submission"))
				result = this.createEditModelAndView(null, "submission.reports.error.notNotified", submission);
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(null, "hacking.logged.error", submission);
			else
				result = this.createEditModelAndView(null, "commit.error", submission);
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Report report, final Submission submission) {
		ModelAndView result;
		result = this.createEditModelAndView(report, null, submission);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final String message, final Submission submission) {
		ModelAndView result;

		result = new ModelAndView("redirect:/welcome/index.do");

		result.addObject("submission", submission);
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}

}
