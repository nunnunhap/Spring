package com.docmall.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/sample3/*")
@Controller
public class SampleController3 {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController3.class);	
	
	// 클라이언트에서 사용하는 파라미터명과 스프링의 메서드 파라미터명이 일치하지 않을 때 중간에 @RequestParam("클라이언트 파라미터명") 사용
	// 매핑주소? localhost:9095/sample3/doA?uname=홍길동&uage=15
	@GetMapping("doA")
	public void doA(@RequestParam("uname") String name, @RequestParam("uage") int age) {
		logger.info("이름 : " + name);
		logger.info("나이 : " + age);
	}
	
	// 매핑주소? localhost:9095/sample3/doB?age=100
	// 매핑주소? localhost:9095/sample3/doB?name=홍길동&age=100
	@GetMapping("doB")
	// required는 반드시 입력을 받아야 하는지 아닌지.
	// defaultValue는 입력값이 없으면 "이름없음"으로 출력됨.
	public void doB(@RequestParam(value = "name", required = false, defaultValue = "이름없음") String name, int age) {
		logger.info("이름 : " + name);
		logger.info("나이 : " + age);	
	}
	
	// 동일한 파라미터명으로 여러 개의 값을 받을 때
	// 매핑주소? localhost:9095/sample3/doD?num1&num2&num=3
	@GetMapping("doD")
	public void doD( @RequestParam("num") ArrayList<Integer> idx) {
		logger.info("idx : " + idx);
	}
	
	// 동일한 파라미터명으로 여러 개의 값을 받을 때2
	// 매핑주소? localhost:9095/sample3/doD?num1&num2&num=3
	@GetMapping("doE")
	public void doE(@RequestParam("num") int[] idx) {
		logger.info("idx : " + Arrays.toString(idx));
	}
	
	// 동일한 파라미터명으로 여러 개의 값을 받을 때3
	// 매핑주소? localhost:9095/sample3/doF?userId=user01&userId=user02&userId=user03
	@GetMapping("doF")
	public void doF(@RequestParam("userId") ArrayList<String> userId ) {
		logger.info("idx : " + userId);
	}
	
}
