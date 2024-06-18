package com.docmall.basic.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security 라이브러리에서 제공하는 기능을 사용하기 위한 설정에 해당하는 클래스 파일
// 설정 목적으로 사용하는 클래스에는 어노테이션 적용. @Bean과 쌍으로 다녀야 함.
@Configuration // 설정 목적으로 사용하고자 하는 클래스는 @Configuration이란 어노테이션을 달아야 하는 규칙이 있음.
// @EnableWebSecurity
public class SecurityConfig {
	
	// 스프링 시큐리티 설정(v2.7과 다름)
	// @Bean 사용 시, 의존성 주입을 알아서 해줌. 안 쓰면 직접 new 생성자 사용해야 함.
	// 간혹 @Bean 생성 안 하고 new 사용한 경우 있는데, 딱 한 곳에만 사용하거나 하면 그렇게 사용하기도 함.
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//				.csrf((csrf) -> csrf.disable());
//		//		.cors((c) -> c.disable())
//		//		.headers((headers) -> headers.disable());
//		return http.build();
//	}
	
	// 암호화기능 bean 생성 passwordEncoder bean 자동생성
	@Bean
	PasswordEncoder passwordEncoder() {
		// BCryptPasswordEncoder의 부모 인터페이스가 PasswordEncoder
		// 그래서 UserInfoController에서 일부러 부모 인터페이스로 선언한 것
		return new BCryptPasswordEncoder();
	}
}
