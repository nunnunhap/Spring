package com.docmall.basic.admin.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.basic.admin.category.AdminCategoryService;
import com.docmall.basic.admin.category.AdminCategoryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/*")
@Slf4j
public class AdminProductController {

	private final AdminProductService adminProductService;
	private final AdminCategoryService adminCategoryService;
	
	// 상품등록폼
	@GetMapping("pro_insert")
	public void pro_insertForm(Model model) {
		
		List<AdminCategoryVo> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", cate_list);
	}
	
	// 2차 카테고리
	
	
	
	
	
	
	
	
	
}
