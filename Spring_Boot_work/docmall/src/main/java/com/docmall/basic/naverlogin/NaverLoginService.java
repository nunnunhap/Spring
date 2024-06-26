package com.docmall.basic.naverlogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NaverLoginService {
	
	@Value("${naver.client.id}")
	private String clientId;
	
	@Value("${naver.redirect.uri}")
	private String redirectUri;
	
	@Value("${naver.client.secret}")
	private String clientSecret;
	
	// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=CLIENT_ID&state=STATE_STRING&redirect_uri=CALLBACK_URL
	public String getNaverAuthorizeUrl() throws UnsupportedEncodingException {
		UriComponents uriComponents = UriComponentsBuilder // 쿼리 스트링 만들 때 사용하는 클래스
				.fromUriString("https://nid.naver.com/oauth2.0/authorize")
				.queryParam("response_type", "code")
				.queryParam("client_id", clientId)
				.queryParam("state", URLEncoder.encode("1234", "UTF-8")) // "1234"는 임의의 값
				.queryParam("redirect_uri", URLEncoder.encode(redirectUri, "UTF-8"))
				.build();
		
		return uriComponents.toString();
	}
	
	// https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&code=EIc5bFrl4RibFls1&state=9kgsGTfH4j7IyAkg
	public String getNaverTokenUrl(NaverCallback callback) throws UnsupportedEncodingException {
	
		try { // 원격 서버 요청하다가 에러 났을 경우를 위한 예외처리
			// Access Token을 받기 위한 요청보내기 작업 후 결과값이 JSON으로 응답
			
			UriComponents uriComponents = UriComponentsBuilder // 쿼리 스트링 만들 때 사용하는 클래스
					.fromUriString("https://nid.naver.com/oauth2.0/token")
					.queryParam("grant_type", "authorization_code")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret) // "1234"는 임의의 값
					.queryParam("code", callback.getCode())
					.queryParam("state",URLEncoder.encode(callback.getState(), "UTF-8"))
					.build();
			
			URL url = new URL(uriComponents.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			
			// 입력스트림 작업(데이터를 읽어옴, stream은 byte임)
			// InputStreamReader클래스 : 바이트 기반의 스트림을 문자기반 스트림으로 변환하는 기능
			if(responseCode == 200) {
				// getInputStream()은 byte(byte stream)로 읽어오고 InputStreamReader()가 byte를 String으로 바꿔줌.
				// 보내고 싶은 데이터면 getOutputStream()
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}else { // BufferedReader를 쓰지 않으면 바이트 단위로 하나씩 읽어오나 이걸 사용 시 문자 기반으로 읽어옴.
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			
			br.close();
			
			log.info("응답데이터 : " + response.toString());
			
			return response.toString();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// https://openapi.naver.com/v1/nid/me
	/*
	 * 접근 토큰(access token)을 전달하는 헤더
		다음과 같은 형식으로 헤더 값에 접근 토큰(access token)을 포함합니다. 토큰 타입은 "Bearer"로 값이 고정되어 있습니다.
		Authorization: {토큰 타입] {접근 토큰]
		Authorization: Bearer AAAAPIuf0L+qfDkMABQ3IJ8heq2mlw71DojBj3oc2Z6OxMQESVSrtR0dbvsiQbPbP1/cxva23n7mQShtfK4pchdk/rc=
	 */
	// 사용자 정보 받아오기
	public String getNaverUserByToken(NaverToken naverToken) {
		String accessToken = naverToken.getAccess_token();
		String tokenType = naverToken.getToken_type();
		
		try {
			URL url = new URL("https://openapi.naver.com/v1/nid/me");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", tokenType + " " + accessToken);
			
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			
			// 입력스트림 작업(데이터를 읽어옴, stream은 byte임)
			// InputStreamReader클래스 : 바이트 기반의 스트림을 문자기반 스트림으로 변환하는 기능
			if(responseCode == 200) {
				// getInputStream()은 byte(byte stream)로 읽어오고 InputStreamReader()가 byte를 String으로 바꿔줌.
				// 보내고 싶은 데이터면 getOutputStream()
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}else { // BufferedReader를 쓰지 않으면 바이트 단위로 하나씩 읽어오나 이걸 사용 시 문자 기반으로 읽어옴.
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			
			br.close();
			
			log.info("사용자 정보 응답데이터 : " + response.toString());
			
			return response.toString();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void getNaverTokenDelete (String access_token) {
		
		try {
			
			UriComponents uriComponents = UriComponentsBuilder // 쿼리 스트링 만들 때 사용하는 클래스
					.fromUriString("https://nid.naver.com/oauth2.0/token")
					.queryParam("grant_type", "delete")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("access_token", URLEncoder.encode(access_token, "UTF-8"))
					.build();
			
			URL url = new URL(uriComponents.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			int responseCode = conn.getResponseCode();
			log.info("상태코드 : " + responseCode);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}
