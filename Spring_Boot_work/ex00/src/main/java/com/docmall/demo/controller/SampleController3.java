package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController3 {
	private static final Logger logger = LoggerFactory.getLogger(SampleController3.class);
	
	// 1) http://localhost:9095/doG?name=abc&age=100
	// 2) http://localhost:9095/doG?age=100			name=null age=100
	// 3) http://localhost:9095/doG?name=abc		에러발생
	// 파라미터가 기본 데이터 타입이면 반드시 값을 제공해야 함. 참조타입은 null로 처리되어 상관없음.
	
	@RequestMapping("/doG")
	public String doG(String name, int age) {
		logger.info("이름은? " + name);
		logger.info("나이는? " + age);
		return "testG";
	}
}
