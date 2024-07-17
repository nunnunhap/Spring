package com.docmall.basic.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.docmall.basic.cart.CartProductVo;
import com.docmall.basic.cart.CartService;
import com.docmall.basic.cart.CartVo;
import com.docmall.basic.common.util.FileManagerUtils;
import com.docmall.basic.user.UserService;
import com.docmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/order/*")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final CartService cartService; 
	private final UserService userService;
	
	/* 1, 2는 동일하게 CartVo vo 사용. 3은 CartVo vo 사용 안함.
	 * 1) pro_list의 모달 바로구매
	 * 2) pro_detail의 바로구매
	 * 3) 장바구니
	 */
	@GetMapping("/orderinfo")
	public String orderinfo(@RequestParam(value = "type", defaultValue = "direct") String type, CartVo vo, HttpSession session, Model model) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		// 1) 장바구니 저장   . 은 자바에서 참조연산자
		if(!type.equals("cartorder")) {
			// 3번 타입으로 진행되면 이 코드는 에러를 맞음. 그래서 type이라는 파라미터를 하나 만듦.
			cartService.cart_add(vo);			
		}
		
		// 2) 주문하기
		List<CartProductVo> cart_list = cartService.cart_list(mbsp_id);
		
		int total_price = 0;
		cart_list.forEach(d_vo -> {
			d_vo.setPro_up_folder(d_vo.getPro_up_folder().replace("\\", "/"));
//			total_price += d_vo.getCart_amount() * d_vo.getPro_price();
		});
		
		for(int i = 0; i < cart_list.size(); i++) {
			total_price += (cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount());
		}
		
		model.addAttribute("cart_list", cart_list);
		model.addAttribute("total_price", total_price);
		
		return "/order/orderinfo";
	}
	
	// 주문자와 동일
	@GetMapping("/ordersame")
	public ResponseEntity<UserVo> ordersame(HttpSession session) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		
		ResponseEntity<UserVo> entity = null;
		entity = new ResponseEntity<UserVo> (userService.login(mbsp_id), HttpStatus.OK);

		return entity;
	}
	
	// 무통장 입금
	@PostMapping("/ordersave")
	public String ordersave(OrderVo vo, String pay_nobank, String pay_nobank_user, HttpSession session) throws Exception {
		
		log.info("주문정보 : " + vo);
		log.info("입금은행 : " + pay_nobank);
		log.info("예금주 : " + pay_nobank_user);
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		String payinfo = pay_nobank + "/" + pay_nobank_user;
		
		orderService.order_process(vo, mbsp_id, "무통장입금", "미납", payinfo);
		
		return "redirect:/주문완료페이지";
	}
	
	// 주문완료
	@GetMapping("/ordercomplete")
	public void ordercomplete() throws Exception {
		
	}
	
	
	
	
	
}
