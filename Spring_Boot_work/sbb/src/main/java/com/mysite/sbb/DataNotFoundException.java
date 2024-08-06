package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 사용자 정의 예외 클래스
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
	// reason = "entity not found" 조회한 데이터가 없다.
	private static final long serialVersionID = 1L;
	public DataNotFoundException(String message) {
		super(message);
	}
	
	
	
}
