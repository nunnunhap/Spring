package com.docmall.demo.mapper;

import com.docmall.demo.domain.UserInfoVO;

// UserInfoMapper.xml mapper파일 구성이 정상적이어야 동작
// 스프링은 이 인터페이스를 구현한 프록시 클래스가 내부적으로 bean으로 자동생성되어 의존성 주입하게 됨. 인터페이스는 객체 생성 불가능.
public interface UserInfoMapper {
	
	// 아이디 중복체크
	String idCheck(String u_id);
	
	// 회원가입 저장
	void join(UserInfoVO vo);
	
	// 로그인(아이디 먼저 비교)
	UserInfoVO login(String u_id);
	
	
}
