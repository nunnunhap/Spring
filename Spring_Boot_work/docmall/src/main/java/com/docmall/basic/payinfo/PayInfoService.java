package com.docmall.basic.payinfo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayInfoService {

	private final PayInfoMapper payInfoMapper;
	
	
	public void payInfo_insert(PayInfoVo vo) {
		payInfoMapper.payInfo_insert(vo);
	}
	
	public PayInfoVo ord_pay_info(Long ord_code) {
		return payInfoMapper.ord_pay_info(ord_code);
	}
	
	
}
