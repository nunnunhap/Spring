package com.docmall.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.EmailDTO;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.EmailService;
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
	private final EmailService emailService;
	
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
		
		return "redirect:/";
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
				// setAttribute(String name, Object value)/ vo가 Object형으로 형변환된 것임.
				session.setAttribute("login_status", vo); // 서버에 세션 방식으로 저장, 로그인한 사용자로 구분
				
				// 원래 요청한 주소가 세션으로 존재하면
				// "targetUrl"은 LoginInterceptor.java에 들어있음.
				if(session.getAttribute("targetUrl") != null) {
					url = (String) session.getAttribute("targetUrl");
				}
				
			}else { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW"; // 이런 메시지가 겹치면 동작이 이상해질 가능성이 높음. 주의
				url = "/userinfo/login"; // 로그인폼 주소
			}
		}else { // 아이디가 존재하지 않을 경우
			msg = "failID";
			url = "/userinfo/login"; // 로그인폼 주소
		}
		
		rttr.addFlashAttribute("msg", msg); // JSP에서 msg변수 사용 목적
		
		return "redirect:" + url; // 메인으로 이동
	}
	
	// 로그아웃은 전통적으로 post방식을 사용하라고 함. 일단 우린 get방식을 사용하겠음.
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); // 세션형태로 관리되는 모든 메모리는 소멸
		
		return "redirect:/";
	}
	
	@GetMapping("mypage")
	// Model은 JSP에서 표현하기 위함.
	public void mypage(HttpSession session, Model model) throws Exception {
		
		// getAttribute() return타입이 Object라서 UserInfoVO로 형변환
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id();
		// 인증된 사용자만 사용 가능한게 mypage다 보니 id를 세션에서 가지고 옴.
		UserInfoVO vo = userInfoService.login(u_id);
		
		model.addAttribute("userinfo", vo);
	}
	
	// 내 정보 수정하기
	@PostMapping("modify") // 인증된 사용자만 수정하게 하기 위하여 session가지고 옴.
	public String modify(UserInfoVO vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		log.info("회원정보 수정 내역 : " + vo);
		
		// 웹 페이지에 session정보 삭제하면 "login_status" 정보가 없어지는거라서 modify가 작동하지 않음.
		// 이 기능을 여러 곳을 넣는건 코드량이 많아지니 스프링의 인터셉터 사용해야 함.(거의 모든 작업이 끝났을 때 함. 작업후반에 작업하는 것이 좋음)
		if(session.getAttribute("login_status") == null) return "redirect:/userinfo/login";
		
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id();
		
		userInfoService.modify(vo);
		
		rttr.addFlashAttribute("msg", "success"); // JSP에서 사용하기 위한 목적
		
		return "redirect:/userinfo/mypage";
	}
	
	@GetMapping("changepw")
	public void changepw() {
		
	}
	
	@PostMapping("changepw")
	public String changepw(String cur_pwd, String new_pwd, HttpSession session, RedirectAttributes rttr) {
		
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id(); // 세션을 통해 아이디 참조
		
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";
		
		if(vo != null) { // 아이디가 존재하는 경우
			// 비밀번호 비교
			if(passwordEncoder.matches(cur_pwd, vo.getU_pwd())) { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하는 것이라면
				// 신규 비밀번호 변경 작업
				String u_pwd = passwordEncoder.encode(new_pwd); // 암호화
				userInfoService.changePw(u_id, u_pwd);
				msg = "success";
				
			}else { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW";
			}
		}
		
		rttr.addFlashAttribute("msg", msg); // JSP에서 msg변수 사용 목적
		
		return "redirect:/userinfo/changepw";
	}
	
	@GetMapping("delete")
	public void delete() {
		
	}
	
	// 계정 삭제
	@PostMapping("delete")
	public String delete(String u_pwd, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		String u_id = ((UserInfoVO) session.getAttribute("login_status")).getU_id(); // 세션을 통해 아이디 참조
		
		UserInfoVO vo = userInfoService.login(u_id);
		
		String msg = "";
		String url = "";
		
		if(vo != null) { // 아이디가 존재하는 경우
			// 비밀번호 비교
			if(passwordEncoder.matches(u_pwd, vo.getU_pwd())) { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하는 것이라면
				// 1234를 쳐도 암호화된 비밀번호는 항상 다름. 그래서 조건은 비밀번호가 아닌 아이디로만 삭제처리를 해줘야 함.
				userInfoService.delete(u_id);
				session.invalidate(); // 회원탈퇴했는데 로그인상태를 유지할 필요 없으니

				msg = "success";
				url = "/";
				
			}else { // 사용자가 입력한 비밀번호가 암호화된 형태에 해당하지 않는 것이라면
				msg = "failPW";
				url = "/userinfo/delete";
			}
		}
		
		rttr.addFlashAttribute("msg", msg); // JSP에서 msg변수 사용 목적
		
		return "redirect:" + url;
	}
	
	// 아이디 찾기
	@GetMapping("idfind")
	public void idfind() {
		
	}
	
	// 아이디 찾기 버튼 클릭 시
	@PostMapping("idfind") // rttr은 메세지 추가시키기 위하여 넣어줌.
	public String idfind(String u_name, String u_email, String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		String url = "";
		String msg = "";
		
		// 인증코드 확인
		if(authcode.equals(session.getAttribute("authcode"))) {
			// 아이디를 찾아 메일 발송
			String u_id = userInfoService.idfind(u_name, u_email);
			if(u_id != null) {
				// 아이디 내용으로 메일 발송 작업(EmailService에 대한 주입작업을 따로 받아야 함.)
				// emailService.sendMail("검색아이디: 타임리프 파일명", EmailDTO dto, msg); dto객체는 수동으로 작업함.
				String subject = "DocMall 아이디 찾기 인증 코드";
				EmailDTO dto = new EmailDTO("DocMall", "DocMall", u_email, subject, u_id);
				// EmailDTO dto = new EmailDTO("DocMallManager", "DocMall", u_email, subject, msg);
				emailService.sendMail("emailIdResult", dto, u_id);
				
				// 인증코드를 메모리 상에서 꼭 제거해줄 것
				session.removeAttribute("authcode");
				
				msg = "success";
				url = "/userinfo/login";
				rttr.addFlashAttribute("msg", msg); // 이건 왜 들어가야 하지?
				
			}else {
				msg = "nameFail"; // jsp 이름 일치 필요
				url = "/userinfo/idfind";
			}
			
		} else {
			msg = "failAuthCode"; // jsp 이름 일치 필요
			url = "/userinfo/idfind";
		}
		rttr.addFlashAttribute("msg", msg); // JSP에서 사용하기 위한 목적

		return "redirect:" + url;
	}
	
	@GetMapping("pwfind")
	public void pwfind() {
		
	}
	
	@PostMapping("pwfind")
	public String pwfind(String u_id, String u_name, String u_email, String authcode, HttpSession session, RedirectAttributes rttr) {
		String url = "";
		String msg = "";
		
		// 인증코드 확인
		if(authcode.equals(session.getAttribute("authcode"))) {
			
			// 사용자가 입력한 3개 정보(아이디, 이름, 이메일)를 조건으로 사용하여, 이메일을 DB에서 가져옴.
			String d_email = userInfoService.pwfind(u_id, u_name, u_email);
			if(d_email != null) {
				// 임시 비밀번호 생성(UUID 이용)
				String tempPw = UUID.randomUUID().toString().replaceAll("-", ""); // -를 제거
				tempPw = tempPw.substring(0, 10); // 10자리
				
				// 암호화된 비밀번호
				String enc_tempPw = passwordEncoder.encode(tempPw);
				userInfoService.tempPwUpdate(u_id, enc_tempPw);
				
				EmailDTO dto = new EmailDTO("DocMall", "DocMall", d_email, "DocMall 임시비밀번호", tempPw);
				
				emailService.sendMail("emailPwResult", dto, tempPw); // 타임리프파일명, null, 암호화되지 않은 번호
				
				session.removeAttribute("authcode");
				
				url = "/userinfo/login";
			}else {
				url = "/userinfo/pwfind";
				msg = "failInput";
			}
			
		}else {
			url = "/userinfo/pwfind";
			msg = "failAuth";
		}
		
		return "redirect:" + url;
	}
	
	// /userinfo/ajax 인증된 사용자만 사용가능한 주소
	@GetMapping("ajax")
	@ResponseBody
	public String ajax() {
		return "ajax";
	}
	
	// memberlist 리스트 구현
	@GetMapping("memberlist")
	public void list(Criteria cri, Model model) {
		List<UserInfoVO> list = userInfoService.userList(cri);
		
		model.addAttribute("list", list);
		
		int total = userInfoService.getTotalCount(cri);
		PageDTO pageDTO = new PageDTO(cri, total);
		
		model.addAttribute("pageMaker", pageDTO);
	}
	
	
	
	
}
