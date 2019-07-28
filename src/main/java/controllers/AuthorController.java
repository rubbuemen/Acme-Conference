/*
 * *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import domain.Author;

@Controller
@RequestMapping("/author")
public class AuthorController extends AbstractController {

	@Autowired
	AuthorService	authorService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showAuthor(@RequestParam final int authorId) {
		ModelAndView result;

		final Author author = this.authorService.findOne(authorId);

		result = new ModelAndView("author/show");

		result.addObject("author", author);

		return result;
	}
}
