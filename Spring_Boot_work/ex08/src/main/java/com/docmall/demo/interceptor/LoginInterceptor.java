package com.docmall.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.docmall.demo.domain.UserInfoVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


// 용도? 인터셉터 기능을 하기 위한 클래스
// 어떤 매핑주소를 인터셉터 할 것인가? WebMvcConfig.java 파일에서 설정
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	// 아래 세개의 메서드가 이벤트 특징을 가지고 있음.
	// 순서	매핑주소가 요청발생 -> 1) preHandle 2) 컨트롤러 url주소의 메서드 3) postHandle 4) afterCompletion
	// Controller로 요청이 들어가기 전, 전에 가로채어 다음 메서드가 호출(실행)_됨
	// 그리고 리턴값이 true가 되면, 컨트롤로 진행되어짐.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("preHandle 핸들핸들");
		
		boolean result = false;
		
		HttpSession session = request.getSession();
		UserInfoVO vo = (UserInfoVO)session.getAttribute("login_status");
		
		if(vo == null) { // 요청이 인증되지 않은 상태 의미
			result = false; // result값이 false면 controller가 진행되지 않음.
			
			if(isAjaxRequest(request)) { // ajax요청이라는 의미
				response.sendError(400); // 400: client쪽의 문제란 것
			} else {
				// 원래 요청한 주소를 세션으로 저장하는 기능
				getTargetUrl(request);
				response.sendRedirect("/userinfo/login");
			}
			
		} else {
			result = true;
		}
		
		return result;
	}
	
	// 원래 요청한 주소
	private void getTargetUrl(HttpServletRequest request) {
		
		String uri = request.getRequestURI(); // /userinfo/mypage와 같은 주소
		String query = request.getQueryString(); // ? 물음표 뒤의 문자열	?userid=doccomsa
		
		if(query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		String targetUrl = uri + query;
		
		if(request.getMethod().equals("GET")) {
			// 요청한 주소에 대한 정보를 세션으로 저장시키겠단 뜻임.
			request.getSession().setAttribute("targetUrl", targetUrl);
		}
	}
	
	// 사용자 요청이 ajax요청인지 구분
	private boolean isAjaxRequest(HttpServletRequest request) {
		boolean isAjax = false;
		
		String header = request.getHeader("AJAX");
		
		if(header != null && header.equals("true")) {
			isAjax = true;
		}
		
		return isAjax;
	}
	

	// 컨트롤러의 url주소에 해당하는 메서드의 실행이 끝나고 return 값의 뷰(view) 화면을 처리하기 전에 이 메서드가 호출됨.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		log.info("postHandle 핸들핸들");
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	// 컨트롤러의 url주소에 해당하는 메서드에서 참조하는 뷰 화면처리가 완료된 후 호출됨.
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 개발하면서 사용해보신 적 없다고 하심.
		log.info("afterCompletion 핸들핸들");
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
}
