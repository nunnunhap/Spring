package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 1. 다른 매핑주소를 이동하는 방법
 2. RedirectAttributes 인터페이스 사용. addAttribute 메서드, addFlashAttribute() 메서드
 */
@Controller
public class SampleController5 {
	// 로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(SampleController5.class);
	
	// 클라이언트 /doL주소 요청하고 메서드가 실행된 후 jsp가 동작하는 것이 아니라 다른 매핑주소(/doM)로 이동하는 작업
	// 1) 메서드의 리턴타입은 String
	// 2) 리턴값은 "redirect:/매핑주소" 형식으로 작성해야 함.
	@RequestMapping("/doL")
	private String doL(RedirectAttributes rttr) {
		logger.info("called doL ...");
		
		// return "redirect:doM?title=spring study&idx=10";과 동일함.
		// 위처럼 return문에 직접 쓰지 않는 이유는 이걸 if, while같은 조건문에 사용할 수 있기 때문임.
		rttr.addAttribute("title", "spring study");
		rttr.addAttribute("idx", 10);
		
		// http://localhost:9095/doL?title=springstudy&idx=10
		// return "redirect:doM?title=spring study&idx=10"; // jsp파일명으로 해석하지 않음.
		return "redirect:/doM";
	}
	
	@RequestMapping("/doM") // 매핑주소가 jsp파일명이 됨.
	public void doM(String title, int idx) {
		logger.info("redirect 요청으로 실행됨");
		logger.info("title : " + title);
		logger.info("idx : " + idx);
	}
	
	/*********************************************************************************/
	@RequestMapping("/doN")
	public String doN(RedirectAttributes rttr) {
		// js용도로 많이 사용됨. 게시판 글이 삭제되었습니다. 같은거 
		rttr.addFlashAttribute("msg", "ok");
		
		return "redirect:/doO"; // redirect를 사용 시 jsp파일명이 동작되지 않음.
	}
	
	@RequestMapping("/doO")
	public void doO() {
		// addFlashAttribute()에 의하여 doO.jsp파일에서 msg키 이름 사용 가능
	}
}
