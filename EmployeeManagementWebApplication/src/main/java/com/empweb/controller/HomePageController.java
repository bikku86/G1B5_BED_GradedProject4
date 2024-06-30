package com.empweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
	
	@GetMapping("/")
	public String home() {
		return "<H1>Welcome to the root URL!</H1>";
	}
	
	@GetMapping("/home")
	public String homePage() {
		return "<H1>This is the Home Page</H1>";
	}

}
