package com.docmall.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.basic.admin.product.ProductVo;
import com.docmall.basic.common.dto.Criteria;

public interface ProductMapper {
	
	List<ProductVo> pro_list(@Param("cat_code") int cat_code, @Param("cri") Criteria cri);
	
	int getCountProductByCategory(int cat_code); // 페이징에선 항상 데이터 갯수가 따라다녀야 함.
	
	ProductVo pro_info(int pro_num); // 상품 팝업 및 상세설명
	
	
	
}
