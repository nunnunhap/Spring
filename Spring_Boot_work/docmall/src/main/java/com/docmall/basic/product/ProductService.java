package com.docmall.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.docmall.basic.admin.product.ProductVo;
import com.docmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductMapper productMapper;
	
	public List<ProductVo> pro_list(int cat_code, Criteria cri) {
		return productMapper.pro_list(cat_code, cri);
	}
	
	public int getCountProductByCategory(int cat_code) {
		return productMapper.getCountProductByCategory(cat_code);
	}
	
	public ProductVo pro_info(int pro_num) {
		return productMapper.pro_info(pro_num);
	}
	
}
