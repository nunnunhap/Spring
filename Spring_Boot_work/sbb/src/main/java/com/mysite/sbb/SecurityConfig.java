package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경설정 파일을 의미
@EnableWebSecurity // 모든 URL 요청이 스프링 시큐리티의 제어를 받게 함.
@EnableMethodSecurity(prePostEnabled = true)// 인증된 사용자만 호출이 가능한 컨트롤러 매핑주소 앞에 선언해야 함.
public class SecurityConfig { // 암기할 필요 없이 참조할 것.
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
					.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		// 모든 URL 요청 허용해서 시큐리티 로그인 주소 작동이 안됨.
			.csrf((csrf) -> csrf
					.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))) // 근데 지금 우리는 3버젼이라서 csrf()가, 시큐리티가 많이 변경되었음. 스프링부트 2점대인지 3점대인지 꼭 체킹할 것.
			.headers((headers) -> headers
	                .addHeaderWriter(new XFrameOptionsHeaderWriter(
	                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			.formLogin((formLogin) -> formLogin
					.loginPage("/user/login") // 시큐리티의 로그인 담당
					.defaultSuccessUrl("/")) // 로그인이 성공적으로 끝날 시 / 주소로 이동
			// 매핑주소 로그아웃 주소 /user/logout 을 컨트롤러에서 작업하지 않음.
			.logout((logout) -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)) // 세션작업
			;
		return http.build();
	}
	
	@Bean // 로그인 인증 시 사용하는 bean  @PreAuthorize("isAuthenticated()")
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean // 리턴값을 PasswordEncoder 인터페이스로 하는 이유? 추후 암호화클래스 변경 시 유지보수 작업의 편리성 생각.
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
