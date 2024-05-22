package com.docmall.demo.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoVO {
	
    private String u_id;
    private String u_pwd;
    private String u_name;
    private String u_email;
    private String u_zip_code;
    private String u_addr;
    private String u_addrdetail;
    private String u_phone;
    private Date   u_regdate; // Calendar, LocalDate, LocalDateTime, LocalTime 등의 날짜 관련 클래스 사용 가능
	
}
