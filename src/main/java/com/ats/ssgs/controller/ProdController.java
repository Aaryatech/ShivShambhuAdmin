package com.ats.ssgs.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
@Scope("session")


public class ProdController {

	RestTemplate rest = new RestTemplate();
	DateFormat dateFormatdd = new SimpleDateFormat("dd-MM-yyyy");
	
	DateFormat dateFormatyy = new SimpleDateFormat("yyyy-MM-dd");

	
	
}
