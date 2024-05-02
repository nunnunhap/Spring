package com.docmall.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.docmall.demo.domain.EmployeeVO;

@Mapper
public interface EmployeesMapper {
	// 이건 환경이 인터페이스니까 당연히 추상메서드 사용
	// Employees 테이블의 데이터를 불러오는 기능의 추상메서드
	// List<EmployeeVO> : Employees 테이블의 읽어온 데이터를 List컬렉션으로 저장하고자 함.
	// 목록형태의 데이터는 List컬렉션 저장소 사용. 예> 회원목록, 상품목록, 주문목록, 게시판 목록 등등
	List<EmployeeVO> emp_list();
}
