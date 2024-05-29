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
	
	// ID 찾기
	String idfind(String u_name, String u_email);
	
	// PW 찾기
	String pwfind(String u_id, String u_name, String u_email);
	
	// 비밀번호 업데이트 작업
	void tempPwUpdate(String u_id, String enc_tempPw); // 강사님은 이걸 u_pwd로 하셨어.
	
	
}
