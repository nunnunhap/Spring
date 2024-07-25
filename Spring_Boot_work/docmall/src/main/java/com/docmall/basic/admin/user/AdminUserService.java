package com.docmall.basic.admin.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

	// DI
	private final AdminUserMapper adminUserMapper;
	
	
	void mailing_save(MailMngVo vo) {
		adminUserMapper.mailing_save(vo);
	}

	String[] getAllMailAddress() {
		return adminUserMapper.getAllMailAddress();
	}


	
	
}
