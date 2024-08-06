package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	// 질문 글에 대한 답변저장
	public void create(Question question, String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		
		// 질문글에 대한 정보
		answer.setQuestion(question); // 이 부분이 차이가 있음.
		
		this.answerRepository.save(answer);
	}
	
	
	
}
