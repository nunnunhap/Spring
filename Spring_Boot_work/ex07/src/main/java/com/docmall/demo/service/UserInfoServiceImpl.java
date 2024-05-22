package com.docmall.demo.service;

import org.springframework.stereotype.Service;

import com.docmall.demo.mapper.UserInfoMapper;

import lombok.RequiredArgsConstructor;

// bean 생성 : userInfoServiceImpl(빈 생성 시 앞 글자는 소문자로 생성됨)
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
	// DI
	private final UserInfoMapper userInfoMapper;
	
	
	
}
