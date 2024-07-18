package com.docmall.basic.admin.order;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.common.dto.PageDTO;
import com.docmall.basic.order.OrderVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/order/*")
public class AdminOrderController {

	private final AdminOrderService adminOrderService;
	
	@GetMapping("order_list")
	public void order_list(Criteria cri, Model model) {
		log.info("Criteria : " + cri);
		cri.setAmount(2);
		
		List<OrderVo> order_list = adminOrderService.order_list(cri);
		int totalCount = adminOrderService.getTotalCount(cri);
		
		model.addAttribute("order_list", order_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 주문상세 load()메서드를 사용하고 있기 때문에 ajax지만 thymeleaf작업을 하고 그것을 불러들이는 것임. 그래서 ResponseEntity를 사용하지 않음.
	@GetMapping("/order_detail_info")
	public void order_detail_info(Long ord_code, Model model) throws Exception {
		// 주문자(수령인) 정보
		OrderVo vo = adminOrderService.order_info(ord_code);
		
		// 주문상세정보
		List<OrderDetailInfoVo> dvo = adminOrderService.order_detail_info(ord_code);
		
		// 결제정보
		
		
	}
	
	
	
	
	
	
}
