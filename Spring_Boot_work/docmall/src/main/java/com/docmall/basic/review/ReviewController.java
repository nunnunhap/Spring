package com.docmall.basic.review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// pro_detail.html 내 상품후기 처리
@RestController // 이 안의 모든 메서드는 ajax로 요청받음.
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review/*")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	
	
	
}
