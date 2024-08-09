package com.mysite.sbb.answer;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
	
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id,
			@Valid AnswerForm answerForm, BindingResult bindingResult,
			Principal principal) { // 현재 로그인한 사용자의 정보가 Principal 객체에 저장됨. HttpSession이랑 비슷한 느낌.
		
		// 질문글의 정보
		Question question = this.questionService.getQuestion(id); // 4번값을 이용하여 객체 생성 // 질문글 일련번호
		// 사용자 정보
		SiteUser siteUser = this.userService.getUser(principal.getName()); // 사용자 ID
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		// 답변저장 // answerForm.getContent()이 답변글
		this.answerService.create(question, answerForm.getContent(), siteUser);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
	
	
	
	
}
