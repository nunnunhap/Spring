package com.docmall.basic.common.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage; // 각 블럭에서 출력할 시작 페이지 번호
	private int endPage; // 각 블럭에서 출력할 끝 페이지 번호
	
	private boolean prev, next; // '이전'과 '다음' 표시할 것인지 여부
	private int total; // 테이블의 전체 데이터 개수
	
	private Criteria cri; // cri.getPageNum(), cri.getAmount(), cri.getType(), cri.getKeyword()
	
	// 생성자
	public PageDTO(Criteria cri, int total) {
		// 생성자 안에서 위의 5개 필드의 값을 구하는 작업
		this.cri = cri;
		this.total = total;
		
		// 블럭마다 보여줄 페이지 번호의 개수. 예> 1 2 3 4 5 6 7 8 9 10
		int pageSize = 10;
		int endPageInfo = pageSize - 1; // 10 - 1 = 9
		
		// endPage를 먼저 구함. // endPage가 10이 나오도록 공식을 만듦.
		// 현재 블럭에서 어떤 페이지 번호를 클릭하든 화면에 페이지 번호를 다시 출력하기 위하여 endPage변수의 값이 동일하게 공식으로 처리.
		this.endPage = (int) (Math.ceil(cri.getPageNum() / (double) pageSize)) * pageSize;
		
		this.startPage = this.endPage - endPageInfo;
		// 근데 이건 총 데이터 수에 맞는 endPage값을 구하지 못한 상태.
		// 실제 총 데이터 수에 해당하는 브럭의 마지막 페이지 번호
		// 자바에서 33 / 10은 3임. 하나라도 double형이어야만 함. realEnd는 db에 들어 있는.
		// 1.0 곱해준 것은 어디까지나 실수형 만들어주려고 그런 것임.
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		// 아래 조건식이 true이면 endPage변수의 값은 실제 테이블의 총 개수를 이용한 마지막 페이지 번호가 됨.
		if(realEnd <= this.endPage) {
			this.endPage = realEnd;
		}
		
		// '이전'과 '다음' 표시
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
