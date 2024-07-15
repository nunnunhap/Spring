package com.docmall.basic.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	
	
	
	
	
	
}
