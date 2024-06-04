package com.docmall.demo.kakaopay;

import java.util.Date;

import lombok.Data;

@Data
// 1단계(결제준비요청-ready) : 결제정보를 카카오페이 서버에게 전달하고 응답받는 데이터를 저장하기 위한 클래스
public class ReadyResponse {
	
	private String tid; // 결제고유번호
	
	// 요청한 클라이언트가 pc웹일 경우, 카카오페이로 결제요청 메시지를 보내기 위한 사용자 정보입력 화면 Redirect URL
	private String next_redirect_pc_url; // url
	private Date created_at;
}
