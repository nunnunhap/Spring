package com.docmall.demo.mapper;

import java.util.List;

import com.docmall.demo.domain.EmployeeVO;

public interface EmployeesMapper {
	// 이건 환경이 인터페이스니까 당연히 추상메서드 사용
	List<EmployeeVO> emp_list();
}
