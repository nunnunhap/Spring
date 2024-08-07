package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // 생성자 메서드를 만들어주고 생성자 주입방식이 진행됨.
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
		
	@GetMapping("/list")
	// @ResponseBody // 아래 리턴값을 순수한 데이터로 인식
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		
		// List<Question> questionList = this.questionService.getList(); // 페이징 기능이 없는 메서드임.
		// model.addAttribute("questionList", questionList);
		
		// 목록과 페이징 정보 둘 다 존재함.
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}") // 이런 주소는 REST API 개발론에서 나왔음.
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		
		model.addAttribute("question", question);
		return "question_detail"; // 답변 폼이 존재하기 때문에 파라미터로 AnswerForm answerForm 사용.
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		
		return "question_form";
	}
	
	/*
	@PostMapping("/create")
	public String questionCreate(
			@RequestParam(value = "subject") String subject,
			@RequestParam(value = "content") String content) {
		
		this.questionService.create(subject, content);
		
		return "redirect:/question/list";
	} */
	
	@PostMapping("/create")
	// @Valid, 에러정보 두 파라미터 순서 변경 시 에러 발생
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("폼 입력 에러");
			return "question_form"; // 에러 발생 시 해당 폼 다시 보여주기
		}
		
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		
		return "redirect:/question/list";
	}
	
	
	
	
	
	
	
	
}
