package com.docmall.basic.admin.product;

import java.util.List;

import com.docmall.basic.common.dto.Criteria;

public interface AdminProductMapper {
	
	void pro_insert(ProductVo vo);
	
	List<ProductVo> pro_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	ProductVo pro_edit(Integer pro_num);
	
	void pro_edit_ok(ProductVo vo);
	
	
}
