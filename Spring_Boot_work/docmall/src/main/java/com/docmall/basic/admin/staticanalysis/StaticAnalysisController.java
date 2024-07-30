package com.docmall.basic.admin.staticanalysis;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/staticanalysis/*")
public class StaticAnalysisController {
	
	private final StaticAnalysisService staticAnalysisService;
	
	
	@GetMapping("/orderStats")
	public void getOrderStatus(Model model) throws Exception {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		
		model.addAttribute("year", year); // 현재 년도
		model.addAttribute("month", month); // 현재 달
	}
	
	@GetMapping("monthlySalesStatusByTopCategory")
	@ResponseBody // responseentity 안에 이 body 성격도 포함
	public List<Map<String, Object>> getMonthlySalesStatusByTopCategory(int year, int month) {
		String ord_date = String.format("%s/%s", year, (month < 10 ? "0" + String.valueOf(month) : month));
		log.info("선택일 : " + ord_date);
		
		List<Map<String, Object>> listObjMap = staticAnalysisService.monthlySalesStatusByTopCategory(ord_date);
		
		return listObjMap;
	}
	
	
	
	
	


}
