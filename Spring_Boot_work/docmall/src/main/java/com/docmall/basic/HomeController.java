package com.docmall.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	// @ResponseBody// 사용 시, return "index"는 index라는 글자가 사용됨. ResponseEntity어노테이션이 ResponseBody기능까지 포함함.
	@GetMapping("/")
	public String index() {
		log.info("기본주소 /");
		return "index"; // Tymeleaf 파일명
	}
	
	
	
}
