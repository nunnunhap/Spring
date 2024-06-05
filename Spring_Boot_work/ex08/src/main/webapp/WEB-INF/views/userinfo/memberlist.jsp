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
	            <div class="float-left">
	                    <form id="searchForm" action="/userinfo/memberlist" method="get">
	                        <div class="form-group">
	                            <select name="type">
	                                <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : ''}" />>----</option>
	                                <option value="I" <c:out value="${pageMaker.cri.type == 'I' ? 'selected' : ''}" />>아이디</option>
	                                <option value="N" <c:out value="${pageMaker.cri.type == 'N' ? 'selected' : ''}" />>이름</option>
	                                <option value="P" <c:out value="${pageMaker.cri.type == 'P' ? 'selected' : ''}" />>전화번호</option>
	                            </select>
	                            <input type="text" name="keyword" placeholder="검색어를 입력하시오" value="${pageMaker.cri.keyword}">
	                            <input type="hidden" name="pageNum" value="1">
	                            <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
	                            <input type="submit" class="btn btn-primary btn-sm" value="Search">
	                        </div>
	                    </form>
	                </div>
                <ul class="pagination pagination-sm m-0 float-right">
	                <c:if test="${pageMaker.prev}">
	                    <li class="page-item">
	                        <a class="page-link" href="/userinfo/memberlist?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.startPage - 1}&amount=${pageMaker.cri.amount}">[이전]</a>
	                    </li>
	                </c:if>
                    <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
                        <li class='page-item ${pageMaker.cri.pageNum == page ? "active" : ""}'>
                            <a class="page-link" href="/userinfo/memberlist?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${page}&amount=${pageMaker.cri.amount}">${page}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${pageMaker.next}">
                        <li class="page-item"><a class="page-link" href="/userinfo/memberlist?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.endPage + 1}&amount=${pageMaker.cri.amount}">[다음]</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>



</body>
</html>