package com.docmall.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.basic.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth2")
public class KakaoLoginController {
	
	// DI
	private final KakaoLoginService kakaoLoginService;
	private final UserService userService;
	
	@Value("${kakao.client.id}")
	private String clientId;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	// Step1 카카오로그인 API Server에게 인가코드를 요청하는 작업
	// 헤더는 없고, 요청 파라미터가 있는 경우 redirect 사용해도 됨.
	@GetMapping("/kakaologin")
	public String connect() {
// String class는 heap에 계속 생성되는데, StringBuffer는 최초 생성된 곳에 계속 추가해줌. 계속 추가해주는 작업이면 StringBuffer가 성능이 더 좋음.
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("response_type=code");
		url.append("&client_id=" + clientId);
		url.append("&redirect_uri=" + redirectUri);
		// 추가 옵션 : 재로그인 시 다시 절차를 밟고 로그인할 수 있도록.
		// url.append("&prompt=login");

		log.info("인가코드 : " + url.toString());
		
		return "redirect:" + url.toString();
	}
	
	// Step2 카카오 로그인 API에서 형재 개발사이트 callback 주소 호출
	// 카카오개발자 사이트에서 애플리케이션 등록과정에서 아래 주소를 설정을 이미 한 상태.
	@GetMapping("/callback/kakao") // 기본주소는 이미 있으니 .properties에서 나머지 주소 가지고 오기
	public String callback(String code, HttpSession session) { // code : 토큰 받기 요청에 필요한 인가 코드
		log.info("code : " + code);
		
		String accessToken = "";
		KakaoUserInfo kakaoUserInfo = null;
		
		try {
			// 카카오 로그인 API서버에게 로그인 인증 성공
			// 인증토큰을 이용하여 카카오 사용자에 대한 정보를 제공해주도록 설계됨.
			accessToken = kakaoLoginService.getAccessToken(code); // 인가코드를 통한 인증토큰 요청
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			kakaoUserInfo = kakaoLoginService.getKakaoUserInfo(accessToken);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(kakaoUserInfo != null) {
			log.info("사용자 정보 : " + kakaoUserInfo);
			//db 작업
			/*
			 * 카카오 로그인 사용자가 회원테이블에 존재유무
			 * 1) 존재하는 경우 : 회원수정작업은 회원테이블에서 가져오면 됨.
			 * 2) 존재 안 하는 경우 : 회원수정작업은 회원테이블 참조 안 하고, 해당 클래스를 에러없도록 작업
			 * 카카오 회원테이블에 존재유무
			 */
			
			// 인증을 세션방식으로 처리
			session.setAttribute("kakao_status", kakaoUserInfo); // 인증여부 구분
			session.setAttribute("accessToken", accessToken); // 카카오 로그아웃에 사용
			
			String sns_email = kakaoUserInfo.getEmail();
			String sns_login_type = userService.existsUserInfo(sns_email);
//			session.setAttribute("sns_type", sns_login_type);
			
			KakaoUserInfo kakaoUser = kakaoLoginService.existsKakaoInfo(sns_email);
			
			// 둘 다 데이터가 존재하지 않으면(회원테이블에도 존재 안하고, 카카오테이블에도 존재 안하면)
			// (userService.existUserInfo(sns_email) = null) && (kakaoLoginService.existskakaoInfo(sns_email) = null)
			// &&연산자라 왼쪽이 false면 오른쪽은 동작이 안 되어 성능상 좋음.
			// 같은 데이터 테스트 시 첫번째는 좌측은 true가 되고 우측은 true가 되어 전체 조건이 true가 되어 데이터가 삽입
			// 					두번째는 true가 되고 우측은 false가 되어 전체 조건이 false가 되어 데이터 삽입이 진행되지 않음.
			// 회원테이블에 존재하면 카카오테이블에 존재하지 않도록 작업 예정.
			//if(!(userService.existUserInfo(sns_email) != null) && (kakaoLoginService.existskakaoInfo(sns_email) != null)) {
			if(userService.existsUserInfo(sns_email) == null && kakaoLoginService.existsKakaoInfo(sns_email) == null) {
				// 카카오 테이블에 데이터 삽입작업
				kakaoLoginService.kakao_insert(kakaoUserInfo);
			}
			
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/kakaologout")
	public String kakaologout(HttpSession session) {
		
		String accessToken = (String)session.getAttribute("accessToken");
		
		if(accessToken != null && !"".equals(accessToken)) {
			try {
				kakaoLoginService.kakaoLogout(accessToken);
			} catch (JsonProcessingException ex) {
				throw new RuntimeException(ex);
			}
			session.removeAttribute("kakao_status");
			session.removeAttribute("accessToken");
		}
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
}
