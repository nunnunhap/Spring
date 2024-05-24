package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/thymeleaf/*")
public class TestController {
	
	@GetMapping("/test")
	public String test() {
		log.info("called test ...");
		
		return "/thymeleaf/test"; // 뷰이름이 jsp나 thymeleaf 둘 중 어떤 것으로 해석되는지? 이건 thymeleaf로 해석
	}
}	
