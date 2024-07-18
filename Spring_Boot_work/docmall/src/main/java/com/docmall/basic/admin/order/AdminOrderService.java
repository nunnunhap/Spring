package com.docmall.basic.admin.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.order.OrderVo;

import lombok.RequiredArgsConstructor;

@Service
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
		return adminOrderMapper.order_detail_info(ord_code);
	}
	
}
