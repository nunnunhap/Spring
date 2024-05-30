<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en" class="h-100">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>아이디 찾기</title>

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
  </head>
  <body class="d-flex flex-column h-100">
    
<%@ include file="/WEB-INF/views/comm/header.jsp" %>

<!-- Begin page content -->
<main role="main" class="flex-shrink-0">
  <div class="container">
    <h1 class="mt-5">Register</h1>
    <div class="row">
      <div class="col">
        <div class="card card-info">
          <div class="card-header">
            <h3 class="card-title">ID Find Form</h3>
          </div>
          
          <form class="form-horizontal" id="idFindForm" action="/userinfo/idfind" method="post">
            <div class="card-body">
              <div class="form-group row">
                <label for="u_name" class="col-sm-4 col-form-label">Name</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" id="u_name" name="u_name" value="홍길동">
                </div>
              </div>
              <div class="form-group row">
                <label for="u_email" class="col-sm-4 col-form-label">이메일</label>
                <div class="col-sm-6">
                  <input type="text" class="form-control" id="u_email" name="u_email" value="yyewkor@naver.com">
                </div>
                <div class="col-sm-2">
                  <button type="button" class="btn btn-link" id="btnMailAuthcode">인증번호 발송</button>
                </div>
              </div>
              <div class="form-group row">
                <label for="authcode" class="col-sm-4 col-form-label">인증코드</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" id="authcode" name="authcode" placeholder="authcode">
                </div>
              </div>
            </div>
            
            <div class="card-footer text-center">
              <button type="submit" class="btn btn-info btn-block" id="btnID">ID 찾기</button>
            </div>

          </form>
        </div>
      </div>
    </div>
  </div>
</main>

<%@ include file="/WEB-INF/views/comm/footer.jsp" %>

  <script>
    let msg = '${msg}'; // UserInfoController의 rttr.addFlashAttribute("msg", msg); 이름 // "failPW" or "failID"가 존재

    if(msg == "nameFail") {
      alert("이름을 정확히 입력해주세요.");
      document.getElementById("u_name").focus(); // jQuery대신 바닐라 JS로 사용했음.
      
      return;
    } else if(msg == "failAuthCode") {
      alert("인증코드를 제대로 입력해주세요.");
      document.getElementById("authcode").focus();
      
      return;
    }


    $(document).ready(function() {

      // 메일 인증코드 요청
      $("#btnMailAuthcode").on("click", function() {
        if($("#u_email").val() == "") {
          alert("메일을 입력하세요");
          $("#u_email").focus();
          
          return;
        }

        $.ajax({
          url : '/email/authcode',
          type : 'get',
          // Controller의 authcode()의 매개변수로 들어가는 EmailDTO의 receiverMail
          data : {receiverMail : $("#u_email").val(), type : "emailId"},
          dataType : 'text', // controller에서 return된 "success"가 text
          success : function(result) {
            if(result == "success") {
              alert("메일로 인증코드가 발급되었습니다.");
              $("#u_authcode").focus();
            }
          }
        });
      });



    }); // ready event end
  </script>
  </body>
</html>