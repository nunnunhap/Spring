package com.docmall.basic.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user/*")
@Slf4j
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("join")
	public void joinForm() throws Exception {
		log.info("join");
	}
	
	@PostMapping("join")
	public String joinOk(UserVo vo) throws Exception {
		userService.join(vo);
		
		return "redirect:/";
	}
	
	
}
