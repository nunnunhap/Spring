package com.docmall.basic.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCategoryService {
	
	private final AdminCategoryMapper admincategoryMapper;
	
	// 1차 카테고리 목록
	public List<AdminCategoryVo> getFirstCategoryList() {
		return admincategoryMapper.getFirstCategoryList();
	}
	
	// 2차 카테고리 목록
	public List<AdminCategoryVo> getSecondCategoryList(int cat_prtcode) {
		return admincategoryMapper.getSecondCategoryList(cat_prtcode);
	}
	
	// 2차 카테고리 정보를 이용한 1차 카테고리 정보
	public AdminCategoryVo getFirstCategoryBySecondCategory(int cat_code) {
		return admincategoryMapper.getFirstCategoryBySecondCategory(cat_code);
	}
	
	
	
}
