package com.docmall.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum; // 1	2	3	4	5	처음은 1을 가지고 나중엔 선택된 페이지 번호를 가짐.
	private int amount; // 페이지 마다 출력할 게시물 개수
	
	// 검색 필드
	private String type; // 제목만, 글작성자, 등등 선택한 검색 종류
	private String keyword; // 검색어
	
	// 생성자
	public Criteria() {
		this(1, 10); // 매개변수가 있는 생성자에 넣어줌.
	}
	
	// 매개변수가 있는 생성자
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		
		System.out.println("pageNum : " + pageNum + ", amount" + amount);
	}
	
}
