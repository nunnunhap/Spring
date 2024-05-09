package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVO {
	private Integer rno;
	private Long	bno; // bno는 게시판 테이블에서도 래퍼타입이었으니 그대로 참조
	private String	retext;
	private String	replyer;
	private Date	replydate;
	private Date	updatedate;
}
