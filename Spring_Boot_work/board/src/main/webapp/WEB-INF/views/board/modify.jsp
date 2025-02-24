<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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

<script src="/js/ckeditor/ckeditor.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <div class="card card-primary">
                <div class="card-header">
                <h3 class="card-title">글수정 폼</h3>
                </div>
                <form action="/board/modify" method="post">
                    <div class="card-body">
                    	<div class="form-group">
                            <label for="title">Bno</label>
                            <input type="text" class="form-control" id="bno" name="bno" value="${boardVO.bno }" readonly>
                         </div>
                        <div class="form-group">
                            <label for="title">Title</label>
                            <input type="text" class="form-control" id="title" name="title" placeholder="Enter title" value="${boardVO.title }">
                        </div>
                        <div class="form-group">
                            <label for="content">Content</label>
                            <textarea class="form-control" rows="3" id="content" name="content" placeholder="Enter content">${boardVO.content }</textarea>
                        </div>
                        <div class="form-group">
                            <label for="writer">Writer</label>
                            <input type="text" class="form-control" id="writer" name="writer" placeholder="Enter title" value="${boardVO.writer }">
                        </div>
                    </div>
                    
                    <div class="card-footer">
                    <button type="submit" class="btn btn-primary">Modify</button>
                    </div>
                </form>
                </div>
        </div>
    </div>
</div>
<script>
	  $(document).ready(function() {
        // ckeditor 환경설정. 자바스크립트 Ojbect문법
        var ckeditor_config = {
                resize_enabled : false,
                enterMode : CKEDITOR.ENTER_BR,
                shiftEnterMode : CKEDITOR.ENTER_P,
                toolbarCanCollapse : true,
                removePlugins : "elementspath", 
                //업로드 탭기능추가 속성. CKEditor에서 파일업로드해서 서버로 전송클릭하면 , 이 주소가 동작된다.
                filebrowserUploadUrl: '/board/imageupload' 
        }

        CKEDITOR.replace("content", ckeditor_config);

        console.log("ckediotr 버전: ", CKEDITOR.version);  // ckediotr 버전:  4.12.1 (Standard)
      });
</script>
</body>
</html>