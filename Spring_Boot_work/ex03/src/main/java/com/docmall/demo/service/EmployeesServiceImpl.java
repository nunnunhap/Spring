package com.docmall.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.EmployeeVO;
import com.docmall.demo.mapper.EmployeesMapper;

// 구현 클래스 : 인터페이스를 상속받는 클래스
@Service
public class EmployeesServiceImpl implements EmployeesService {
	
	// 스프링이 내부적으로 EmployeesMapper 인터페이스를 이용하여 객체를 생성
	// 그리고, 아래 객체에 주입(대입). 즉, 참조를 해줌. 이걸 "의존성 주입(Dependency Injection : DI)"이라고 함.
	@Autowired // 주입기능을 제공하는 어노테이션
	private EmployeesMapper employeesMapper;

	@Override
	public List<EmployeeVO> emp_list() {
		System.out.println("서비스 호출");
		return employeesMapper.emp_list();
	}
}
