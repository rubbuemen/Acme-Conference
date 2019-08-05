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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.CategoryService;
import services.ConferenceService;
import services.FinderService;
import services.SponsorshipService;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import controllers.AbstractController;
import domain.Category;
import domain.Conference;
import domain.Finder;
import domain.Sponsorship;

@Controller
@RequestMapping("/finder/author")
public class AuthorFinderController extends AbstractController {

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	AuthorService		authorService;

	@Autowired
	FinderService		finderService;

	@Autowired
	CategoryService		categoryService;

	@Autowired
	SponsorshipService	sponsorshipService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView finderSearch() {
		ModelAndView result;

		Finder finder = null;

		try {
			finder = this.finderService.findFinderAuthorLogged();
			result = this.createEditModelAndView(finder);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.updateCriteria(finder.getKeyWord(), finder.getMinDate(), finder.getMaxDate(), finder.getMaxFee(), finder.getCategory());
				result = new ModelAndView("redirect:/finder/author/edit.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("The logged actor is not the owner of this entity"))
					result = this.createEditModelAndView(finder, "hacking.logged.error");
				else if (oops.getMessage().equals("This entity does not exist"))
					result = this.createEditModelAndView(null, "hacking.notExist.error");
				else
					result = this.createEditModelAndView(finder, "commit.error");
			}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;
		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		if (finder == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			result = new ModelAndView("finder/edit");
			final String language = LocaleContextHolder.getLocale().getLanguage();
			final Collection<Category> categories = this.categoryService.findAll();
			final Map<Conference, Sponsorship> randomSponsorship = new HashMap<>();
			for (final Conference c : finder.getConferences()) {
				final Sponsorship sponsorship = this.sponsorshipService.findRandomSponsorship();
				if (sponsorship != null)
					randomSponsorship.put(c, sponsorship);
			}
			result.addObject("conferences", finder.getConferences());
			result.addObject("language", language);
			result.addObject("categories", categories);
			result.addObject("randomSponsorship", randomSponsorship);
		}

		result.addObject("finder", finder);
		result.addObject("actionURL", "finder/author/edit.do");
		result.addObject("message", message);

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
			result = new ModelAndView("redirect:/finder/author/edit.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(null, "commit.error");
		}

		return result;
	}

}
