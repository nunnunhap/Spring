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

  <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Navbar</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav">
      <c:forEach items="${cateList}" var="vo">
      <li class="nav-item dropdown"><!-- btncategory라는 이름을 줌. -->
        <a class="nav-link dropdown-toggle btncategory" href="${vo.c_code}" role="button" data-toggle="dropdown" aria-expanded="false">
          ${vo.c_name}
        </a>
        <div class="dropdown-menu subcategory"></div>
      </li>
      </c:forEach>
      
    </ul>
  </div>
</nav>


<script>
  $(document).ready(function() {

    // 1차 카테고리 클릭 시 2차 카테고리 출력
    $('a.btncategory').on("click", function(e) {
      e.preventDefault(); // <a>의 링크 기능 제거
      let c_pcode = $(this).attr("href"); // href에 카테고리 코드값을 숨겨둠.
      // console.log("c_code", c_code);
      // return;
      let cur_a = $(this); // ajax사용 시, 이렇게 변수를 따로 뽑아놔야 함.

      $.ajax({
        url : '/category/subcatelist', // 2차 매핑주소
        type : 'get',
        data : {c_pcode : c_pcode}, // js의 object방식
        dataType : 'json',
        success : function(result) {
          let sub_code = "";
          for(let i = 0; i < result.length; i++) {
            // sub_code += `<a class="dropdown-item" href="${result[i].c_code}">${result[i].c_name}</a>`;
            sub_code += '<a class="dropdown-item" href="' + result[i].c_code + '">' + result[i].c_name + '</a>';
          }
          cur_a.next().empty(); // 카테고리를 클릭할 때마다 이전 것이 계속 추가되는 부분이 있어서 empty()처리해줌.
          cur_a.next().append(sub_code);
        }
      });
    });

    // 2차 카테고리 클릭 후 상품 리스트 출력
    $("div.subcategory").on("click", "a", function(e) {
      e.preventDefault();
      console.log("2차 카테고리 : " + $(this).attr("href"));
      console.log("2차 카테고리 : " + $(this).html());
      
      location.href = "상품리스트";
    });



  });


</script>
</body> 


</html>