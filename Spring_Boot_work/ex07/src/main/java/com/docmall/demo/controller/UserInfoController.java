package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// bean 생성 : userInfoController
@Controller
@RequestMapping("/userinfo/*")
@Slf4j
@RequiredArgsConstructor
public class UserInfoController {
	// DI
	private final UserInfoService userInfoService;
	
	// 회원가입폼
	@GetMapping("join")
	public void joinForm() {
		log.info("called join...");
	}
	
	
}
