/*
 * *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.SponsorshipService;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Conference;
import domain.Sponsorship;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	SponsorshipService	sponsorshipService;


	@RequestMapping(value = "/listGeneric", method = RequestMethod.GET)
	public ModelAndView listConferences() {
		ModelAndView result;
		Collection<Conference> forthcomingConferences, pastConferences, runningConferences;
		forthcomingConferences = this.conferenceService.findForthcomingConferencesFinalMode();
		pastConferences = this.conferenceService.findPastConferencesFinalMode();
		runningConferences = this.conferenceService.findRunningConferencesFinalMode();

		final Map<Conference, Sponsorship> randomSponsorship1 = new HashMap<>();
		for (final Conference c : forthcomingConferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship1.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship2 = new HashMap<>();
		for (final Conference c : pastConferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship2.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship3 = new HashMap<>();
		for (final Conference c : runningConferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship3.put(c, sponsorship);
		}

		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("conference/listGeneric");

		result.addObject("forthcomingConferences", forthcomingConferences);
		result.addObject("pastConferences", pastConferences);
		result.addObject("runningConferences", runningConferences);
		result.addObject("randomSponsorship1", randomSponsorship1);
		result.addObject("randomSponsorship2", randomSponsorship2);
		result.addObject("randomSponsorship3", randomSponsorship3);
		result.addObject("language", language);

		return result;
	}

	@RequestMapping(value = "/listGeneric", method = RequestMethod.POST, params = "search")
	public ModelAndView searchConference(final String singleKeyWord) {
		ModelAndView result;
		Collection<Conference> conferences, forthcomingConferences, pastConferences, runningConferences;
		conferences = this.conferenceService.findConferencesFinalModeBySingleKeyWord(singleKeyWord);
		forthcomingConferences = this.conferenceService.findForthcomingConferencesFinalMode();
		pastConferences = this.conferenceService.findPastConferencesFinalMode();
		runningConferences = this.conferenceService.findRunningConferencesFinalMode();

		final Map<Conference, Sponsorship> randomSponsorship = new HashMap<>();
		for (final Conference c : conferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship1 = new HashMap<>();
		for (final Conference c : forthcomingConferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship1.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship2 = new HashMap<>();
		for (final Conference c : pastConferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship2.put(c, sponsorship);
		}

		final Map<Conference, Sponsorship> randomSponsorship3 = new HashMap<>();
		for (final Conference c : runningConferences) {
			final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
			if (sponsorship != null)
				randomSponsorship3.put(c, sponsorship);
		}

		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("conference/listGeneric");

		result.addObject("conferences", conferences);
		result.addObject("forthcomingConferences", forthcomingConferences);
		result.addObject("pastConferences", pastConferences);
		result.addObject("runningConferences", runningConferences);
		result.addObject("randomSponsorship", randomSponsorship);
		result.addObject("randomSponsorship1", randomSponsorship1);
		result.addObject("randomSponsorship2", randomSponsorship2);
		result.addObject("randomSponsorship3", randomSponsorship3);
		result.addObject("language", language);

		return result;
	}

	//A+
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView downloadPDF(final HttpServletResponse response, @RequestParam final int conferenceId) {
		ModelAndView result;
		try {
			final Conference conference = this.conferenceService.findOne(conferenceId);
			final String title = conference.getTitle().toLowerCase().replace(" ", "_");
			response.setContentType("text/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=" + title + ".pdf");
			final Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			this.conferenceService.downloadPDF(document, conference);
			result = new ModelAndView("redirect:/conference/listGeneric.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/conference/listGeneric.do");
			result.addObject("message", "commit.error");
		}

		return result;
	}
}
