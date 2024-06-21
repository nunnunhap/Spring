package com.docmall.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@Value("${kakao.client.id}")
	private String clientId;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	// Step1
	@GetMapping("/kakaologin")
	public String connect() {
// String class는 heap에 계속 생성되는데, StringBuffer는 최초 생성된 곳에 계속 추가해줌. 계속 추가해주는 작업이면 StringBuffer가 성능이 더 좋음.
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("response_type=code");
		url.append("&client_id=" + clientId);
		url.append("&redirect_uri=" + redirectUri);

		log.info("인가코드 : " + url.toString());
		
		return "redirect:" + url.toString();
	}
	
	// Step2 카카오 로그인 API에서 호출
	@GetMapping("/callback/kakao") // 기본주소는 이미 있으니 .properties에서 나머지 주소 가지고 오기
	public String callback(String code, HttpSession session) { // code : 토큰 받기 요청에 필요한 인가 코드
		log.info("code : " + code);
		
		String accessToken = "";
		KakaoUserInfo kakaoUserInfo = null;
		
		try {
			accessToken = kakaoLoginService.getAccessToken(code);
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
			
			// 세션작업
			session.setAttribute("kakao_status", kakaoUserInfo);
		}
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
}
