package com.docmall.demo.service;

import com.docmall.demo.dto.EmailDTO;

public interface EmailService {
	
	void sendMail(String type, EmailDTO dto, String message);
	
	
	
}
