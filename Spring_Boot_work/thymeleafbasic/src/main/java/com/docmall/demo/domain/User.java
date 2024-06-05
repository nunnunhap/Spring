package com.docmall.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 만듦.
@Data
public class User {
	
	private String username;
	private int age;
	
}
