package com.docmall.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.order.OrderVo;
import com.docmall.basic.payinfo.PayInfoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminOrderService {
	
	private final AdminOrderMapper adminOrderMapper;
	private final PayInfoMapper payInfoMapper;
	
	
	List<OrderVo> order_list(Criteria cri, String start_date, String end_date) {
		return adminOrderMapper.order_list(cri, start_date, end_date);
	}
	
	List<Map<String, Object>> order_list2() {
		return adminOrderMapper.order_list2();
	}
	
	int getTotalCount(Criteria cri, String start_date, String end_date) {
		return adminOrderMapper.getTotalCount(cri, start_date, end_date);
	}
	
	OrderVo order_info(Long ord_code) {
		return adminOrderMapper.order_info(ord_code);
	}
	
	List<OrderDetailInfoVo> order_detail_info(Long ord_code) {
		log.info("상품정보 출력을 위한..." + adminOrderMapper.order_detail_info(ord_code));
		return adminOrderMapper.order_detail_info(ord_code);
	}
	
	@Transactional // 서비스 계층에서 트랜잭션 처리를 해주기.
	void order_product_delete(@Param("ord_code")Long ord_code, @Param("pro_num")int pro_num ) {
		
		// 트랜잭션 성격
		// 주문상품 개별삭제
		adminOrderMapper.order_product_delete(ord_code, pro_num);
		
		// 주문테이블 주문금액 변경
		adminOrderMapper.order_tot_price_change(ord_code);
		
		// 결제테이블 주문금액 변경
		payInfoMapper.pay_tot_price_change(ord_code);
		
	}
	
	void order_basic_modify(OrderVo vo) {
		adminOrderMapper.order_basic_modify(vo);
	}
	
	
	
}
