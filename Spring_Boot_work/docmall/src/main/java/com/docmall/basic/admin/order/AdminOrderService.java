package com.docmall.basic.admin.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.order.OrderVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderService {
	
	private final AdminOrderMapper adminOrderMapper;
	
	
	List<OrderVo> order_list(Criteria cri) {
		return adminOrderMapper.order_list(cri);
	}
	
	int getTotalCount(Criteria cri) {
		return adminOrderMapper.getTotalCount(cri);
	}
	
	OrderVo order_info(Long ord_code) {
		return adminOrderMapper.order_info(ord_code);
	}
	
	List<OrderDetailInfoVo> order_detail_info(Long ord_code) {
		log.info("상품정보 출력을 위한..." + adminOrderMapper.order_detail_info(ord_code));
		return adminOrderMapper.order_detail_info(ord_code);
	}
	
	void order_product_delete(@Param("ord_code")Long ord_code, @Param("pro_num")int pro_num ) {
		adminOrderMapper.order_product_delete(ord_code, pro_num);
	}
	
	void order_basic_modify(OrderVo vo) {
		adminOrderMapper.order_basic_modify(vo);
	}
	
	
	
}
