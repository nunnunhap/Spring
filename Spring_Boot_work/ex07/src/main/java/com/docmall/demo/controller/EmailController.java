package com.docmall.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.demo.dto.EmailDTO;
import com.docmall.demo.service.EmailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController // jsp를 사용하지 않는다.
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/email/*")
public class EmailController {
	
	private final EmailService emailService;
	
	@GetMapping("authcode")
	// HttpSession : 사용자에게 발급한 인증코드를 세션을 통하여 서버에 저장하고 있어야만 이후에 인증확인 요청이 들어왔을 때 비교가 가능함.
	// 실은 이 EmailDTO dto는 receiverMail로 직접 받아도 됨.
	// EmailDTO dto가 스프링에서 알아서 객체가 생성됨. dto.setReceiverMail("입력한 메일주소");
	public ResponseEntity<String> authcode(EmailDTO dto, HttpSession session) {
		log.info("dto : " + dto); // dto.toString() 호출
		
		ResponseEntity<String> entity = null;
		
		// 인증코드
		String authcode = "";
		for(int i = 0; i < 6; i++) { // 보안생각하면 uuid 등등을 사용가능
			authcode += String.valueOf((int) (Math.random() * 10));
		}
		
		log.info("인증코드 : " + authcode);
		
		// 사용자가 자신의 메일에서 발급받은 인증코드를 읽고 회원가입 시 인증확인란에 입력을 하게 될 시
		// 서버에서 비교 목적으로 세션방식으로 인증코드를 메모리에 저장해두어야 함.
		session.setAttribute("authcode", authcode);
		
		try {
			emailService.sendMail(dto, authcode);
			// HttpStatus.OK : 200
			entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// HttpStatus.INTERNAL_SERVER_ERROR : 500
			entity = new ResponseEntity<String> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return entity;
		// 위의 코드를 ajax로 호출해야 함.
	}
	
	
}
