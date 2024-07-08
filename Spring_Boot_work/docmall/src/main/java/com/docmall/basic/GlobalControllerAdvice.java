package com.docmall.basic;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.docmall.basic.admin.category.AdminCategoryService;
import com.docmall.basic.admin.category.AdminCategoryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = {"com.docmall.basic.user", "com.docmall.basic.product", "com.docmall.basic.cart", "com.docmall.basic.order"}) // 카테고리가 사용되는 컨트롤러의 패키지를 설정
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerAdvice {
	
	private final AdminCategoryService adminCategoryService;
	
	@ModelAttribute
	public void comm_test(Model model) {
		log.info("공통코드 실행");
		List<AdminCategoryVo> user_cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("user_cate_list", user_cate_list);
	}
	
	
}
