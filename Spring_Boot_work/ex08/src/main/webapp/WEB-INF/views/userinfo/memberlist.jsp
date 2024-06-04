<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/comm/config.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/views/comm/header.jsp" %>

    <div class="container-fluid">
        <div class="card">
            <div class="card-header">
                <h3 class="card-title">DB 화면 출력</h3>
            </div>
            
            <div class="card-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Zip-Code</th>
                        <th>Addr</th>
                        <th>AddrDetail</th>
                        <th>Phone</th>
                        <th>Regdate</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="userInfoVO"><!-- 이 var가 jsp에서 객체라고 보면 됨. -->
                            <tr>
                                <td>${userInfoVO.u_id}</td>
                                <td>${userInfoVO.u_name}</td>
                                <td>${userInfoVO.u_email}</td>
                                <td>${userInfoVO.u_zip_code}</td>
                                <td>${userInfoVO.u_addr}</td>
                                <td>${userInfoVO.u_addrdetail}</td>
                                <td>${userInfoVO.u_phone}</td>
                                <td><fmt:formatDate value="${userInfoVO.u_regdate}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="card-footer clearfix">
                <ul class="pagination pagination-sm m-0 float-right">
	                <c:if test="${pageMaker.prev}">
	                    <li class="page-item">
	                        <a class="page-link" href="/userinfo/memberlist?pageNum=${pageMaker.startPage - 1}&amount=${pageMaker.cri.amount}">[이전]</a>
	                    </li>
	                </c:if>
                    <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
                        <li class='page-item ${pageMaker.cri.pageNum == page ? "active" : ""}'>
                            <a class="page-link" href="/userinfo/memberlist?pageNum=${page}&amount=${pageMaker.cri.amount}">${page}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${pageMaker.next}">
                        <li class="page-item"><a class="page-link" href="/userinfo/memberlist?pageNum=${pageMaker.endPage + 1}&amount=${pageMaker.cri.amount}">[다음]</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>



</body>
</html>