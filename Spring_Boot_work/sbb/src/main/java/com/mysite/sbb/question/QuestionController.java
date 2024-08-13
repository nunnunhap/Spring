package com.mysite.sbb.question;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor // 생성자 메서드를 만들어주고 생성자 주입방식이 진행됨.
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	private final UserService userService;
		
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
	

	/*
	@PostMapping("/create")
	public String questionCreate(
			@RequestParam(value = "subject") String subject,
			@RequestParam(value = "content") String content) {
		
		this.questionService.create(subject, content);
		
		return "redirect:/question/list";
	} */
	
	// 인증된 사용자만 질문글 작성하기
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	// @Valid, 에러정보 두 파라미터 순서 변경 시 에러 발생
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			System.out.println("폼 입력 에러");
			return "question_form"; // 에러 발생 시 해당 폼 다시 보여주기
		}
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		
		return "redirect:/question/list";
	}
	
	// 수정 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}") // Rest API에서 요구하는 주소. 이 전까진 쿼리스트링 사용했었음.
	// QuestionForm questionForm 이 파라미터에 @ModelAttribute 가 있다고 생각하면 됨.
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		// 수정하고자 하는 일련번호의 Question 엔티티 클래스 객체 생성
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		questionForm.setSubject(question.getSubject());
		questionForm.setSubject(question.getContent());
		
		// QuestionForm questionForm이 아래 html페이지에 Model로 참조된다. 
		return "question_form";
	}
	
	// 수정 저장
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		// 수정하고자 하는 내용을 db에서 읽어옴.
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	// 답변 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.questionService.delete(question);
		
		return "redirect:/";
	}
	
	// 추천하기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id); // 추천 대상의 질문글
		SiteUser siteUser = this.userService.getUser(principal.getName()); // 추천사용자(인증 받은 사용자)

		this.questionService.vote(question, siteUser);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
	
	
}
