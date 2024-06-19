package com.docmall.basic.user;

import org.apache.ibatis.annotations.Param;

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
	
}
