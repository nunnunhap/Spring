package com.docmall.demo.service;

import org.springframework.stereotype.Service;

import com.docmall.demo.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // lombok 제공, 생성자를 자동으로 만들어줌.
public class BoardServiceImpl implements BoardService {
	
	// 의존성 주입(DI)
	private final BoardMapper boardMapper;
	
	
	
}
