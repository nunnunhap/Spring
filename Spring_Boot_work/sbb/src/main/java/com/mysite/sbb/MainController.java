package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	@GetMapping("/sbb")
	@ResponseBody // Tymeleaf 없이 그냥 데이터만 브라우저에 출력하고 싶을 때 사용함.
	public String index() {
		log.info("index");
		return "안녕하세요, 스프링부트 JPA 학습 진행합니다.";
	}
	
	
	
	
	
}
