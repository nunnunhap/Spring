package com.docmall.basic.admin.user;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailMngVo {
	
	// result map 사용을 위하여 이름을 다르게 만듦!
	private Integer idx;
    private String title;
    private String content;
    private String gubun;
    private int sendcount;
    private Date regDate;
	
}
