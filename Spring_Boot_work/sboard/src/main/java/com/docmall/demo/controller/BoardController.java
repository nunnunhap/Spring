package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
	
	// DI : 생성자 주입방식
	private final BoardService boardService;
	
	
}
