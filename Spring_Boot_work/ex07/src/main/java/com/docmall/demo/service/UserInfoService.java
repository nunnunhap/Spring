package com.docmall.demo.service;

import com.docmall.demo.domain.UserInfoVO;

public interface UserInfoService {

	// 아이디 중복체크
	String idCheck(String u_id);
	
	// 회원가입 저장
	void join(UserInfoVO vo);
	
	// 로그인(아이디 먼저 비교)
	UserInfoVO login(String u_id);
	
	
	
}
