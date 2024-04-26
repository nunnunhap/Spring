<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>상품정보</h3>
	<!-- product.name 필드처럼 참조했지만 내부적으론 getter가 동작 -->
	<p>상품이름은? <b>${ product.name }</b></p>
	<p>상품가격은? <b>${ product.price }</b></p>
</body>
</html>