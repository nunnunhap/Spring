package com.mysite.sbb.user;

import lombok.Getter;

// 열거형 데이터 타입
@Getter
public enum UserRole {
	
	ADMIN("ROLE_ADMIN"), USER("ROLE_USER");
	
	UserRole(String value) {
		this.value = value;
	}
	
	private String value;
}