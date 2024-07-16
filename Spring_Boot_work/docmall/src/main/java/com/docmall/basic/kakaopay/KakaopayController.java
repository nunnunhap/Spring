package com.docmall.basic.kakaopay;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.basic.cart.CartProductVo;
import com.docmall.basic.cart.CartService;
import com.docmall.basic.order.OrderService;
import com.docmall.basic.order.OrderVo;
import com.docmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kakao/*")
public class KakaopayController {
	
	private final KakaopayService kakaoPayService;
	private final CartService cartService;
	private final OrderService orderService;
	
	private OrderVo vo;
	private String mbsp_id;
	
	@GetMapping("kakaoPayRequest")
	public void kakaoPayRequest() {
		
	}

	@ResponseBody
	@GetMapping(value = "/kakaoPay")
	public ReadyResponse kakaoPay(OrderVo vo, HttpSession session) {
		
		log.info("주문자 정보1 : " + vo);
		
		// 1) 결제 준비요청(ready)
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		this.mbsp_id = mbsp_id;

		List<CartProductVo> cart_list = cartService.cart_list(mbsp_id);
		
		String itemName = "";
		int quantity = 0;
		int totalAmount = 0;
		int taxFreeAmount = 0;
		int vatAmount = 0;
		
		for(int i = 0; i < cart_list.size(); i++) {
			itemName += cart_list.get(i).getPro_name() + "/";
			quantity += cart_list.get(i).getCart_amount();
			totalAmount += cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount();
		}
		
		String partnerOrderId = mbsp_id;
		String partnerUserId = mbsp_id;
		
		ReadyResponse readyResponse = kakaoPayService.ready(partnerOrderId, partnerUserId, itemName, 
				quantity, totalAmount, taxFreeAmount, vatAmount);
		log.info("kakaoPay 응답데이터 : " + readyResponse);
		
		// 주문정보
		this.vo = vo;
		
		return readyResponse;
	}
	
	@GetMapping("/approval")
	public void approval(String pg_token) {
		log.info("pg_token : " + pg_token);
		
		// 2) 결제 승인 요청
		String approveResponse = kakaoPayService.approve(pg_token);
		log.info("최종결과 : " + approveResponse);
		// 주문정보 DB 저장
		// aid값이 존재하면
		
		// 트랜잭션(all or nothing)으로 반드시 처리 : 주문 테이블, 주문 상세 테이블, (결제테이블: 특히 무통장 입금), 장바구니 비우기
		if(approveResponse.contains("aid")) { // aid는 성공적 결제 시 넘어오는 키값
			log.info("주문자 정보2 : " + vo);
			orderService.order_process(vo, mbsp_id);
		}
	}
	
	@GetMapping("/cancel")
	public void cancel() {
		
	}
	
	@GetMapping("/fail")
	public void fail() {
		
	}
	
	
	
	
}
