package com.docmall.basic.admin.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	
	public void pro_delete(Integer pro_num) {
		adminProductMapper.pro_delete(pro_num);
	}

	// 체크된 개수만큼 반복문이 동작되어 커넥션 객체수가 진행이 되기 때문에 성능적으로 권장사항 아님.
	// 사용자에서 요청되는 작업일 경우, 많은 사용자로 인한 동시작업엔 성능이 떨어짐.
	// 관리자에서 요청되는 작업일 경우, 문제되지 않음.
	public void pro_checked_modify1(List<Integer> pro_num_arr, List<Integer> pro_price_arr, List<String> pro_buy_arr) {
		for(int i = 0; i < pro_num_arr.size(); i++) {
			adminProductMapper.pro_checked_modify1(pro_num_arr.get(i), pro_price_arr.get(i), pro_buy_arr.get(i));
		}
	}
	
	public void pro_checked_modify2(List<Integer> pro_num_arr, List<Integer> pro_price_arr, List<String> pro_buy_arr) {
		List<ProductDTO> pro_modify_list = new ArrayList<>();
		
		for(int i = 0; i < pro_num_arr.size(); i++) {
			ProductDTO productDTO = new ProductDTO(pro_num_arr.get(i), pro_price_arr.get(i), pro_buy_arr.get(i));
			pro_modify_list.add(productDTO);
		}
		adminProductMapper.pro_checked_modify2(pro_modify_list);
	}
	
}
