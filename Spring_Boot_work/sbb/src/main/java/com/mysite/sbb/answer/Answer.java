package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

// 엔티티 클래스 : 테이블로 생성. 용도 > 답변 테이블
@Getter
@Setter // 엔티티 만들 때 Setter 메서드 사용하지 않기를 권장함.
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createDate;

	// Answer 입장에서 Question은 부모임. 참조되는 클래스가 참조하는 클래스 안에 들어와 있어야 함.
	@ManyToOne // N : 1 관계 ==> 많은 Answer 당 Question 하나
	private Question question; // 질문 데이터는  1개이므로 List컬렉션 사용 안함.
	
	// 답글 작성자. 한 사용자가 여러개 게시글 작성(1 : N)
	@ManyToOne // many : Answer, one : author
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	// 추천(좋아요) 기능
	@ManyToMany
	Set<SiteUser> voter;
	
}
