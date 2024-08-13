package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

// 엔티티 클래스 : 테이블로 생성. 용도 > 질문 테이블
@Getter
@Setter // 엔티티 만들 때 Setter 메서드 사용하지 않기를 권장함.
@Entity
public class Question { // 이걸 entity 클래스로 만듦. 여기서 만들면 그대로 테이블이 만들어짐.
	
	@Id // 테이블의 Primary key에 꼭 사용함.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 일련번호 개념
	private Integer id;
	
	// @Column(name = "s_subject") // 실제 컬럼명은 name값으로 생성되며, 생략 시 필드명과 동일하게 생성됨.
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT") // 4000바이트 이상 사용할 때.
	private String content;
	private LocalDateTime createDate;
	
	// 질문글을 통한 답변글 참조
	// 질문글 삭제 시 답변글도 함께 삭제
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList; // 답변이 여러개이므로 List<Answer>
	
	// 질문글 작성자. 한 사용자가 여러개 게시글 작성(1 : N)
	@ManyToOne // many : question, one은 author
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	// 추천(좋아요) 기능 // Set은 중복이 안되니 같은 질문글에 같은 사용자가 반복해서 클릭하면 안됨.
	@ManyToMany
	Set<SiteUser> voter; // 한 게시글에 대하여 여러 사람이 추천을 하니 복수 개념이 들어가야 함. N : N 관계
	
	
}
