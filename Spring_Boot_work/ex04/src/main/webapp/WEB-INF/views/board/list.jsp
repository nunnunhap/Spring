<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jstl 2.0때는 jstl/core였는데 3.0에선 에러 일어나니 jakarta.tags.core로 바꾸기. -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="12">
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">DB 화면 출력</h3>
                    </div>
                    
                    <div class="card-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>bno</th>
                            <th>title</th>
                            <th>writer</th>
                            <th>regdate</th>
                            <th>viewcount</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${list}" var="boardVO"><!-- 이 var가 jsp에서 객체라고 보면 됨. -->
                                <tr>
                                    <td>${boardVO.bno}</td>
                                    <!-- 테이블의 primary key를 조건식에 사용하는 것. 왜냐하면 데이터의 유일성을 보장하기 때문임.
                                    글번호 1번에 대한 내용을 볼 수 있음. bno파라미터에 사용자가 선택한 글 번호를 넘겨 get을 요청함. -->
                                    <td><a href="/board/get?bno=${boardVO.bno}">${boardVO.title}</a></td>
                                    <td>${boardVO.writer}</td>
                                    <td><fmt:formatDate value="${boardVO.regdate}" pattern="YYYY-MM-DD" /></td>
                                    <td>${boardVO.viewcount}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </div>
                    
                    <div class="card-footer clearfix">
                        <ul class="pagination pagination-sm m-0 float-right">
                            <li class="page-item"><a class="page-link" href="#">«</a></li>
                            <li class="page-item"><a class="page-link" href="#">1</a></li>
                            <li class="page-item"><a class="page-link" href="#">2</a></li>
                            <li class="page-item"><a class="page-link" href="#">3</a></li>
                            <li class="page-item"><a class="page-link" href="#">»</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>