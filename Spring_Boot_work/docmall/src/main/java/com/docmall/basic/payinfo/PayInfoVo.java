package com.docmall.basic.payinfo;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PayInfoVo {
	
	private Integer p_id;
	private Long ord_code;
	private String paymethod;
	private int p_price;
	private String p_status;
	private Date p_date;
}