package com.setvect.bokslportal.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public String page(final HttpServletRequest request) {
		return "index";
	}
}