package com.docmall.basic.admin.user;

public interface AdminUserMapper {
	
	void mailing_save(MailMngVo vo);
	
	String[] getAllMailAddress();
	
	
}
