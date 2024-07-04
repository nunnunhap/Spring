package com.docmall.basic.common.dto;

import org.springframework.web.util.UriComponentsBuilder;

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
	private String type; // 제목만, 글작성자, 등등 선택한 검색 종류 // null값
	private String keyword; // 검색어 // null값
	
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
	
	// 아래 메서드명은 무조건 getter방식으로 만들어주어야 함.
	public String[] getTypeArr() { // boardMapper.xml에서 사용. getter메서드 작동.
		// true면 검색파라미터가 없었단 뜻. 첫번째는 공백, 두번째는.. 만약에 type필드가 TWC로 넘어오면 "T", "W", "C"요소를 가지는 배열로 리턴
		// type.split(""); -> "TWC".split("") -> "T" "W" "C"
		return type == null ? new String[] {} : type.split("");
	}
	
	// UriComponentsBuilder : 여러개의 파라미터들을 연결하여 URL형태로 만들어주는 기능
	// ?pageNum=2&amount=10&type=T&keyword=사과"
	public String getListLink() {
		UriComponentsBuilder builder =  UriComponentsBuilder.fromUriString("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.amount)
				.queryParam("type", this.type)
				.queryParam("keyword", this.keyword);
				
		return builder.toUriString();
	}
}
