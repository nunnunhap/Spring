<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .uploadResult {
        width: 100%;
        background-color: gray;
    }
    
    .uploadResult ul {
        display: flex;
        flex-flow: row;
        justify-content: center;
        align-items: center;
    }
    
    .uploadResult ul li {
        list-style: none;
        padding: 10px;
    }
    
    .uploadResult ul li img {
        width: 100px;
    }
    .bigPictureWrapper {
        position: absolute;
        display: none;
        justify-content: center;
        align-items: center;
        top:0%;
        width:100%;
        height:100%;
        background-color: gray; 
        z-index: 100;
    }
    
    .bigPicture {
        position: relative;
        display:flex;
        justify-content: center;
        align-items: center;
    }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<h3>ajax를 이용한 업로드</h3>
	<div class="bigPictureWrapper">
        <div class="bigPicture"></div>
    </div>

    <div class="uploadDiv">
        <input type="file" name="uploadFile" multiple>
    </div>

    <div class="uploadResult">
        <ul>

        </ul>
    </div>
    <button id="uploadBtn">Upload</button>
<script>
    $(document).ready(function() {
        $("#uploadBtn").on("click", function() {
            let formData = new FormData(); // form태그에 해당하는 객체

            let inputFile = $("input[name='uploadFile']");

            let files = inputFile[0].files;

            console.log(files);

            for(let i = 0; i < files.length; i++) {
                formData.append("uploadFile", files[i]);
            }

            $.ajax({
                url : '/upload/uploadAjaxAction',
                processData : false,
                contentType : false,
                data : formData,
                type : 'post',
                dataType : 'json',
                success : function(result) {
                    console.log("파일정보 : " + result[0].fileName);
                }
            });
        });
    });
</script>
</body>
</html>