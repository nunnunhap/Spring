package com.docmall.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	// 유효성 검사 적용, 입력폼
	@GetMapping("write")
	public String write(Model model) {
		model.addAttribute("board", new BoardVO()); // input태그의 id, name, value 속성 자동추가
		
		return "/board/write";
	}
	
	@PostMapping("write") // 데이터를 받는 이 클래스 BoardVO를 유효성검사를 하도록 해줌.
	public String write(@Valid @ModelAttribute("board") BoardVO board, BindingResult bindingResult) {
		
		// 사용자가 입력한 데이터가 유효성 검사 어노테이션에 의하여 문제가 생기면 아래 코드가 true가 됨.
		if(bindingResult.hasErrors()) {
			return "/board/write"; // thymeleaf페이지 : write.html
		} // 유효성 검사를 하고 에러가 있어서 다시 write로 돌아갈 땐 "board"가 없음. 그래서 위의 @ModelAttribute("board")를 넣어준 것.
		log.info("called write...");
		return "redirect:/board/list"; // 주소
	}
	
	
	
	
}
