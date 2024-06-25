package com.docmall.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginService {
	
	private final KakaoMapper kakaoMapper;
	
	@Value("${kakao.client.id}")
	private String clientId;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	@Value("${kakao.oauth.tokenuri}")
	private String tokenUri;
	
	@Value("${kakao.oauth.userinfouri}")
	private String userinfoUri;
	
	@Value("${kakao.user.logout}")
	private String kakaologout;
	
	// 엑세스 토큰을 받기 위한 정보
	// https://kauth.kakao.com/oauth/token 주소 호출
	// 요청방식 : post
	// 헤더(header) : Content-type: application/x-www-form-urlencoded;charset=utf-8
	/* 본문(body) : 
	 * grant_type : authorization_code
	 * client_id : 앱 REST API 키
	 * redirect_uri : 인가 코드가 리다이렉트된 URI
	 * code : 인가 코드 받기 요청으로 얻은 인가 코드(실시간으로 달라짐)
	 * client_secret : 토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드
	 */
	public String getAccessToken(String code) throws JsonProcessingException {
		// 1) Http Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 2) Http Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		body.add("code", code);
		body.add("client_secret", clientSecret);
		
		// 3) Header + Body 정보를 Entity로 구성
		HttpEntity<MultiValueMap<String, String>> tokenKakaoRequest = new HttpEntity<MultiValueMap<String, String>>(body, headers);
		
		// 4) Http 요청 보내기(API Server에게 통신을 담당하는 기능을 제공하는 클래스)
		RestTemplate restTemplate = new RestTemplate();
		// exchange()로 카카오서버로 요청을 하고 옴.
		ResponseEntity<String> response = restTemplate.exchange(tokenUri, HttpMethod.POST, tokenKakaoRequest, String.class);
		
		// 5) Http 응답(JSON) -> Access Token 추출(Parsing)작업
		String responseBody = response.getBody(); // 내용을 getBody()를 통하여 가지고 올 수 있음.
		log.info("응답데이터 : " + responseBody);
		
		// ObjectMapper는 jackson.databind 라이브러리 내의 클래스
		ObjectMapper objectMapper = new ObjectMapper();
		
		// readTree() : tree구조로 데이터를 읽어옴.
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		return jsonNode.get("access_token").asText();
		// 데이터는 요청 시에도 header와 body, 응답도 heaer와 body로 옴.
		// token은 작은 단위의, 대개 쪼갤 수 없는 데이터에 사용함.
	}
	
	// access Token을 이용한 사용자 정보 받아오기 우린 id, email, nickname.
	public KakaoUserInfo getKakaoUserInfo(String accessToken) throws JsonProcessingException {
		
		// 1) Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 2) Body 생성 안함. API매뉴얼에서 필수 아님.
		
		// 3) Header + Body 정보를 Entity로 구성
		HttpEntity<MultiValueMap<String, String>> userInfoKakaoRequest = new HttpEntity<>(headers);
		
		// 4) Http 요청
		RestTemplate restTemplate = new RestTemplate();
		
		// 5) Http 응답
		ResponseEntity<String> responseEntity = restTemplate.exchange(userinfoUri, HttpMethod.POST, userInfoKakaoRequest, String.class);
		
		String responseBody = responseEntity.getBody();
		
		log.info("응답사용자 정보 : " + responseBody);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		// 인증토큰을 이용한 카카오 사용자에 대한 정보를 받아옴.
		Long id = jsonNode.get("id").asLong();
		String email = jsonNode.get("kakao_account").get("email").asText();
		String nickname = jsonNode.get("properties").get("nickname").asText();
		
		return new KakaoUserInfo(id, nickname, email);
	}
	
	// 카카오 로그아웃 	https://kauth.kakao.com/oauth/logout
	// 헤더 Authorization: Bearer ${ACCESS_TOKEN}
	// 헤더는 있고 파라미터는 없는 경우
	public void kakaoLogout(String accessToken) throws JsonProcessingException {
		
		// Http Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken); // " " 스페이스바 잊지 말기
		headers.add("Content-type", "application/x-www-form-urlencoded");
		
		// Http 요청작업
		HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
		
		// Http 요청하기
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(kakaologout, HttpMethod.POST, kakaoLogoutRequest, String.class);
		
		// 리턴된 정보 : JSON포맷의 문자열
		String responseBody = response.getBody();
		log.info("responseBody : " + responseBody);
		
		// JSON문자열을 Java객체로 역직렬화하거나 Java객체를 JSON으로 직렬화할 때 사용하는 jackson library클래스
		// ObjectMapper 생성 비율이 비싸기 때문에 bena/static으로 처리하는 것이 성능에 좋다.
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		Long id = jsonNode.get("id").asLong();
		
		log.info("id : " + id);
	}
	
	// 카카오 정보 존재유무
	public KakaoUserInfo existsKakaoInfo(String sns_email) {
		return kakaoMapper.existsKakaoInfo(sns_email);
	}
	
	// 카카오 정보 삽입
	public void kakao_insert(KakaoUserInfo kakaoUserInfo) {
		kakaoMapper.kakao_insert(kakaoUserInfo);
	}
	
	
}
