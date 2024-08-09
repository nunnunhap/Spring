package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	// 질문 글에 대한 답변저장
	public Answer create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		
		// 질문글에 대한 정보
		answer.setQuestion(question); // 이 부분이 차이가 있음.
		
		// 사용자에 대한 정보
		answer.setAuthor(author);
		
		this.answerRepository.save(answer);
		
		return answer;
	}
	
	
	
}
