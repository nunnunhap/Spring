package com.docmall.basic.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	
	public void join(UserVo vo) {
		userMapper.join(vo);
	}
	
	public String idCheck(String mbsp_id) {
		return userMapper.idCheck(mbsp_id);
	}
}
