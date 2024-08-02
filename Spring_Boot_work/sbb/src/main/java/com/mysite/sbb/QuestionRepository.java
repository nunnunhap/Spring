package com.mysite.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티, 프라이머리키> : 테이블에 대하여 삽입, 삭제, 수정, 조회 작업을 가능하게 해주는 인터페이스
// 해당 interface는 CRUD 작업을 처리하는 메서드들이 내장.
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	// 추상 메서드명 네이밍 규칙이 존재함.
	// 메서드 명명규칙 : findBy + 필드명의 첫 문자를 대문자로
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	
	
	
	
}
