<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <div class="col-12">
            <div class="card card-primary">
                <div class="card-header">
                <h3 class="card-title">게시물 조회</h3>
                </div>
                <form action="/board/get" method="post">
                    <div class="card-body">
                        <div class="form-group">
                            <label for="title">Bno</label>
                            <input type="text" class="form-control" id="bno" name="bno" readonly value="${boardVO.bno }">
                        </div>
                        <div class="form-group">
                            <label for="title">Title</label>
                            <input type="text" class="form-control" id="title" name="title" readonly value="${boardVO.title }">
                        </div>
                        <div class="form-group">
                            <label for="content">Content</label>
                            <div>
                            	${boardVO.content }
                            </div>
                            <div>
                            	<!-- out태그가 데이타에 태그가 존재하면, escape 처리를 하여, HTML Entity로 변환해준다. < -> &lt;  > -> &gt; -->
                            	<c:out value="${boardVO.content }" escapeXml="true"></c:out>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="writer">Writer</label>
                            <input type="text" class="form-control" id="writer" name="writer" readonly value="${boardVO.writer }">
                        </div>
                        <div class="form-group">
                            <label for="writer">Regdate</label>
                            <input type="text" class="form-control" id="regdate" name="regdate" readonly value="<fmt:formatDate value="${boardVO.regdate }" pattern="yyyy-MM-dd" />">
                        </div>
                        <div class="form-group">
                            <label for="writer">Updatedate</label>
                            <input type="text" class="form-control" id="updatedate" name="updatedate" readonly value=<fmt:formatDate value="${boardVO.updatedate }" pattern="yyyy-MM-dd" />>
                        </div>
                        <div class="form-group">
                            <label for="writer">Viewcount</label>
                            <input type="text" class="form-control" id="viewcount" name="viewcount" readonly value="${boardVO.viewcount }">
                        </div>
                    </div>
                    
                    <div class="card-footer">
                        <button type="button" class="btn btn-primary" onclick="fn_modify(${boardVO.bno})">Modify</button>
                        <button type="button" class="btn btn-danger" onclick="fn_delete(${boardVO.bno})">Delete</button>
                        <button type="button" class="btn btn-primary" onclick="fn_list();">List</button>
                    </div>
                </form>
                </div>
        </div>
    </div>
</div>

<script>
    
    let actionForm = document.getElementById("actionForm"); // form태그 참조
    
    
    function fn_modify(bno) {
    	
    	location.href = "/board/modify?bno=" + bno;
    }
    
    function fn_delete(bno) {
        if(!confirm(bno + " 번 게시물을 삭제하겠습니까?")) return;

        location.href = "/board/delete?bno=" + bno;
    }

    function fn_list() {
    	location.href = "/board/list";
    }
</script>
</body>
</html>