package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/") // 기본주소
	public String main() {
		
		return "redirect:/category/catelist";
	}
	
	
	
}
