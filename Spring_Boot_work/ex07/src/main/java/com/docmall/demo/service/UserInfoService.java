package com.docmall.demo.service;

import org.apache.ibatis.annotations.Param;

import com.docmall.demo.domain.UserInfoVO;

public interface UserInfoService {

	// 아이디 중복체크
	String idCheck(String u_id);
	
	// 회원가입 저장
	void join(UserInfoVO vo);
	
	// 로그인(아이디 먼저 비교)
	UserInfoVO login(String u_id);
	
	// 회원정보 수정
	void modify(UserInfoVO vo);
	
	// 비밀번호 변경
	void changePw(String u_id, String new_pwd);
	
	// 계정 삭제
	void delete(String u_id);
	
}
