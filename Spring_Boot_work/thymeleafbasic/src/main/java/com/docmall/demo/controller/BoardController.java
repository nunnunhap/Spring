package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@GetMapping("write")
	public String write(Model model) {
		model.addAttribute("board", new BoardVO());
		
		return "/board/write";
	}
	
	@PostMapping("write") // 데이터를 받는 이 클래스 BoardVO를 유효성검사를 하도록 해줌.
	public String write(@Valid BoardVO board, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/board/write"; // thymeleaf페이지 : write.html
		}
		log.info("called write...");
		return "redirect:/board/list"; // 주소
	}
	
	
	
	
}
