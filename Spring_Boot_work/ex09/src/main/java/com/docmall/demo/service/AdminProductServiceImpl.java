package com.docmall.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.demo.domain.CategoryVO;
import com.docmall.demo.mapper.AdminProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {
	
	// DI
	public final AdminProductMapper adminProductMapper;
	
	@Override
	public List<CategoryVO> categoryAllList() {
		return adminProductMapper.categoryAllList();
	}
	
	@Override
	public List<CategoryVO> subCategoryList(Integer c_pcode) {
		return adminProductMapper.subCategoryList(c_pcode);
	}
	
}
