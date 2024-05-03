package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 의존성 주입작업
	@Autowired // bean 생성
	private BoardService boardService;
	
	
	
	
}
