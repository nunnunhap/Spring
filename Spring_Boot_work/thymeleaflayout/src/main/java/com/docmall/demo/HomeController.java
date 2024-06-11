package com.docmall.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	
	@GetMapping("/basic")
	public String basic() {
		
		return "basic";
	}
}
