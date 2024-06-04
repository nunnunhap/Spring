package com.docmall.demo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.UserInfoVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.mapper.UserInfoMapper;

import lombok.RequiredArgsConstructor;

// bean 자동생성 : userInfoServiceImpl(빈 생성 시 앞 글자는 소문자로 생성됨)
@Service // 중간계층의 비지니스 로직을 목적을 한 클래스
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
	// DI
	private final UserInfoMapper userInfoMapper;
	private final PasswordEncoder passwordEncoder;
	
	// 아이디 중복체크
	@Override
	public String idCheck(String u_id) {
		return userInfoMapper.idCheck(u_id);
	}
	
	// 회원가입 저장
	@Override
	public void join(UserInfoVO vo) {
		
		// 비밀번호를 암호화
		vo.setU_pwd(passwordEncoder.encode(vo.getU_pwd()));
		
		userInfoMapper.join(vo);
	}
	
	@Override
	public UserInfoVO login(String u_id) {
		return userInfoMapper.login(u_id);
	}
	
	@Override
	public void modify(UserInfoVO vo) {
		userInfoMapper.modify(vo);
	}
	
	@Override
	public void changePw(String u_id, String new_pwd) {
		userInfoMapper.changePw(u_id, new_pwd);
	}

	@Override
	public void delete(String u_id) {
		userInfoMapper.delete(u_id);
	}
	
	@Override
	public String idfind(String u_name, String u_email) {
		return userInfoMapper.idfind(u_name, u_email);
	}
	
	@Override
	public String pwfind(String u_id, String u_name, String u_email) {
		return userInfoMapper.pwfind(u_id, u_name, u_email);
	}
	
	@Override
	public void tempPwUpdate(String u_id, String enc_tempPw) {
		userInfoMapper.tempPwUpdate(u_id, enc_tempPw);
	}
	
	@Override
	public List<UserInfoVO> userList(Criteria cri) {
		return userInfoMapper.userList(cri);
	}
	
	@Override
	public int getTotalCount(Criteria cri) {
		return userInfoMapper.getTotalCount(cri);
	}
	
}
