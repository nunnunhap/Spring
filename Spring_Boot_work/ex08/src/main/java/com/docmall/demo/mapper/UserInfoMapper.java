package com.docmall.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.dto.Criteria;

// UserInfoMapper.xml mapper파일 구성이 정상적이어야 동작
// 스프링은 이 인터페이스를 구현한 프록시 클래스가 내부적으로 bean으로 자동생성되어 의존성 주입하게 됨. 인터페이스는 객체 생성 불가능.
public interface UserInfoMapper {
	
	// 아이디 중복체크
	String idCheck(String u_id);
	
	// 회원가입 저장
	void join(UserInfoVO vo);
	
	// 로그인(아이디 먼저 비교)
	UserInfoVO login(String u_id);
	
	// 회원정보 수정
	void modify(UserInfoVO vo);
	
	// 비밀번호 변경 (암호화처리하여 들어와야 함) mybatis에서 파라미터가 두 개면 param처리 필요.
	void changePw(@Param("u_id") String u_id, @Param("new_pwd") String new_pwd);
	
	// 계정 삭제
	void delete(String u_id);
	
	// ID 찾기
	String idfind(@Param("u_name") String u_name, @Param("u_email") String u_email);
	
	// PW 찾기
	String pwfind(@Param("u_id") String u_id, @Param("u_name") String u_name, @Param("u_email") String u_email);
	
	// 비밀번호 업데이트 작업
	void tempPwUpdate(@Param("u_id") String u_id, @Param("enc_tempPw") String enc_tempPw);
	
	// 회원목록 리스트
	List<UserInfoVO> userList(Criteria cri);
	
	// 회원 게시물 총 갯수
	int getTotalCount(Criteria cri);
}
