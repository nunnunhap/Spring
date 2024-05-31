package com.docmall.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.docmall.demo.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

// 용도? LoginInterceptor 인터셉터 클래스를 위한 매핑주소 설정
@RequiredArgsConstructor
@Component // 이 어노테이션을 쓰지 않으면 빈 생성이 안됨. loginInterceptor빈으로 관리됨. bean을 관리하는 곳을 스프링 컨테이너라고 함.
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor) // 아래의 매핑주소로 요청이 들어오면 loginInterceptor가 작동하겠단 뜻임.
				.addPathPatterns("/userinfo/mypage", "/userinfo/changepw", "/userinfo/delete", "/userinfo/ajax");
		// 특정주소가 전부 인증사용자만 써야 하는 경우는 "/userinfo/*"(userinfo바로 밑의 레벨), "/userinfo/**"(userinfo의 모든 하위레벨)
	}
	
	
	
	
}
