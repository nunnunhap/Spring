package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	// 질문글에 대한 답변저장 후 답변 엔티티를 리턴
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
	
	// 답변글 조회(삭제 또는 수정작업)
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		}else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	// 답변글 삭제
	public void delete(Answer answer) {
		this.answerRepository.delete(answer);
	}
	
	// 답변글 수정 // 수정을 한다면 entity 객체를 항상 준비해주어야 함.
	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	// 추천하기   entity를 테이블로 바라보자. Answer
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepository.save(answer);
	}
	
	
	
	
}
