package com.docmall.basic.user;

public interface UserMapper {
	
	void join(UserVo vo);
	
	// 아이디 중복체크
	String idCheck(String mbsp_id);
	
	
}
