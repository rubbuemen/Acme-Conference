/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.reviewer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/report/reviewer")
public class ReviewerReportController extends AbstractController {

	@Autowired
	ReportService		reportService;

	@Autowired
	SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Report> reports;

		reports = this.reportService.findReportsByReviewerLogged();

		result = new ModelAndView("report/list");

		result.addObject("reports", reports);
		result.addObject("requestURI", "report/reviewer/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Report report;

		report = this.reportService.create();

		result = this.createEditModelAndView(report);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				this.reportService.save(report);
				result = new ModelAndView("redirect:/report/reviewer/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("This submission is not available to report"))
					result = this.createEditModelAndView(report, "report.error.save.submissionNotAvailable");
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(report, "hacking.logged.error");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(report, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView acceptReport(@RequestParam final int reportId) {
		ModelAndView result;

		Report report = null;

		try {
			report = this.reportService.findReportReviewerLogged(reportId);
			this.reportService.acceptReport(report);
			result = new ModelAndView("redirect:/report/reviewer/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The status of this report is not 'BORDER-LINE'"))
				result = this.createEditModelAndView(report, "report.error.notBorderLine");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(report, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(report, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView rejectReport(@RequestParam final int reportId) {
		ModelAndView result;

		Report report = null;

		try {
			report = this.reportService.findReportReviewerLogged(reportId);
			this.reportService.rejectReport(report);
			result = new ModelAndView("redirect:/report/reviewer/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The status of this report is not 'BORDER-LINE'"))
				result = this.createEditModelAndView(report, "report.error.notBorderLine");
			else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
				result = this.createEditModelAndView(report, "hacking.logged.error");
			else if (oops.getMessage().equals("This entity does not exist"))
				result = this.createEditModelAndView(null, "hacking.notExist.error");
			else
				result = this.createEditModelAndView(report, "commit.error");
		}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;
		result = this.createEditModelAndView(report, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final String message) {
		ModelAndView result;

		if (report == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else if (report.getId() == 0) {
			result = new ModelAndView("report/create");
			final Collection<Submission> findSubmissionsToReport = this.submissionService.findSubmissionsToReportReviewerLogged();
			result.addObject("submissions", findSubmissionsToReport);
			result.addObject("report", report);
			result.addObject("actionURL", "report/reviewer/edit.do");
		} else
			result = this.list();

		result.addObject("message", message);

		return result;
	}

}
