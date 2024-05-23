package com.docmall.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	// ID 중복체크
	// DB 연동작업 등 문제가 일어날 소지가 있을 시 예외작업 진행함.
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String u_id) throws Exception {
		log.info("아이디 : " + u_id);
		ResponseEntity<String> entity = null;
		
		// 아이디가 사용 가능한지 아닌지부터 체크
		String idUse = "";
		if(userInfoService.idCheck(u_id) != null) {
			idUse = "no"; // 사용불가능
		}else {
			idUse = "yes"; // 사용가능
		}
		
		entity = new ResponseEntity<String> (idUse, HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
}
