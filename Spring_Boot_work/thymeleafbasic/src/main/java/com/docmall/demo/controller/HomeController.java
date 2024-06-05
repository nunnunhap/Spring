package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/home/*")
@Controller
public class HomeController {
	
	@GetMapping("/testA")
	// @ResponseBody떄문에 리턴값 "test"가 화면에 그대로 출력
	// 실 개발에선 ResponseEntity를 사용함.
	@ResponseBody
	public String testA() {
		return "testA";
	}
	
	@GetMapping("/testB")
	@ResponseBody
	public String testB(String userid, String pwd) {
		return String.format("testB- userid:%s, pwd:%s", userid, pwd);
	}
	
	// REST full 개발 -> 경로주소 형태 권장
	@GetMapping("/testC/{userid}/{pwd}")
	@ResponseBody
	public String testC(@PathVariable("userid") String userid, @PathVariable("pwd") String pwd) {
		return String.format("testC- userid:%s, pwd:%s", userid, pwd);
	}
	
	// Rest + 쿼리 스트링
	@GetMapping("/testD/{userid}")
	@ResponseBody
	public String testD(@PathVariable("userid") String userid, String pwd) {
		return String.format("testD- userid:%s, pwd:%s", userid, pwd);
	}
	
}
