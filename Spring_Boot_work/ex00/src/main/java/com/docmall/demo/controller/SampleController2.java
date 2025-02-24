package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 이 클래스가 브라우저로부터 사용되기 원한다면 무조건 이 @Controller 어노테이션을 붙여야 함.
public class SampleController2 {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleController2.class);
	
	// 매핑주소와 메서드명은 일치하지 않아도 됨.
	// 매핑주소를 jsp명으로 사용할 경우에는 메서드 리턴타입이 void여야 함.(규칙)
	// 매핑주소를 jsp명으로 사용하지 않을 경우에는 method의 리턴타입을 String으로 해서 리턴값을 jsp명으로 사용함.(규칙)
	
	@RequestMapping("/doD")
	public String doD() {
		logger.info("called doD ...");
		return "testD"; // "testD"가 jsp파일명이 됨.
	}
	
	@RequestMapping("/doE")
	public String doE() {
		logger.info("called doE ...");
		return "testE";
	}
	
	@RequestMapping("/doF")
	public String doF() {
		logger.info("called doF ...");
		return "testF";
	}
}
