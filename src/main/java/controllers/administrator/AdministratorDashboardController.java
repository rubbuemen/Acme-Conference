/*
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class AdministratorDashboardController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		final ModelAndView result;

		result = new ModelAndView("dashboard/show");

		//Query C1
		final String[] queryC1 = this.administratorService.dashboardQueryC1().split(",");
		final String minQueryC1 = queryC1[0];
		final String maxQueryC1 = queryC1[1];
		final String avgQueryC1 = queryC1[2];
		final String stddevQueryC1 = queryC1[3];
		result.addObject("minQueryC1", minQueryC1);
		result.addObject("maxQueryC1", maxQueryC1);
		result.addObject("avgQueryC1", avgQueryC1);
		result.addObject("stddevQueryC1", stddevQueryC1);

		//Query C2
		final String[] queryC2 = this.administratorService.dashboardQueryC2().split(",");
		final String minQueryC2 = queryC2[0];
		final String maxQueryC2 = queryC2[1];
		final String avgQueryC2 = queryC2[2];
		final String stddevQueryC2 = queryC2[3];
		result.addObject("minQueryC2", minQueryC2);
		result.addObject("maxQueryC2", maxQueryC2);
		result.addObject("avgQueryC2", avgQueryC2);
		result.addObject("stddevQueryC2", stddevQueryC2);

		//Query C3
		final String[] queryC3 = this.administratorService.dashboardQueryC3().split(",");
		final String minQueryC3 = queryC3[0];
		final String maxQueryC3 = queryC3[1];
		final String avgQueryC3 = queryC3[2];
		final String stddevQueryC3 = queryC3[3];
		result.addObject("minQueryC3", minQueryC3);
		result.addObject("maxQueryC3", maxQueryC3);
		result.addObject("avgQueryC3", avgQueryC3);
		result.addObject("stddevQueryC3", stddevQueryC3);

		//Query C4
		final String[] queryC4 = this.administratorService.dashboardQueryC4().split(",");
		final String minQueryC4 = queryC4[0];
		final String maxQueryC4 = queryC4[1];
		final String avgQueryC4 = queryC4[2];
		final String stddevQueryC4 = queryC4[3];
		result.addObject("minQueryC4", minQueryC4);
		result.addObject("maxQueryC4", maxQueryC4);
		result.addObject("avgQueryC4", avgQueryC4);
		result.addObject("stddevQueryC4", stddevQueryC4);

		//Query B1
		final String[] queryB1 = this.administratorService.dashboardQueryB1().split(",");
		final String minQueryB1 = queryB1[0];
		final String maxQueryB1 = queryB1[1];
		final String avgQueryB1 = queryB1[2];
		final String stddevQueryB1 = queryB1[3];
		result.addObject("minQueryB1", minQueryB1);
		result.addObject("maxQueryB1", maxQueryB1);
		result.addObject("avgQueryB1", avgQueryB1);
		result.addObject("stddevQueryB1", stddevQueryB1);

		//Query B2
		final String[] queryB2 = this.administratorService.dashboardQueryB2().split(",");
		final String minQueryB2 = queryB2[0];
		final String maxQueryB2 = queryB2[1];
		final String avgQueryB2 = queryB2[2];
		final String stddevQueryB2 = queryB2[3];
		result.addObject("minQueryB2", minQueryB2);
		result.addObject("maxQueryB2", maxQueryB2);
		result.addObject("avgQueryB2", avgQueryB2);
		result.addObject("stddevQueryB2", stddevQueryB2);

		//Query B3
		final String[] queryB3 = this.administratorService.dashboardQueryB3().split(",");
		final String minQueryB3 = queryB3[0];
		final String maxQueryB3 = queryB3[1];
		final String avgQueryB3 = queryB3[2];
		final String stddevQueryB3 = queryB3[3];
		result.addObject("minQueryB3", minQueryB3);
		result.addObject("maxQueryB3", maxQueryB3);
		result.addObject("avgQueryB3", avgQueryB3);
		result.addObject("stddevQueryB3", stddevQueryB3);

		return result;
	}

}
