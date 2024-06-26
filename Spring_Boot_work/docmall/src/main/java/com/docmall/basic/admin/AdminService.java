package com.docmall.basic.admin;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
	
	private final AdminMapper adminMapper;
	
	public AdminVo loginOk(String admin_id) {
		return adminMapper.loginOk(admin_id);
	}
	
	
	
}
