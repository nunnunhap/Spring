package com.mysite.sbb.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; 
	
	public SiteUser create(String username, String email, String password) {
		// 일반적으로 insert/update 작동 시에는 리턴타입이 void인데 여기선 SiteUser임.
		
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 이렇게 직접 쓰는 것은 권장하지 않음. bean생성하여 사용하는 것을 권장함.
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		
		return user;
	}
	
	
	
	
}
