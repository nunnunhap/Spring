<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!doctype html>
<html lang="en" class="h-100">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>회원가입</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/sticky-footer-navbar/">
	<%@ include file="/WEB-INF/views/comm/config.jsp" %>
    

    <!-- Favicons -->
<link rel="apple-touch-icon" href="/docs/4.6/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="https://getbootstrap.com/docs/4.6/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="/docs/4.6/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon.ico">
<meta name="msapplication-config" content="/docs/4.6/assets/img/favicons/browserconfig.xml">
<meta name="theme-color" content="#563d7c">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    
    <!-- Custom styles for this template -->
    <link href="/sticky-footer-navbar.css" rel="stylesheet">
    
    <script>
    	let msg = '${msg}';

    	if(msg == 'success') {
    		alert("비밀번호가 변경되었습니다.");
    	}else if(msg == "failPW") {
        alert("현재 비밀번호를 확인해주세요.");
      }
    </script>
    
  </head>
  <body class="d-flex flex-column h-100">
    
<%@ include file="/WEB-INF/views/comm/header.jsp" %>

<!-- Begin page content -->
<main role="main" class="flex-shrink-0">
  <div class="container">
    <h1 class="mt-3 mb-4 text-center">비밀번호 변경</h1>
    <div class="row">
        <%@ include file="/WEB-INF/views/comm/navbar.jsp" %>
      <div class="col-8">
        <form class="form-horizontal" id="changePwForm" action="/userinfo/changepw" method="post">
          <div class="card-body">
            <div class="form-group row">
              <label for="cur_pwd" class="col-sm-4 col-form-label">현재 비밀번호</label>
              <div class="col-sm-6">
                <input type="password" class="form-control" id="cur_pwd" name="cur_pwd">
              </div>
            </div>
            <div class="form-group row">
              <label for="new_pwd" class="col-sm-4 col-form-label">신규 비밀번호</label>
              <div class="col-sm-6">
                <input type="password" class="form-control" id="new_pwd" name="new_pwd">
              </div>
            </div>
            <div class="form-group row">
              <label for="new_pwd2" class="col-sm-4 col-form-label">신규 비밀번호 확인</label>
              <div class="col-sm-6">
                <input type="password" class="form-control" id="new_pwd2" name="new_pwd2">
              </div>
            </div>
          </div>
          
          <div class="card-footer">
            <button type="button" class="btn btn-info btn-block" id="btnChangePw">비밀번호 변경</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/views/comm/footer.jsp" %>

  <script>
    $(document).ready(function() {
      // 비밀번호 변경 버튼
      $("#btnChangePw").on("click", function() {
        if($("#cur_pwd").val() == "") {
          alert("현재 비밀번호를 입력하시오.");
          $("#cur_pwd").focus();
          return;
        } else if($("#new_pwd").val() == "") {
          alert("신규 비밀번호를 입력하시오.");
          $("#new_pwd").focus();
          return;
        } else if($("#new_pwd2").val() == "") {
          alert("비밀번호 확인란을 입력하시오.");
          $("#new_pwd2").focus();
          return;
        } else if($("#new_pwd").val() != $("#new_pwd2").val()) {
          alert("신규 비밀번호가 일치하지 않습니다.");
          $("#new_pwd2").focus();
          $("#new_pwd2").val("");
          return;
        }

        $("#changePwForm").submit();
      });
    }); // ready event end
  </script>
  </body>
</html>