package com.docmall.basic.order;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderVo {
	
	private Long ord_code; // 시퀀스 처리
    private String mbsp_id; // 세션 정보
    private String ord_name;
    private String ord_addr_zipcode;
    private String ord_addr_basic;
    private String ord_addr_detail;
    private String ord_tel;
    private int ord_price;
    private String ord_desc;
    private Date ord_regdate;
    private String ord_admin_memo; // 관리자 용도로만 사용함.
	
}