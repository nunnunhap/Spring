package com.docmall.basic.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
	
	private final AdminService adminService;
	// SecurityConfig에서 주입받음.
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/") // http://localhost:9095/admin/
	public String loginForm() {
		return "/admin/adLogin";
	}
	
	@PostMapping("/admin_ok")
	public String admin_ok(AdminVo vo, HttpSession session) throws Exception {
		log.info("관리자정보 : " + vo);
		
		AdminVo d_vo = adminService.loginOk(vo.getAdmin_id());
		
		String url = "";
		
		if(d_vo != null) {
			if(passwordEncoder.matches(vo.getAdmin_pw(), d_vo.getAdmin_pw())) {
				log.info("비밀번호 일치");
				
				d_vo.setAdmin_pw(""); // pw는 필요없어서 세션에서 뺌.
				session.setAttribute("admin_state", d_vo);
				url = "admin/admin_menu";
			}
		}
		
		return "redirect:/" + url;
	}
	
	@GetMapping("/admin_menu")
	public void admin_menu() {
		
	}
	
	
	
	
	
}
