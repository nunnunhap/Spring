package com.docmall.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.CategoryVO;
import com.docmall.demo.service.AdminProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/product/*")
public class AdminProductController {
	
	// DI
	public final AdminProductService adminProductService;
	
	// 상품등록
	@GetMapping("pro_ins")
	public void pro_ins(Model model) throws Exception {
		// GlobalControllerAdvice.java 파일에서 1차 카테고리 model작업으로 아래는 필요 없음.
		// model.addAttribute("cateList", adminProductService.categoryAllList());
	}
	
	// 2차 카테고리
	@GetMapping("subcatelist")
	public ResponseEntity<List<CategoryVO>> subCateList(Integer c_pcode) throws Exception {
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>> (adminProductService.subCategoryList(c_pcode), HttpStatus.OK);
		
		return entity;
	}
	
	
}
