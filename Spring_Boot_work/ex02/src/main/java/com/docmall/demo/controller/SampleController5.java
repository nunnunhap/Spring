package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// ResponseEntity 클래스 : 일반적으로 ajax기술을 사용할 때 이용
// 명시적으로 서버에서 클라이언트에게 보내는 데이터와 그 데이터 설명에 해당하는 MIME-TYPE, 인코딩, 상태코드를 보내서 명시적으로 작업하기 위한 클래스
@RequestMapping("/sample5/*")
@Controller
public class SampleController5 {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController5.class);
	
	@GetMapping("doA")
	public @ResponseBody String doA() {
		// json포맷의 문자열, escape sequence(특수문자)사용 (예시): "{\"name\" : \"홍길동\"}"
		String msg = "{\"name\" : \"홍길동\"}";
		
		return msg; // @ResponseBody어노테이션을 사용하지 않으면 이것이 jsp명임. 사용하면 순수한 문자열 데이터로 클라이언트에게 보내줌.
	}
	
	@GetMapping("doB")
	public ResponseEntity<String> doB() {
		ResponseEntity<String> entity = null;
		
		// 1) body : 서버에서 클라이언트에게 전송하는 데이터
		String msg = "{\"name\" : \"홍길동\"}"; // json 포맷의 문자열
		
		// 2) header에 body데이터를 해석하기 위한 MIME-TYPE과 인코딩정보 작업
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		
		// 3) 상태코드 : HttpStatus.OK -> 성공
		entity = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
}
