package com.docmall.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.dto.SampleDTO;
import com.docmall.demo.dto.SampleDTOList;

@RequestMapping("/sample4/*")
@Controller
public class SampleController4 {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(SampleController4.class);		
	
	@GetMapping("basicForm")
	public void basicForm() {
		logger.info("basicForm called ...");
	}
	
	// 스프링은 자바랑 다르게 파라미터에 클래스가 들어오면 생성자가 내부적으로 들어와서 알아서 “객체가 생성”됨.(힙에 기억장소가 생성되었다)
	// 그 다음 데이터(setAge, setName)가 들어와지며 힙의 기억장소에 데이터 저장.
	// 생성자 호출 -> setAge(), setName() 각각 호출되어 dto객체가 가리키는 힙 영역의 기억장소에 데이터 저장
	@PostMapping("basicPro")
	public void basicPro(SampleDTO dto) {
		logger.info("1인(단일) 회원정보: " + dto);
	}
	
	/*******************************************************************/
	
	@GetMapping("basicForm2")
	public void basicForm2() {
		logger.info("basicForm2 called ...");
	}
	
	@PostMapping("basicPro2")
	public void basicPro2(SampleDTOList list) {
		logger.info("다수 회원정보: " + list);
	}
}
