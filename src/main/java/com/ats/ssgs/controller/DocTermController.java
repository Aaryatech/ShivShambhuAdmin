package com.ats.ssgs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.ssgs.common.Constants;
import com.ats.ssgs.model.master.Document;

@Controller
public class DocTermController {
	RestTemplate rest = new RestTemplate();

	List<Document> docList;

	@RequestMapping(value = "/showAddDocTerm", method = RequestMethod.GET)
	public ModelAndView showAddDocTerm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

			model = new ModelAndView("docterm/adddocterm");

			Document[] docArray = rest.getForObject(Constants.url + "getAllDocList", Document[].class);
			docList = new ArrayList<Document>(Arrays.asList(docArray));

			model.addObject("docList", docList);

			model.addObject("title", "Add DocTerm");

		} catch (Exception e) {

			System.err.println("exception In showAddDocTerm at Doc Term Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;

	}

}
