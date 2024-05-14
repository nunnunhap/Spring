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
<!-- <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script> 
    기존에 쓰던 주소는 function getPage 지원이 안되어 아래로 바꿈.-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container-fluid">
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
                            <td>
                            	<%-- <a href="/board/get?bno=${boardVO.bno}&type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.cri.pageNum}&amount=${pageMaker.cri.amount}">${boardVO.title}</a> --%>
                            	<a class="move" href="${boardVO.bno}">${boardVO.title}</a>
                            </td>
                            <td>${boardVO.writer}</td> <!-- getter가 동작됨. -->
                            <td><fmt:formatDate value="${boardVO.regdate}" pattern="yyyy-MM-dd" /></td>
                            <td>${boardVO.viewcount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form id="actionForm" action="/board/list" method="get">
            	<input type="hidden" name="bno" value="">
            	<input type="hidden" name="type" value="${pageMaker.cri.type}">
            	<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
            	<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
            	<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
            </form>
            </div>
            
            <!-- 1 2 3 4 5 6 7 8 9 10 [다음] -->
            <!-- name이 Criteria 와 일치해야 함.-->
            <div class="card-footer clearfix">
                <div class="float-left">
                    <form id="searchForm" action="/board/list" method="get">
                        <div class="form-group">
                            <select name="type">
                                <option value="" <c:out value="${pageMaker.cri.type == null ? 'selected' : ''}" />>----</option>
                                <option value="T" <c:out value="${pageMaker.cri.type == 'T' ? 'selected' : ''}" />>제목</option>
                                <option value="C" <c:out value="${pageMaker.cri.type == 'C' ? 'selected' : ''}" />>내용</option>
                                <option value="W" <c:out value="${pageMaker.cri.type == 'W' ? 'selected' : ''}" />>작성자</option>
                                <option value="TC" <c:out value="${pageMaker.cri.type == 'TC' ? 'selected' : ''}" />>제목 or 내용</option>
                                <option value="TW" <c:out value="${pageMaker.cri.type == 'TW' ? 'selected' : ''}" />>제목 or 작성자</option>
                                <option value="TWC" <c:out value="${pageMaker.cri.type == 'TWC' ? 'selected' : ''}" />>제목 or 작성자 or 내용</option>
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
	                    	<!-- 파라미터를 Criteria cri로 했으면 pageNum, amount도 Criteria안의 필드명을 해야 함. -->
	                    	<!-- board/list 매핑 주소 요청 시 스프링에서 메서드의 파라미터가 Criteria cri로 되어 있기 때문에 
	                    		클라이언트에서 pageNum, amount 두 파라미터로 정보를 보내야 함. -->
                            <%--
	                        <a class="page-link" href="/board/list?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.startPage - 1}&amount=${pageMaker.cri.amount}">[이전]</a>
                            --%>
	                        <a class="page-link" href="${pageMaker.startPage - 1}">[이전]</a>
                        </li>
	                    
	                </c:if>
                    <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="page">
                        <li class='page-item ${pageMaker.cri.pageNum == page ? "active" : ""}'>
                            <%--
                            <a class="page-link" href="/board/list?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${page}&amount=${pageMaker.cri.amount}">${page}</a>
                            --%>
                            <a class="page-link" href="${page}">${page}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${pageMaker.next}">
                        <li class="page-item">
                        	<%--
                        	<a class="page-link" href="/board/list?type=${pageMaker.cri.type}&keyword=${pageMaker.cri.keyword}&pageNum=${pageMaker.endPage + 1}&amount=${pageMaker.cri.amount}">[다음]</a>
                        	--%>
                        	<a class="page-link" href="${pageMaker.endPage + 1}">[다음]</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            let actionForm = $("#actionForm")

            // 게시물 목록 제목 클릭 시
            $("a.move").on("click", function(e) {
                e.preventDefault(); // <a> 태그의 href 속성에 의한 링크 기능을 제거

                let bno = $(this).attr("href"); // .attr를 통해 속성 값을 불러오거나 속성 값 변경, 속성 값 추가가 가능하
                $("input[name='bno']").val(bno);

                actionForm.attr("action", "/board/get");
                actionForm.submit();
            });

           // 페이지 번호 클릭 시
           $("ul.pagination a.page-link").on("click", function(e) {
               e.preventDefault(); // <a> 태그의 href 속성에 의한 링크 기능을 제거

               // 페이지 작업
               $("input[name='pageNum']").val($(this).attr("href"));
               
               actionForm.attr("action", "/board/list");
               actionForm.submit();
           });
       });
   </script>
</body>
</html>