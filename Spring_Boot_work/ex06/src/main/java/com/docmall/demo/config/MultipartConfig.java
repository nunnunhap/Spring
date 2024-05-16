package com.docmall.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/*
 * 스프링 부트 2.7에선 multipart가 기본 bean으로 등록되어 있음.
 * 스프링 부트 3이상부터는 multipart에서 설정 클래스를 생정하고 bean으로 등록해야 함.
 * 
 * */

@Configuration // 환경설정과 관련된 클래스는 이 어노테이션 사용함.
public class MultipartConfig {
	
	@Bean // 객체를 스프링시스템에서 관리하고 싶다면 이 어노테이션 달음. 스프링에서 자동으로 이 메소드를 호출해서 bean이란 성격으로 주입함.
	// @Bean을 달지 않으면 이 메서드를 수동으로 호출해야만 함.
	public MultipartResolver multipartResolver() {
		
		return new StandardServletMultipartResolver(); // 자바의 객체생성구문
	}
	
	/*
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(null);
		factory.setMaxRequestSize(null);
		factory.setMaxFileSize(null);
		
		return factory.createMultipartConfig();
	}*/
	
	
	
	
	
	
}
