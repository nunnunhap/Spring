package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.ProductVO;

@Controller
public class SampleController4 {
	private static final Logger logger = LoggerFactory.getLogger(SampleController4.class);
	
	// 메서드에 파라미터에 제공한 값을 JSP에서 사용하고 싶을 경우 @ModelAttribute("이름")을 이용하자.
	// http://localhost:9095/doJ?name=홍길동&age=460
	@RequestMapping("/doJ")
	public String doJ(@ModelAttribute("name") String name, @ModelAttribute("age") int age) {
		logger.info("이름은? : " + name);
		logger.info("나이는? : " + age);
		
		return "testJ";
	}
	
	// product 객체의 정보를 jsp파일에서 사용하고 싶을 경우
	@RequestMapping("/doK")
	public String doK(Model model) {
		// 실제론 이렇게 작업 안 하고 db에서 상품테이블에 대한 정보를 생성해 옴.
		ProductVO product = new ProductVO("사과", 10000);
		
		logger.info("상품정보? " + product); // product.toString()메서드 호출
		
		// "product"는 리턴값에 써있는 productInfo.jsp에서 참조할 이름.
		// product는 객체. 두 이름이 달라도 상관없음.
		model.addAttribute("product", product);
		
		return "productInfo";
	}
}
