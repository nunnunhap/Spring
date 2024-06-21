package com.docmall.basic.kakaologin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
// 카카오로부터 넘어오는 사용자 정보를 담당하는 클래스
public class KakaoUserInfo {
	
	private Long id;
	private String nickname;
	private String email;
	
}
