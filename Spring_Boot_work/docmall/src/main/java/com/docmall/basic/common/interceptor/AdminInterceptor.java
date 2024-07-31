package com.docmall.basic.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.docmall.basic.admin.AdminVo;
import com.docmall.basic.user.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("preHandle 핸들핸들");
		
		boolean result = false;
		
		HttpSession session = request.getSession();
		AdminVo vo = (AdminVo)session.getAttribute("admin_state");
		
		if(vo == null) { // 요청이 인증되지 않은 상태 의미
			result = false; // result값이 false면 controller가 진행되지 않음.
			
			if(isAjaxRequest(request)) { // ajax요청이라는 의미
				response.sendError(400); // 400: client쪽의 문제란 것
			} else {
				// 원래 요청한 주소를 세션으로 저장하는 기능
				getTargetUrl(request);
				response.sendRedirect("/admin/");
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
	
}
