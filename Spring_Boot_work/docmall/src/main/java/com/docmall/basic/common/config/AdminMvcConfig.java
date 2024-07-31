package com.docmall.basic.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.docmall.basic.common.interceptor.AdminInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdminMvcConfig implements WebMvcConfigurer {
// LoginConfig라던가 이런 식으로 이름 지으면 됨.
	
	private final AdminInterceptor adminInterceptor;
	
	// 인터셉터 매핑주소 설정에서 제외되는 경로 작업
	private static final String[] EXCLUDE_PATHS = {
			"/admin/",
			"/admin/admin_ok"
	};
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminInterceptor)
				.addPathPatterns("/admin/**")  // admin으로 시작하는 하위레벨의 깊이에 상관없이 모든 주소를 설정
				.excludePathPatterns(EXCLUDE_PATHS); // 직접 적어도 되긴 하나 관리를 위하여 EXCLUDE_PATHS로 따로 빼냄.
	}
	
	
	
}
