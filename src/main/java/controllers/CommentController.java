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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	CommentService		commentService;

	@Autowired
	CommentableService	commentableService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int commentableId) {
		ModelAndView result;
		Collection<Comment> comments;
		final Commentable commentable = this.commentableService.findOne(commentableId);

		try {
			comments = this.commentService.findCommentsByCommentable(commentable);
			result = new ModelAndView("comment/list");
			result.addObject("comments", comments);
			result.addObject("commentable", commentable);
			result.addObject("requestURI", "comment/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(null, "commit.error", commentable);
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentableId) {
		ModelAndView result;
		Comment comment;
		Collection<Comment> comments;

		final Commentable commentable = this.commentableService.findOne(commentableId);
		comments = this.commentService.findCommentsByCommentable(commentable);

		comment = this.commentService.create();

		result = this.createEditModelAndView(comment, commentable);
		result.addObject("comments", comments);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView createOrEdit(@Valid final Comment comment, final BindingResult binding, @RequestParam final int commentableId) {
		ModelAndView result;

		final Commentable commentable = this.commentableService.findOne(commentableId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(comment, commentable);
		else
			try {
				this.commentService.save(comment, commentable);
				result = new ModelAndView("redirect:/comment/list.do?commentableId=" + commentableId);
				final Collection<Comment> comments = this.commentService.findCommentsByCommentable(commentable);
				result.addObject("comments", comments);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "commit.error", commentable);
			}

		return result;
	}

	// Ancillary methods
	protected ModelAndView createEditModelAndView(final Comment comment, final Commentable commentable) {
		ModelAndView result;
		result = this.createEditModelAndView(comment, null, commentable);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message, final Commentable commentable) {
		ModelAndView result;

		if (comment == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = new ModelAndView("comment/create");

		result.addObject("commentable", commentable);
		result.addObject("comment", comment);
		result.addObject("actionURL", "comment/edit.do");
		result.addObject("message", message);

		return result;
	}
}
