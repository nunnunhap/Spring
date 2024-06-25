package com.docmall.basic.user;

import org.apache.ibatis.annotations.Param;

import com.docmall.basic.kakaologin.KakaoUserInfo;

public interface UserMapper {
	
	void join(UserVo vo);
	
	// 아이디 중복체크
	String idCheck(String mbsp_id);
	
	// 로그인
	UserVo login(String mbsp_id);
	
	// ID 찾기
	String idfind(@Param("mbsp_name") String mbsp_name, @Param("mbsp_email") String mbsp_email);
	
	// PW 찾기
	String pwfind(@Param("mbsp_id") String mbsp_id, @Param("mbsp_name") String mbsp_name, @Param("mbsp_email") String mbsp_email);
	
	// 비밀번호 업데이트 작업
	void tempPwUpdate(@Param("mbsp_id") String mbsp_id, @Param("temp_enc_pw") String temp_enc_pw);
	
	// 회원정보 수정
	void modify(UserVo vo);
	
	// 비밀번호 변경 (암호화처리하여 들어와야 함) mybatis에서 파라미터가 두 개면 param처리 필요.
	void changePw(@Param("mbsp_id") String mbsp_id, @Param("new_mbsp_password") String new_mbsp_password);
	
	// 회원정보 삭제
	void delete(String mbsp_id);
	
	// SNS 계정 존재유무
	String existsUserInfo(String sns_email);
	
	
}
