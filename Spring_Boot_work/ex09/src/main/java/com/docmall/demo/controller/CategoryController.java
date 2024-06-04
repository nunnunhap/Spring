package com.docmall.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.CategoryVO;
import com.docmall.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category/*")
public class CategoryController {
	
	// DI
	public final CategoryService categoryService;
	
	// 카테고리 출력 // DB니까 그래도 Exception처리하기
	@GetMapping("catelist")
	public void categoryList(Model model) throws Exception {
		// GlobalControllerAdvice.java 파일에서 1차 카테고리 model작업으로 아래는 필요 없음.
		/* List<CategoryVO> list = categoryService.categoryAllList();
		log.info("카테고리 리스트 : " + list);
		
		model.addAttribute("cateList", list);
		*/
	}
	
	// 2차 카테고리
	@GetMapping("subcatelist")
	public ResponseEntity<List<CategoryVO>> subCateList(Integer c_pcode) throws Exception {
		ResponseEntity<List<CategoryVO>> entity = null;
		
		log.info("1차 카테고리코드 : " + c_pcode);
		
		// categoryService.subCategoryList(c_pcode)이 json변환 후 catelist.jsp로 넘어감.
		entity = new ResponseEntity<List<CategoryVO>> (categoryService.subCategoryList(c_pcode), HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
