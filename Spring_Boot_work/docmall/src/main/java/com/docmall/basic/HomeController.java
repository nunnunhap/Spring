package com.docmall.basic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.basic.admin.category.AdminCategoryService;
import com.docmall.basic.admin.category.AdminCategoryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	private final AdminCategoryService adminCategoryService;
	
	// @ResponseBody// 사용 시, return "index"는 index라는 글자가 사용됨. ResponseEntity어노테이션이 ResponseBody기능까지 포함함.
	@GetMapping("/")
	public String index(Model model) {
		log.info("기본주소 /");
		
		List<AdminCategoryVo> user_cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("user_cate_list", user_cate_list);
		
		return "index"; // Tymeleaf 파일명
	}
	
	
	
}
