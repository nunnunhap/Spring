package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 의존성 주입
	@Autowired
	private BoardService boardService; // 부모 인터페이스 타입으로 작업
	
	// 글쓰기 폼
	@GetMapping("write")
	public void write() {
		logger.info("called write ...");
	}
	
	// 글쓰기 저장
	@PostMapping("write")
	public String write(BoardVO vo) {
		logger.info("게시판 입력데이터 : " + vo);
		
		// db 저장 작업
		boardService.write(vo);
		
		return "redirect: /board/list";
	}
	
	// 글 목록 저장
	@GetMapping("list")
	public void list( ) {
		logger.info("리스트");
	}
	
}
