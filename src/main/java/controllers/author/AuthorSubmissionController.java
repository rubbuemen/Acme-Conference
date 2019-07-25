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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Conference;
import domain.Submission;

@Controller
@RequestMapping("/submission/author")
public class AuthorSubmissionController extends AbstractController {

	@Autowired
	SubmissionService	submissionService;

	@Autowired
	ConferenceService	conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Submission> submissions;

		submissions = this.submissionService.findSubmissionsByAuthorLogged();

		result = new ModelAndView("submission/list");

		result.addObject("submissions", submissions);
		result.addObject("requestURI", "submission/author/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Submission submission;

		submission = this.submissionService.create();

		result = this.createEditModelAndView(submission);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Submission submission, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(submission);
		else
			try {
				this.submissionService.save(submission);
				result = new ModelAndView("redirect:/submission/author/list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("When creating a submission, it has not yet been possible to make an assignment"))
					result = this.createEditModelAndView(submission, "submission.error.save.notAssigned");
				else if (oops.getMessage().equals("When creating a submission it has not yet been possible to notify"))
					result = this.createEditModelAndView(submission, "submission.error.save.notNotified");
				else if (oops.getMessage().equals("The submission must include a paper to review only, not a camera-ready version"))
					result = this.createEditModelAndView(submission, "submission.error.save.notCameraReadyVersion");
				else if (oops.getMessage().equals("This conference is not available to submit"))
					result = this.createEditModelAndView(submission, "submission.error.save.coferenceNotAvailable");
				else if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(submission, "hacking.logged.error");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(submission, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView uploadCameraReadyVersion(@RequestParam final int submissionId) {
		ModelAndView result;

		final Submission submission = this.submissionService.findSubmissionAuthorLogged(submissionId);

		try {
			this.submissionService.uploadCameraReadyVersion(submission);
			result = new ModelAndView("redirect:/submission/author/list.do");

		} catch (final Throwable oops) {
			if (oops.getMessage().equals("The submission paper already has a camera-ready version"))
				result = this.createEditModelAndView(submission, "submission.error.upload.cameraReadyVersion");
			else if (oops.getMessage().equals("To upload the camera-ready version the submission must be accepted"))
				result = this.createEditModelAndView(submission, "submission.error.upload.submissionNotAccepted");
			else if (oops.getMessage().equals("You can't upload the camera-ready version because the camera-ready deadline of the conference has elipsed"))
				result = this.createEditModelAndView(submission, "submission.error.upload.conferenceDeadlineElipsed");
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
		else if (submission.getId() == 0) {
			result = new ModelAndView("submission/create");
			final Collection<Conference> findConferencesToSubmit = this.conferenceService.findConferencesToSubmit();
			result.addObject("conferences", findConferencesToSubmit);
			result.addObject("submission", submission);
			result.addObject("actionURL", "submission/author/edit.do");
		} else
			result = this.list();

		result.addObject("message", message);

		return result;
	}

}
