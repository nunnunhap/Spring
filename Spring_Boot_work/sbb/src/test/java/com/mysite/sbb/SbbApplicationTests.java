package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 스프링부트의 테스트용 클래스라는 것을 의미
class SbbApplicationTests {
	
	@Autowired // lombok 어노테이션 사용불가.
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@Test // JUnit에서 제공하는 @Test어노테이션 필수로 작성 : 아래 메서드는 JUnit 환경에서 테스트하기 위한 메서드임을 알려줌.
	void testJpa() {
		/*
		Question q1 = new Question();
		// 원래 엔티티에선 setter메서드 사용하지 말아야 함.
		q1.setSubject("sbb가 무엇일까요?");
		q1.setContent("sbb에 대하여 알고 싶어!"); // sbb 그냥 게시판 뜻하는 것임. spring board
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); // 첫 질문데이터 저장
		
		Question q2 = new Question();
		q2.setSubject("스프링 부트 모델 질문입니다");
		q2.setContent("id는 자동생성되나요?"); // sbb 그냥 게시판 뜻하는 것임. spring board
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2); // 두번째 질문데이터 저장
		
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size()); // assertEquals(기대값, 실제값)
		
		Question q = all.get(0);
		assertEquals("sbb가 무엇일까요?", q.getSubject());
		
		Optional<Question> oq = this.questionRepository.findById(3); // List컬렉션이 아니라 인덱스가 없음.
		// ()안의 데이터가 존재하지 않을 경우를 위한 null 체크가 필요 없음.
		if(oq.isPresent()) { // 본래 null.get~~() 은 안되는데 optional 덕분에 에러 안남.
			Question q3 = oq.get();
			assertEquals("sbb가 무엇일까요?", q3.getSubject());
		}
		
		Question q4 = this.questionRepository.findBySubject("sbb가 무엇일까요?");
		assertEquals(3, q4.getId());
		
		Question q5 = this.questionRepository.findBySubjectAndContent("sbb가 무엇일까요?", "sbb에 대하여 알고 싶어!");
		assertEquals(3, q5.getId());
		
		Optional<Question> oq1 = this.questionRepository.findById(3);
		assertTrue(oq1.isPresent()); // false 반환되면 에러발생
		
		Question q6 = oq1.get(); // 수정하고자 하는 데이터를 참조
		
		q6.setSubject("수정한 제목");
		this.questionRepository.save(q6); // 삽입(insert), 수정(update) 시 save() 메서드 사용
		
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
		
		Optional<Question> oq = this.questionRepository.findById(4);
		assertTrue(oq.isPresent());
		// 답글을 달려면 먼저 본문 글을 가지고 들어와야 함.
		Question q = oq.get(); // 질문글 참조
		
		Answer a = new Answer();
		a.setContent("네, 자동으로 생성됩니다.");
		a.setQuestion(q); // 이렇게 넣어주어야만 어떤 질문글에 대한 답글인지 알 수 있음. mybatis처럼 부모의 일련번호 참조가 아님.
		a.setCreateDate(LocalDateTime.now());
		
		this.answerRepository.save(a);
		*/
		
		Optional<Answer> oa = this.answerRepository.findById(1); // Left Join
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(4, a.getQuestion().getId()); // 질문글의 3번글을 보고 답변글 저장
		
		
		
		
	}
	
	
	
	
	
	
	
	
	

}
