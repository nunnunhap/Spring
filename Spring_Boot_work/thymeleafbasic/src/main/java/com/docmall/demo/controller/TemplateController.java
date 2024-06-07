package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/fragment/*")
public class TemplateController {
	
	@GetMapping("/fragmentMain")
	public String fragmentMain() {
		
		return "/fragment/fragmentMain";
	}
	
	@GetMapping("/layoutExtend")
	public String layoutExtend() {
		
		return "layoutExtend/layoutExtendMain";
	}
	
	@GetMapping("/ex01")
	public String ex01() {
		return "layoutExtend/ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02() {
		return "layoutExtend/ex02";
	}
	
	@GetMapping("/ex03")
	public String ex03() {
		return "layoutExtend/ex03";
	}
	
	
}
