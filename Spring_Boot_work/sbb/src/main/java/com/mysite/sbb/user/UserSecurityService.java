package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// 로그인 인증을 담당하는 클래스
@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;
	
	// 이 메서드를 재정의하여 로그인 인증작업을 하도록 제공. 시큐리티에서 설정작업도 필요.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
		if(_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다."); // 예외 던지기
		}
		SiteUser siteUser = _siteUser.get(); // 여기 비밀번호까지 함께 들어있음.
		
		// GrantedAuthority : 권한 부여 인터페이스
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		// 로그인한 사용자가 관리자인지 일반인지에 따라 권한작업 정보를 추가
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
	
	
	
	
	
}
