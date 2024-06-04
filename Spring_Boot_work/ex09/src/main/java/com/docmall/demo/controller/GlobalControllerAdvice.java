package com.docmall.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.docmall.demo.service.AdminProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Controller 어노테이션 사용 안함.
// com.docmall.demo.controller 패키지에서 필요한 1차 카테고리 model작업을 아래 파일에서 한 번만 작업하면 여러군데서 사용가능
@ControllerAdvice(basePackages = {"com.docmall.demo.controller"}) // 이 주소가 요청될 때마다 동작
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerAdvice {
	
	// DI
	public final AdminProductService adminProductService;
	
	@ModelAttribute // 반드시 달아주어야 함.
	public void categoryList(Model model) throws Exception {
		log.info("글로벌컨트롤러어드바이스 동작"); // 이것이 제일 먼저 동작시작
		// 1차 카테고리
		model.addAttribute("cateList", adminProductService.categoryAllList());
		
	} // 모든 패키지에서 이 작업을 반복하지 않아도 됨.
	
}
