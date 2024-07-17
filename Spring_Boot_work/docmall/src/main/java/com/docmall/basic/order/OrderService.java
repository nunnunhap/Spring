package com.docmall.basic.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docmall.basic.cart.CartMapper;
import com.docmall.basic.payinfo.PayInfoMapper;
import com.docmall.basic.payinfo.PayInfoVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderMapper orderMapper;
	private final PayInfoMapper payInfoMapper;
	private final CartMapper cartMapper;
	
	
	@Transactional
	public void order_process(OrderVo vo, String mbsp_id, String paymethod, String p_status, String payinfo) {
		// 1) 주문테이블(insert)
		vo.setMbsp_id(mbsp_id);
		orderMapper.order_insert(vo);
		
		// 2) 주문 상세 테이블(insert)
		orderMapper.orderDetail_insert(vo.getOrd_code(), mbsp_id);
		
		// 3) 결제 테이블(insert)
		PayInfoVo p_vo = PayInfoVo.builder()
				.ord_code(vo.getOrd_code())
				.mbsp_id(mbsp_id)
				.p_price(vo.getOrd_price())
				.paymethod(paymethod) // 무통장 입금
				.payinfo(payinfo)
				.p_status(p_status) // 미납
				.build();
		
		payInfoMapper.payInfo_insert(p_vo);
		
		// 4) 장바구니 테이블 삭제(delete)
		cartMapper.cart_empty(mbsp_id);
	}
	
}
