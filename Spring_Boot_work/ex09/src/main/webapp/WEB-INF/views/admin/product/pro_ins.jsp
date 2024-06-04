<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품관리</title>
<%@ include file="/WEB-INF/views/comm/config.jsp" %>
</head>
<body>
	
	<select id="btn_category">
		<option>1차 카테고리 선택</option>
		<c:forEach items="${cateList}" var="cateVo">
			<option value="${cateVo.c_code}">${cateVo.c_name}</option>
		</c:forEach>
	</select>
    <select id="btn_subcategory">
        <option>2차 카테고리 선택</option>
    </select>

<script>
        $(document).ready(function() {
            
            // 1차 카테고리 클릭 시 2차 카테고리 출력
            $('select#btn_category').on("change", function() {
                // e.preventDefault(); // <a>의 링크 기능 제거
                let c_pcode = $(this).val(); // value의 코드값 읽어옴. <form>태그와 관련있으니 val()사용
                let cur_option = $(this);
                    
                $.ajax({
                    url : '/admin/product/subcatelist', // 2차 매핑주소
                    type : 'get',
                    data : {c_pcode : c_pcode}, // js의 object방식
                    dataType : 'json',
                    success : function(result) {
                    let sub_cate_str = "";
                    for(let i = 0; i < result.length; i++) {
                        sub_cate_str += '<option value="' + result[i].c_code + '">' + result[i].c_name + '</option>';
                    }
                    // console.log(sub_cate_str)
                    $("select#btn_subcategory").empty();
                    $("select#btn_subcategory").append(sub_cate_str);
                    }
                });
            });

          // 2차 카테고리 클릭 후 상품 리스트 출력
            $("div.subcategory").on("click", "option", function(e) {
                e.preventDefault();
                console.log("2차 카테고리 : " + $(this).attr("href"));
                console.log("2차 카테고리 : " + $(this).html());
                
                location.href = "상품리스트";
            });
        });
</script>
</body>
</html>