package com.docmall.basic.kakaopay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kakao/*")
public class KakaopayController {
	
	private final KakaoPayService kakaoPayService;
	
	@GetMapping("kakaoPayRequest")
	public void kakaoPayRequest() {
		
	}

	@ResponseBody
	@GetMapping(value = "/kakaoPay")
	public ReadyResponse kakaoPay() {
		ReadyResponse readyResponse = kakaoPayService.ready();
		log.info("kakaoPay 응답데이터 : " + readyResponse);
		
		return readyResponse;
	}
	
	@GetMapping("/approval")
	public void approval(String pg_token) {
		log.info("pg_token : " + pg_token);
		
		// 2) 결제 승인 요청
		String approveResponse = kakaoPayService.approve(pg_token);
		
		log.info("최종결과 : " + approveResponse);
	}
	
	@GetMapping("/cancel")
	public void cancel() {
		
	}
	
	@GetMapping("/fail")
	public void fail() {
		
	}
	
	
	
	
}
