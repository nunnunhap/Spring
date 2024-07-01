package com.docmall.basic.admin.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.basic.admin.category.AdminCategoryVo;
import com.docmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductService {

	private final AdminProductMapper adminProductMapper;
	
	
	public void pro_insert(ProductVo vo) {
		adminProductMapper.pro_insert(vo);
	}
	
	List<ProductVo> pro_list(Criteria cri) {
		return adminProductMapper.pro_list(cri);
	}
	
	int getTotalCount(Criteria cri) {
		return adminProductMapper.getTotalCount(cri);
	}
	
	public ProductVo pro_edit(Integer pro_num) {
		return adminProductMapper.pro_edit(pro_num);
	}
	
	public void pro_edit_ok(ProductVo vo) {
		adminProductMapper.pro_edit_ok(vo);
	}
	

	
	
	
}
