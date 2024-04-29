package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.dto.LoginDTO;

// class위에 RequestMapping어노테이션을 적어줌. 잘못된건 아닌데 이젠 RequestMapping은 메서드에선 잘 안 씀.
@RequestMapping("/sample/*") // 아래 매핑주소의 공통주소이면서 jsp파일의 폴더명이 됨.
@Controller
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	// 클라이언트(브라우저) 요청이 get방식일 경우. 주소? localhost:9095/sample/doA
	@GetMapping("doA")
	public void doA() {
		logger.info("called doA ...");
	}
	
	@GetMapping("doB")
	public void doB() {
		logger.info("called doB ...");
	}
	
	@GetMapping("doC")
	public void doC() {
		logger.info("called doC ...");
	}
	
	// doD() 메서드를 여러개의 매핑주소가 공유하고자 할 때 다음과 같이 처리(재사용성)
	// 주소1? localhost:9095/sample/doD
	// 주소2? localhost:9095/sample/testD
	@GetMapping(value = {"doD", "testD"})
	public void doD() {
		logger.info("called doD ...");
	}
	
	/****************************************************************************/
	
	// 주소? get요청방식 localhost:9095/sample/basicGet
	@GetMapping("basicGet")
	public void basicGet() {
		logger.info("called basicGet ...");
	}
	
	// 주소? post요청방식 localhost:9095/sample/basicPost

	/*
	@PostMapping("basicPost")
	public void basicPost(String u_id, String u_pw) {
		logger.info("called basicPost ... ");
		logger.info("아이디?" + u_id);
		logger.info("비밀번호?" + u_pw);
	}
	*/
	
	@PostMapping("basicPost")
	public void basicPost(LoginDTO dto) {
		logger.info("called basicPost ... ");
		logger.info("아이디?" + dto.getU_id());
		logger.info("비밀번호?" + dto.getU_pw());
	}
}





