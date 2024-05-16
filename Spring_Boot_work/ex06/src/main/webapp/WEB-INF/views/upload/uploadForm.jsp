<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>form태그를 이용한 파일 업로드</h3>
	<!-- <form> 태그의 인코딩 타입은 enctype="application/x-www-form-urlencoded" 가 기본값이라 생략가능.
		 하지만 파일 첨부를 할 경우 다른 방식의 인코딩 필요. 아래의 방식을 필수로 넣어주어야 함. -->
	<form method="post" action="uploadFormAction" enctype="multipart/form-data">
		<input type="file" name="uploadFile" multiple> <!-- multiple은 파일 복수선택 가능 -->
		<button type="submit">전송</button>
	</form>
</body>
</html>