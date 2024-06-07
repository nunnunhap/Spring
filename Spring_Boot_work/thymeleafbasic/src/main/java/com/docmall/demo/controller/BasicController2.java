package com.docmall.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/basic2/*")
public class BasicController2 {
	
	// th:block 예제
	@GetMapping("ex01")
	public void ex01(Model model) {
		List<User> list = new ArrayList<>();
		list.add(new User("userA", 10));
		list.add(new User("userB", 20));
		list.add(new User("userC", 30));
		
		model.addAttribute("users", list); // users객체 3개
	}
	
	// 자바스크립트 예제
	@GetMapping("ex02")
	public void ex02(Model model) {

		model.addAttribute("user", new User("user01", 40));
	}
	
	/* 기본 객체.
	 * 스프링 3.0 이전에서는 지원되었으나 3.0부터 지원 안함.
	 * ${#request}, ${#response}, ${#session}, ${#servletContext}, ${#locale}
	 * 
	 * 스프링 3.0 이후에도 지원(현재 3.3임)
	 * ${#locale}
	 * 
	 * 위의 내장객체를 3.0 이후에서도 사용하고 싶은 경우 model로 추가작업함.
	 */
	@GetMapping("ex03") // 메서드 리턴타입을 void로 하면 작동이 안됨.(정식지원은 아님)
	public String ex03(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("request", request);
		model.addAttribute("response", response);
		model.addAttribute("servletContext", request.getServletContext());
		
		return "/basic2/ex03";
	}
	
	// param객체, session객체, bean객체 사용
	// param객체 사용 URL. /basic2/ex04?userid=doccomsa
	@GetMapping("ex04")
	public String ex04(HttpSession session) {
		session.setAttribute("login_status", "user01");
		
		return "/basic2/ex04";
	}
	
	// bean객체 사용. 전역적인 기능 지원 ex03, ex04... 모두 사용 가능함.
	@Component("helloBean")
	static class HelloBean {
		public String hello(String data) {
			return "Hello " + data;
		}
	}
	
	// thymeleaf 유틸리티 객체 - temporals : 자바8 날짜포맷 지원
	// 날짜 데이터  1) Date, 2) Calendar, 3) LocalDateTime, LocalDate, LocalTime
	@GetMapping("ex05")
	public String ex05(Model model) {
		model.addAttribute("localDateTime", LocalDateTime.now());
		
		return "/basic2/ex05";
	}
	
	// thymeleaf 유틸리티 객체 - numbers
	@GetMapping("ex06")
	public String ex06(Model model) {
		model.addAttribute("number", 5);
		
		return "/basic2/ex06";
	}
	
	
	
	
	
}