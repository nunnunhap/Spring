package com.docmall.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.service.UserInfoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 매핑주소를 관리하는 클래스를 컨트롤러 클래스라고 하고, 그 컨트롤러 클래스는 @Controller라는 어노테이션을 달아야 함.
// @Controller를 달았을 때 자동으로 bean이 생성 : userInfoController
@Controller
@RequestMapping("/userinfo/*")
@Slf4j
@RequiredArgsConstructor
public class UserInfoController {
	// DI
	private final UserInfoService userInfoService;
	private final PasswordEncoder passwordEncoder;
	
	// 회원가입폼
	@GetMapping("join")
	public void joinForm() {
		log.info("called join...");
	}
	
	// ID 중복체크
	// DB 연동작업 등 문제가 일어날 소지가 있을 시 예외작업 진행함.
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String u_id) throws Exception {
		log.info("아이디 : " + u_id);
		ResponseEntity<String> entity = null;
		
		// 아이디가 사용 가능한지 아닌지부터 체크
		String idUse = "";
		if(userInfoService.idCheck(u_id) != null) {
			idUse = "no"; // 사용불가능
		}else {
			idUse = "yes"; // 사용가능
		}
		
		entity = new ResponseEntity<String> (idUse, HttpStatus.OK);
		
		return entity;
	}
	
	// insert, update, delete 작업을 하고, 다른 주소로 이동할 경우에는 메서드의 리턴타입은 String으로 해야 함.
	// 이유는? redirect : 사용해야 하므로.
	// db와 관련된 작업은 예외 작업을 보통 해줌.
	// 회원가입 저장
	@PostMapping("join")
	public String join(UserInfoVO vo) throws Exception {
		
		// log.info("비밀번호 : " + passwordEncoder.encode(vo.getU_pwd()));
		log.info("회원정보 : " + vo);
		// 암호화 길이는 항상 60바이트(길게 입력했든 작게 입력했든 암호화를 60바이트로 한단 뜻임)
		
		userInfoService.join(vo);
		
		return "redirect:/list";
	}
	
	// 로그인 폼
	@GetMapping("login")
	public void loginForm() {
		
	}
	
	@PostMapping("login") // db와 연관있으니 Exception 사용하기
	public String login(String u_id, String u_pwd, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";
		String url = "/"; // "/" 메인주소
		
		if(vo != null) { // 아이디가 존재하는 경우
			// 비밀번호 비교 // vo.getU_pwd()는 db에서 읽어온 암호화된 비밀번호
			if(passwordEncoder.matches(u_pwd, vo.getU_pwd())) { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하는 것이라면
				session.setAttribute("login_status", vo);
			}else { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW";
				url = "/userinfo/login"; // 로그인폼 주소
			}
		}else { // 아이디가 존재하지 않을 경우
			msg = "failID";
			url = "/userinfo/login"; // 로그인폼 주소
		}
		
		rttr.addFlashAttribute("msg", msg); // JSP에서 msg변수 사용 목적
		
		return "redirect:" + url; // 메인으로 이동
	}
	
	
	
	
	
	
	
	
}
