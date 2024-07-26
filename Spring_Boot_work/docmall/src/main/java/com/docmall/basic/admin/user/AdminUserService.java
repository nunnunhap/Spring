package com.docmall.basic.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.docmall.basic.common.dto.Criteria;

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
	
	void mailSendCountUpdate(int idx) {
		adminUserMapper.mailSendCountUpdate(idx);
	}

	List<MailMngVo> getMailInfoList(Criteria cri, String title) {
		return adminUserMapper.getMailInfoList(cri, title);
	}
	
	int getMailListCount(String title) {
		return adminUserMapper.getMailListCount(title);
	}
	
	MailMngVo getMailSendInfo(int idx) {
		return adminUserMapper.getMailSendInfo(idx);
	}
	
	void mailingedit(MailMngVo vo) {
		adminUserMapper.mailingedit(vo);
	}
	
}
