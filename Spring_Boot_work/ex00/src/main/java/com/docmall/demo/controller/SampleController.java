package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// 클라이언트(브라우저)로부터 요청을 받아 실행하는 클래스는 @Controller 어노테이션을 클래스 수준으로 가지고 있어야 함.
@Controller
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	// 클라이언트(브라우저)에서 메서드를 호출하고자 할 경우에는 매핑주소를 설정해야 함.
	// 즉, doA()를 호출하려면 주소를 가져야 함. @RequestMapping이 매핑주소 설정하는 어노테이션
	@RequestMapping("/doA") //매핑주소 이름과 메서드명은 안 같아도 상관없음. 그냥 구분하기 좋게 같은 이름으로 하는 것.
	public void doA() {
		// doA()가 클라이언트가 브라우저를 이용해서 호출했는지 아는 방법
		logger.info("doA called ..."); // System.out.println();을 대체함.
		// System.out.println("doA"); // 사용할 순 있는데 성능적으로 떨어져
	}
	
	@RequestMapping("/doB") // 매핑주소와 메서드명은 안 같아도 됨.
	public void doB() {
		logger.info("doB called ...");
	}
	
	@RequestMapping("/doC")
	public void doC() {
		logger.info("doC called ...");
	}
}

