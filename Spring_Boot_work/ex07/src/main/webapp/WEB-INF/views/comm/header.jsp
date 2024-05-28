<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<header>
  <!-- Fixed navbar -->
  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="/">Register</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
      <ul class="navbar-nav mr-auto">
        <!-- Join/ Login은 인증 전 표시/ sessionScope는 세션방식으로 서버측에 정보를 저장했을 때 영역을 가리킴.
        	 controller의 session.setAttribute()를 가리킴. 세션방식으로 저장한 방식을 가리킬 때 그 메모리 영역을 “세션 스코프”라고 함.
        	 그 안의 login_status라는 변수를 찾는 것. -->
        <c:if test="${sessionScope.login_status == null}">
        <li class="nav-item">
          <a class="nav-link" href="/userinfo/join">Join<!-- <span class="sr-only">(current)</span>--></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/userinfo/login">LogIn</a>
        </li>
        </c:if>
        <!-- MyPage/ LogOut은 인증 후 표시(현재 세션 방식 사용) -->
        <c:if test="${sessionScope.login_status != null}">
        <li class="nav-item">
          <a class="nav-link" href="/userinfo/mypage">MyPage<!-- <span class="sr-only">(current)</span>--></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/userinfo/logout">LogOut</a>
        </li>
        </c:if>
        <li class="nav-item">
          <a class="nav-link disabled">Disabled</a>
        </li>
      </ul>
      <form class="form-inline mt-2 mt-md-0">
        <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
      </form>
    </div>
  </nav>
</header>