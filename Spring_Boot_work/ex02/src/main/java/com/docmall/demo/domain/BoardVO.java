package com.docmall.demo.domain;

import java.util.Date;

// 데이터베이스의 게시판 테이블을 참조하는 클래스
public class BoardVO {
	// 요새 추세는 NUMBER는 int보다 long을 쓰는 추세
	// 여기선 래퍼클래스로 씀. 이유는 나중에.
	private Long idx; // 글번호, 시퀀스 생성 및 사용
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	

	public Long getIdx() {
		return idx;
	}
	public void setIdx(Long idx) {
		this.idx = idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	// 필드들을 통한 데이터 확인
	@Override
	public String toString() {
		return "BoardVO [idx=" + idx + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + "]";
	}
	
	

	
}
