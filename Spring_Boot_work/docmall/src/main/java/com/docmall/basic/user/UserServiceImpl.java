package com.docmall.basic.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserMapper userMapper;
	
	@Override
	public void join(UserVo vo) {
		userMapper.join(vo);
	}
	
	
	
	
	
}
