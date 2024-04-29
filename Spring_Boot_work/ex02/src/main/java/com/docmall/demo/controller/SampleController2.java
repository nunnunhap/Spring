package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;

// 게시판 기능에 사용하는 컨트롤러 클래스
/*
	주소
	글쓰기(get) /board/write
	글저장(post) /board/write
	글목록(get) /board/list
*/
@RequestMapping("/board/*")
@Controller
public class SampleController2 {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController2.class);
	
	// 글쓰기 폼
	@GetMapping("write")
	public void write() {
		logger.info("글쓰기");
	}
	
	// 글쓰기 저장 : 저장은 보통 @PostMapping주소로 만듦.
	// db의 테이블에 insert, update, delete 작업을 하는 매핑주소는 현재까지는 @PostMapping으로 주소 설정
	// 메서드의 리턴타입은 String으로 해야 함. redirect를 써서 저장하고 이동하려고.
	// 위와 동일한 write지만 매개변수가 다르면 다시 사용가능함. 메서드 오버로딩
	@PostMapping("write")
	public String write(BoardVO vo) {
		// 사용자가 입력한 게시판 db를 BoardVO vo로 받고 db의 게시판 테이블에 저장
		// jsp명이 아닌 해당 주소로 이동.
		// redirect는 꼭 String으로 써야 한다는 규칙이 있어서 method return타입도 String으로 쓰는 것.
		return "redirect:/board/list";
	}
	
	// 글쓰기 목록
	@GetMapping("list")
	public void list() {
		logger.info("리스트");
	}
}
