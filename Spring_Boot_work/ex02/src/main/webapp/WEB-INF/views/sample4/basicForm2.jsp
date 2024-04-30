<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>회원정보</h3>
	<form action="/sample4/basicPro2" method="post">
		이름1 : <input type="text" name="list[0].name"><br>
		나이1 : <input type="text" name="list[0].age"><br>
		이름2 : <input type="text" name="list[1].name"><br>
		나이2 : <input type="text" name="list[1].age"><br>
		이름3 : <input type="text" name="list[2].name"><br>
		나이3 : <input type="text" name="list[2].age"><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>