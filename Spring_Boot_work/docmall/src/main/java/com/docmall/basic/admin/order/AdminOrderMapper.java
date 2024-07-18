package com.docmall.basic.admin.order;

import java.util.List;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.order.OrderVo;

public interface AdminOrderMapper {

	List<OrderVo> order_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	OrderVo order_info(Long ord_code);
	
	List<OrderDetailInfoVo> order_detail_info(Long ord_code);
	
	
	
	
	
	
}
