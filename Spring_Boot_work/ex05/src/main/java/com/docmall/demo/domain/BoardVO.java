package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	private Long	bno; // 참조타입 래퍼클래스, 이것이 primary key니까..!
    private String	title;
    private String	content;
    private String	writer;
    private Date	regdate;
    private Date	updatedate;
    private int		viewcount;
 
}


