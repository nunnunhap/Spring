package com.mysite.sbb.answer;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티, 프라이머리키> : 테이블에 대하여 삽입, 삭제, 수정, 조회 작업을 가능하게 해주는 인터페이스
// 해당 interface는 CRUD 작업을 처리하는 메서드들이 내장.
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	
	
	
	
}
