package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티, 프라이머리키> : 테이블에 대하여 삽입, 삭제, 수정, 조회 작업을 가능하게 해주는 인터페이스
// 해당 interface는 CRUD 작업을 처리하는 메서드들이 내장.
// mybatis로 비교하면, QuestionMapper.java, QuestionMapper.xml 2가지 파일로 관점을 바라봄.
// 원칙적으론 Service가 중간에 들어가야 하는데, 이번 작업은 생략함.
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	// 추상 메서드명 네이밍 규칙이 존재함.
	// 메서드 명명규칙 : findBy + 필드명의 첫 문자를 대문자로
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	
	// 목록과 페이징 처리
	Page<Question> findAll(Pageable pageable);
	
	// 목록과 페이징 처리. 검색추가
	Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	
	
}
