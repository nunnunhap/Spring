package com.docmall.basic.admin.staticanalysis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaticAnalysisService {
	
	private final StaticAnalysisMapper staticAnalysisMapper;
	
	
	
	
	List<Map<String, Object>> monthlySalesStatusByTopCategory(String ord_date) {
		return staticAnalysisMapper.monthlySalesStatusByTopCategory(ord_date);
	}
	
	
}
