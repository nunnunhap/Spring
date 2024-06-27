package com.docmall.basic.admin.category;

import java.util.List;

public interface AdminCategoryMapper {
	
	// 1차 카테고리 목록
	List<AdminCategoryVo> getFirstCategoryList();
	
	// 2차 카테고리 목록
	List<AdminCategoryVo> getSecondCategoryList(int cat_prtcode);
	
	
}
