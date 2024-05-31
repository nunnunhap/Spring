package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/*")
@Controller
@Slf4j
public class AdminController {
	
	// 이 주소는 인터셉터 타지 않음.
	@GetMapping("intro")
	@ResponseBody // 이 어노테이션을 사용하면 jsp 안 만들고 리턴값이 문자열데이터로써 바로 클라이언트로 넘어감.
	public String intro() {
		log.info("called intro...");
		return "intro";
	}
	
	// 이 주소는 인터셉터 타지 않음.
	@PostMapping("/admin_ok")
	@ResponseBody
	public String admin_ok() {
		log.info("called admin_ok...");
		return "admin_ok";
	}
	
	// // 이 주소는 인터셉터 탄다.
	@GetMapping("/product/pro_ins")
	@ResponseBody
	public String pro_ins() {
		log.info("/product/pro_ins");
		return "/product/pro_ins";
	}
	
	
	
	
}
