package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	// 페이징 없음
	public List<Question> getList() {
		return questionRepository.findAll();
	}
	
	// 페이징 및 정렬 작업
	public Page<Question> getList(int page, String kw) { // kw는 검색어를 입력받는 파라미터
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw);
		return this.questionRepository.findAll(spec, pageable);
	}
	
	// 수정, 삭제
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject, String content, SiteUser user) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user); // 사용자 정보 추가
		this.questionRepository.save(q);
	}
	
	// 수정하기
	public void modify(Question question, String subject, String content) {
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	// 삭제하기 // entity가 필요함을 꼭 기억하기
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	// 추천하기
	public void vote(Question question, SiteUser siteUser) {
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
	
	private Specification<Question> search(String kw) {
		// Specification 재정의
		// 자바 익명객체. new 인터페이스명() { // 재정의 }
		// 보통 class로 만드나, 이번 건은 한 번 쓰고 끝나기 때문에 이렇게 사용함.
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거 
                
                // Join 시 JOIN조건식 ON절에 해당하는 의미.
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                
                // 검색조건 추가 LIKE 패턴 매칭
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
            }
        };
    }
	
	
	
	
	
	
}
