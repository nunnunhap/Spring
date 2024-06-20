package com.docmall.basic.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.basic.mail.EmailDTO;
import com.docmall.basic.mail.EmailService;

import jakarta.servlet.http.HttpSession;
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
	
	// 로그인 폼
	@GetMapping("login")
	public void loginForm() {
		
	}
	
	@PostMapping("login") // db와 연관있으니 Exception 사용하기
	public String loginOk(LoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		UserVo vo = userService.login(dto.getMbsp_id());
		
		String msg = "";
		String url = "/"; // "/" 메인주소
		
		if(vo != null) { // 아이디가 존재하는 경우
			// 비밀번호 비교 // vo.getU_pwd()는 db에서 읽어온 암호화된 비밀번호
			// paswordEncoder.matches(평문텍스트, db에 저장된 암호화된 비밀번호)
			if(passwordEncoder.matches(dto.getMbsp_password(), vo.getMbsp_password())) { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하는 것이라면
				vo.setMbsp_password(""); // 굳이 할 필욘 없다만, 암호화된 비밀번호를 공백처리해주기도 함.
				session.setAttribute("login_status", vo); // 서버에 세션 방식으로 저장, 로그인한 사용자로 구분
			}else { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW";
				url = "/user/login"; // 로그인폼 주소
			}
		}else { // 아이디가 존재하지 않을 경우
			msg = "failID";
			url = "/user/login"; // 로그인폼 주소
		}
		
		rttr.addFlashAttribute("msg", msg); // Thymeleaf에서 msg변수 사용 목적
		
		return "redirect:" + url; // 메인으로 이동
	}
	
	// 로그아웃은 전통적으로 post방식을 사용하라고 함. 일단 우린 get방식을 사용하겠음.
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); // 세션형태로 관리되는 모든 메모리는 소멸
		
		return "redirect:/";
	}
	
	// 아이디 찾기
	@GetMapping("idfind")
	public void idfindForm() {
		
	}
	
	// 아이디 찾기 버튼 클릭 시
	@PostMapping("idfind") // rttr은 메세지 추가시키기 위하여 넣어줌.
	public String idfindOk(String mbsp_name, String mbsp_email, String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		String url = "";
		String msg = "";
		
		// 인증코드 확인
		if(authcode.equals(session.getAttribute("authcode"))) {
			// 아이디를 찾아 메일 발송
			String u_id = userService.idfind(mbsp_name, mbsp_email);
			if(u_id != null) {
				// 아이디 내용으로 메일 발송 작업(EmailService에 대한 주입작업을 따로 받아야 함.)
				// emailService.sendMail("검색아이디: 타임리프 파일명", EmailDTO dto, msg); dto객체는 수동으로 작업함.
				String subject = "DocMall 아이디 찾기 인증 코드";
				EmailDTO dto = new EmailDTO("DocMall", "DocMall", mbsp_email, subject, u_id);
				// EmailDTO dto = new EmailDTO("DocMallManager", "DocMall", u_email, subject, msg);
				emailService.sendMail("emailIdResult", dto, u_id);
				
				// 인증코드를 메모리 상에서 꼭 제거해줄 것
				session.removeAttribute("authcode");
				
				msg = "success";
				url = "/user/login";
				rttr.addFlashAttribute("msg", msg); // 이건 왜 들어가야 하지?
				
			}else {
				msg = "nameFail"; // jsp 이름 일치 필요
				url = "/user/idfind";
			}
			
		} else {
			msg = "failAuthCode"; // jsp 이름 일치 필요
			url = "/user/idfind";
		}
		rttr.addFlashAttribute("msg", msg);

		return "redirect:" + url;
	}

	@GetMapping("pwfind")
	public void pwfind() {
		
	}
	
	@PostMapping("pwfind")
	public String pwfind(String mbsp_id, String mbsp_name, String mbsp_email, String authcode, HttpSession session, RedirectAttributes rttr) {
		String url = "";
		String msg = "";
		
		// 인증코드 확인
		if(authcode.equals(session.getAttribute("authcode"))) {
			
			// 사용자가 입력한 3개 정보(아이디, 이름, 이메일)를 조건으로 사용하여, 이메일을 DB에서 가져옴.
			String d_email = userService.pwfind(mbsp_id, mbsp_name, mbsp_email);
			if(d_email != null) {
				// 임시 비밀번호 생성(UUID 이용)
				String tempPw = userService.getTempPw();
				
				// 암호화된 비밀번호
				String temp_enc_pw = passwordEncoder.encode(tempPw);
				
				// 암호화된 임시 비밀번호를 해당 아이디에 업데이트
				userService.tempPwUpdate(mbsp_id, temp_enc_pw);
				
				EmailDTO dto = new EmailDTO("DocMall", "DocMall", d_email, "DocMall 임시비밀번호", tempPw);
				
				emailService.sendMail("emailPwResult", dto, tempPw); // 타임리프파일명, null, 암호화되지 않은 번호
				
				session.removeAttribute("authcode");
				msg = "success";
				url = "/user/pwfind";
			}else {
				url = "/user/pwfind";
				msg = "failInput";
			}
			
		}else {
			url = "/user/pwfind";
			msg = "failAuth";
		}
		
		rttr.addFlashAttribute("msg", msg);
		
		return "redirect:" + url;
	}
	
	@GetMapping("mypage")
	public void mypage(HttpSession session, Model model) throws Exception {
		
		// getAttribute() return타입이 Object라서 UserInfoVO로 형변환
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		// 인증된 사용자만 사용 가능한게 mypage다 보니 id를 세션에서 가지고 옴.
		UserVo vo = userService.login(mbsp_id);
		
		model.addAttribute("user", vo);
	}
	
	// 내 정보 수정하기
	@PostMapping("modify") // 인증된 사용자만 수정하게 하기 위하여 session가지고 옴.
	public String modify(UserVo vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		log.info("회원정보 수정 내역 : " + vo);
		
		// 인터셉터 기능 구현 전까지만 사용 예정
		if(session.getAttribute("login_status") == null) return "redirect:/user/login";
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		userService.modify(vo);
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/user/mypage";
	}
	
	@GetMapping("changepw")
	public void changepw() {
		
	}
	
	@PostMapping("changepw")
	public String changepw(String cur_mbsp_password, String new_mbsp_password, HttpSession session, RedirectAttributes rttr) {
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id(); // 세션을 통해 아이디 참조
		
		UserVo vo = userService.login(mbsp_id);
		
		String msg = "";
		
		if(vo != null) { // 아이디가 존재하는 경우
			// 비밀번호 비교
			if(passwordEncoder.matches(cur_mbsp_password, vo.getMbsp_password())) { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하는 것이라면
				// 신규 비밀번호 변경 작업
				String enc_new_mbsp_password = passwordEncoder.encode(new_mbsp_password); // 암호화
				userService.changePw(mbsp_id, enc_new_mbsp_password);
				msg = "success";
				
			}else { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW";
			}
		}
		
		rttr.addFlashAttribute("msg", msg); // JSP에서 msg변수 사용 목적
		
		return "redirect:/user/changepw";
	}
	
	
	
	
}
