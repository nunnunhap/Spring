package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.docmall.demo.domain.ProductVO;

// 클라이언트의 결과값을 jsp로 동작하여 받고자 하는 것이 아니라 JSON포맷의 데이터로 받고자 할 때의 예제 @ResponseBody 사용
@Controller
public class SampleController6 {
	// 로그 객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController6.class);
	
	// ProductVO vo객체를 JSON으로 변환할 때 사용하는 라이브러리 : jackson-databind라이브러리.
	@RequestMapping("/doJSON")
	public @ResponseBody ProductVO doJSON() {
		ProductVO vo = new ProductVO("사과", 10000);
		logger.info("called doJSON ... " + vo); // vo.toString() 호출
		// 참조타입의 초기값은 null이라서 이렇게 null처리해주면 일단 에러를 막을 수 있음.

		// vo가 가지고 있는 heap영역의 정보(사과, 10000)을 클라이언트에게 리턴. 꼭 @ResponseBody 어노테이션 사용해야 함.
		// 이 어노테이션을 쓰지 않으면 jsp로 인식되어버림. 쓰게 되면 JSON으로 보내줌.
		// {"name":"사과","price":10000.0} JSON문자열이 클라이언트(브라우저)로 응답된다.
		return vo;	
	}
}
