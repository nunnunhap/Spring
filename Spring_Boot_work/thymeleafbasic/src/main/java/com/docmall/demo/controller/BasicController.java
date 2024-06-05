package com.docmall.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/basic/*")
public class BasicController {
	
	// data : 태그가 포함된 텍스트 문자열
	@GetMapping("doA")
	public String doA(Model model) {
		model.addAttribute("data", "Hello <b>Spring!</b>");
		
		return "/basic/doA"; // thymeleaf 파일 /basic/doA.html
	}
	
	@GetMapping("doB")
	public String doB(Model model) {
		User userhong = new User("홍길동", 100); // db에서 회원정보 가져오게 됨.
		model.addAttribute("user", userhong);
		
		return "basic/doB"; // thymeleaf 파일 /basic/doB.html
	}
	
	// List객체 출력(컬렉션) ex)회원목록
	@GetMapping("doC")
	public String doC(Model model) {
		User userSon = new User("손흥민", 30);
		User userLee = new User("이강인", 33);
		User userKim = new User("김민재", 22);
		User userHwang = new User("황희찬", 55);
		
		/*
		List<User> list = new ArrayList<User>();
		list.add(userSon);
		list.add(userLee);
		list.add(userKim);
		list.add(userHwang);
		위와 아래 한 줄은 같은 표현법 */
		
		List<User> list = new ArrayList<User>(Arrays.asList(userSon, userLee, userKim, userHwang));

		model.addAttribute("list", list);
		
		return "basic/doC";
	}
	
	// Map 객체를 출력
	@GetMapping("doD")
	public String doD(Model model) {
		Map<String, User> map = new HashMap<>();
		map.put("userA", new User("손흥민", 33));
		map.put("userB", new User("이강인", 28));
		
		model.addAttribute("userMap", map);
		
		return "basic/doD"; // thymeleaf 파일명. /basic/doD.html
	}
	
	// if문 사용목적. 나이가 18세 이상이면 성년, 이하면 미성년
	@GetMapping("doE")
	public String doE(Model model) {
		User user1 = new User("이몽룡", 33);
		User user2 = new User("춘향이", 15);
		
		List<User> list = new ArrayList<User>(Arrays.asList(user1, user2));
		model.addAttribute("list", list);
		
		return "basic/doE"; // thymeleaf 파일명. /basic/doE.html
	}
	
	// 링크기본주소 + 쿼리스트링 + 경로주소
	// HomeController의 매핑주소 사용
	@GetMapping("doF")
	public String doF(Model model) {
		model.addAttribute("param1", "user01");
		model.addAttribute("param2", 1234);
		
		return "basic/doF"; // thymeleaf 파일명. /basic/doF.html
	}
	
	// 리터럴 예제. 리터럴 : 데이터
	// 즉, 문자열과 변수를 연결할 때 -> |문자열 ${변수}|
	@GetMapping("/doG")
	public String doG(Model model) {
		model.addAttribute("data", "Spring!");
		
		return "basic/doG"; // thymeleaf 파일명. /basic/doG.html
	}
	
	// 연산예제
	@GetMapping("doH")
	public String doH(Model model) {
		model.addAttribute("nullData", null);
		model.addAttribute("data", "Spring");
		
		return "basic/doH"; // thymeleaf 파일명. /basic/doH.html
	}
	
	
	
	
}
