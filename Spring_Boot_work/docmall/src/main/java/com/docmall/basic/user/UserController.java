package com.docmall.basic.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.basic.mail.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user/*")
@Slf4j
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	
	@GetMapping("join")
	public void joinForm() throws Exception {
		log.info("join");
	}
	
	// insert, update, delete 작업을 하고, 다른 주소로 이동할 경우에는 메서드의 리턴타입은 String으로 해야 함.
	// 이유는? redirect : 사용해야 하므로.
	// db와 관련된 작업은 예외 작업을 보통 해줌.
	// 회원가입 저장
	@PostMapping("join")
	public String join(UserVo vo) throws Exception {
		
		// log.info("비밀번호 : " + passwordEncoder.encode(vo.getU_pwd()));
		vo.setMbsp_password(passwordEncoder.encode(vo.getMbsp_password())); // 암호화 과정
		log.info("회원정보 : " + vo);
		// 암호화 길이는 항상 60바이트(길게 입력했든 작게 입력했든 암호화를 60바이트로 한단 뜻임)
		
		userService.join(vo);
		
		return "redirect:/";
	}
	
	// ID 중복체크
	// DB 연동작업 등 문제가 일어날 소지가 있을 시 예외작업 진행함.
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) throws Exception {
		log.info("아이디 : " + mbsp_id);
		ResponseEntity<String> entity = null;
		
		// 아이디가 사용 가능한지 아닌지부터 체크
		String idUse = "";
		if(userService.idCheck(mbsp_id) != null) {
			idUse = "no"; // 사용불가능
		}else {
			idUse = "yes"; // 사용가능
		}
		
		entity = new ResponseEntity<String> (idUse, HttpStatus.OK);
		
		return entity;
	}
	
	
}
