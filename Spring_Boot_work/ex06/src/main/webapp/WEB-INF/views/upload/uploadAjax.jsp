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
        <ul></ul>
    </div>
    <button id="uploadBtn">Upload</button>
<script>
    $(document).ready(function() {
        
        // 파일 전송 클릭버튼
        $("#uploadBtn").on("click", function() {
            let formData = new FormData(); // form태그에 해당하는 객체

	        // <input>을 가리킴. 다만 name은 여러개 만들 수 있어서 복수의 의미. #id면 단수.
            let inputFile = $("input[name='uploadFile']");

	        // inputFile[0] : 첫번째 태그
	        // inputFile[0].files : 첫번째 태그에서 선택한 파일들
            let files = inputFile[0].files;

            console.log(files);

            for(let i = 0; i < files.length; i++) {
                formData.append("uploadFile", files[i]); // "uploadFile"이름을 스프링에서 참조
            }

            $.ajax({
                url : '/upload/uploadAjaxAction',
                processData : false, // 기본값 true. false의미? key:value값의 구조를 QueryString으로 변환
	            /* 기본값 true. false의미? "application/x-www-form-urlencoded;charset=UTF-8"
	               "multipart/form-data" 인코딩을 사용하여 전송. */
                contentType : false,
                data : formData, // 스프링으로 (서버)로 전송할 데이터
                type : 'post', // 요청방식
                dataType : 'json', // 스프링에서 호출된 메서드의 리턴타입
                success : function(result) { // JSON으로 넘어옴.
                	
                    // 업로드 파일 목록 정보
                    for(let i = 0; i < result.length; i++) {
                    	console.log("날짜폴더명", result[i].uploadPath);
                    	console.log("클라이언트에서 보낸 파일명", result[i].fileName);
                        console.log("중복되지 않은 파일명", result[i].uuid);
                        console.log("이미지 파일 여부", result[i].image);
                	}
                    
                    showUploadedFile(result);
                }
            });
        });

        // 파일 삭제 클릭 이벤트 X 클릭 // $("정적선택자").on("click", "동적선택자", function() {});
        // 동적 코드(만들어진 코드)는 정적코드(내가 친 코드)와 달리 다이렉트로 이벤트 설정 불가.
        $(".uploadResult").on("click", "span", function() {
            console.log("삭제이벤트"); // 언제나 log로 인식되나 확인할 것.
            
            let targetFile = $(this).data("file"); // data-file
            let type = $(this).data("type"); // data-type

            // 파일명에서 날짜폴더명을 분리해야 함.
            console.info("targetFile", targetFile);
            console.info("type", type);

            $.ajax({
                url : 'deleteFile',
                data : {fileName : targetFile, type : type}, // JS Object 문법
                dataType : 'text', // 스프링 메서드 리턴타입
                type : 'post',
                success : function(result) {
                    if(result = "success") {
                        alert("이미지가 삭제되었습니다.");
                    }
                }
            });
        });

    }); // ready end

    // 파일 업로드 목록 정보가 출력될 위치를 참조
    let uploadResult = $(".uploadResult ul");


    // 업로드된 파일정보를 받아, 그것을 List형태로 출력하는 목적
    // 위의 result가 아래의 uploadResultArr에 들어올 것임.
    function showUploadedFile(uploadResultArr) {
        let str = "";

        // jQuery의 each()는 $()안의 대상이 복수일 경우만 사용 가능
        // i는 순번이라서 처음은 0번임. obj는 AttachFileDTO 클래스의 구조를 가지고 있으며 i번째 파일 정보를 가지고 있음.
        $(uploadResultArr).each(function(i, obj) {
            if(!obj.image) { // 일반파일일 때
                // encodeURIComponent()는 JS에서 중요하게 사용됨.
                let fileCalpath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid);

                // 태그에 데이터를 숨기고 싶을 때 <예 : <p data-이름="값">
                str += "<li><div><a href='/upload/download?fileName=" + fileCalpath + "'><img src='/img/attach.png'>" +
                    obj.fileName + "</a><span style='cursor:pointer;' data-file=\'" + fileCalpath + "\' data-type='file'> X </span></div></li>";
            }else { // 이미지 파일일 때
                // encodeURIComponent() : (에러발생 400)서버에서 \역슬래시를 허용하지 않아서 인코딩으로 /로 바꿔주는 것.
                let fileCalpath = encodeURIComponent(obj.uploadPath + "/" + "s_" + obj.uuid);
                let originPath = obj.uploadPath + "\\" + obj.uuid;

                originPath = originPath.replace(new RegExp(/\\/g), "/");

                // href=\"javascript 이렇게 \를 넣은 이유는 이걸 빼면 두 번째 ""가 묶는 역할로 사용할 수 없기 때문.
                str += "<li><a href=\"javascript:showImage('" + originPath + "')\"><img src='display?fileName=" + fileCalpath + "'></a>" +
                    "<span style='cursor:pointer;' data-file=\'" + fileCalpath + "\' data-type='image'> X </span></li>";
            }
        });

        uploadResult.append(str);

    }
</script>
</body>
</html>