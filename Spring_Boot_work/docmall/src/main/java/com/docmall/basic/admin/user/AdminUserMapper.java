package com.docmall.basic.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.basic.common.dto.Criteria;

public interface AdminUserMapper {
	
	void mailing_save(MailMngVo vo);
	
	String[] getAllMailAddress();
	
	void mailSendCountUpdate(int idx);
	
	List<MailMngVo> getMailInfoList(@Param("cri") Criteria cri, @Param("title") String title);
	
	int getMailListCount(String title);
	
	MailMngVo getMailSendInfo(int idx);
	
	void mailingedit(MailMngVo vo);
	
	
}
