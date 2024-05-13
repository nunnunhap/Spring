<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <style>
        table, td, th {
            border: 1px solid;
        }

        table {
            width: 70%;
            border-collapse: collapse;
        }
    </style>

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

    <!-- 1) Include Handlebars from a CDN -->
    <script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>

    <!-- 2) Handlebar Template -->
    <!-- {{}}가 핸들바 문법임. 데이터를 반복시킬 때 each 문법 사용, <tr>이 10개가 만들어짐! 데이터가 여러개면 each작업해주기. -->
    <!-- convertDate가 함수, replydate가 값 -->
    <script id="reply-template" type="text/x-handlebars-template">
        <table id="replytable">
            {{#each .}}
            <tr>
                <td>[<span id="rno_{{rno}}">{{rno}}</span>] <span id="replyer_{{rno}}">{{replyer}}</span> {{convertDate replydate}}</td>
            </tr>
            <tr>
                <td><span id="retext_{{rno}}">{{retext}}</span></td>
            </tr>
            <tr>
                <td>
                    <button type="button" name="btnReplyModify" data-rno="{{rno}}" class="btn btn-primary btn-sm">수정</button>
                    <button type="button" name="btnReplyDelete" class="btn btn-danger btn-sm">삭제</button>
                </td>
            </tr>
            {{/each}}
        </table>
    </script>

    <script>
        // Handlebar Template에서 사용할 사용자정의 함수 작업
        // 2024-05-09T07:42:36.000+00:00 형태에서 2024/05/09 로 변환
        Handlebars.registerHelper("convertDate", function(replydate) {
            const date = new Date(replydate);

            // 월, 일이 두 자리면 두 글자로 표현하고, 한 글자여도 두 글자로.
            let month = (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1 );
            let day = (date.getDate() < 10 ? "0" + (date.getDate()) : date.getDate());

            return date.getFullYear() + "/" + month + "/" + day;
        })
    </script>
</head>
<body>
    <div class="container-fluid" style="padding: 20px;">
        <div class="row">
            <div class="col-12">
                <div class="card card-primary">
                    <div class="card-header">
                        <h3 class="card-title">게시글 상세조회</h3>
                    </div>
                    <form id="actionForm" action="/board/list" method="get">
                    	<input type="hidden" name="bno" value="${boardVO.bno}">
                    	<input type="hidden" name="pageNum" value="${cri.pageNum}">
                    	<input type="hidden" name="amount" value="${cri.amount}">
                    	<input type="hidden" name="type" value="${cri.type}">
                    	<input type="hidden" name="keyword" value="${cri.keyword}">
                    </form>
                    <form action="/board/get" method="post">
                        <div class="card-body">
                            <div class="form-group">
                                <label for="bno">Bno</label>
                                <input type="text" class="form-control" id="bno" name="bno" readonly value="${boardVO.bno}">
                            </div>
                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" class="form-control" id="title" name="title" readonly value="${boardVO.title}">
                            </div>
                            <div class="form-group">
                                <label for="content">Content</label>
                                <textarea class="form-control" rows="3" id="content" name="content" readonly>${boardVO.content}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="writer">Writer</label>
                                <input type="text" class="form-control" id="writer" name="writer" readonly value="${boardVO.writer}">
                            </div>
                            <div class="form-group">
                                <label for="regdate">Regdate</label>
                                <input type="text" class="form-control" id="regdate" name="regdate" readonly value="<fmt:formatDate value="${boardVO.regdate}" pattern="yyyy-MM-dd hh:mm:ss" />">
                            </div>
                            <div class="form-group">
                                <label for="updatedate">Updatedate</label>
                                <input type="text" class="form-control" id="updatedate" name="updatedate" readonly value="<fmt:formatDate value="${boardVO.updatedate}" pattern="yyyy-MM-dd hh:mm:ss" />">
                            </div>
                            <div class="form-group">
                                <label for="viewcount">Viewcount</label>
                                <input type="text" class="form-control" id="viewcount" name="viewcount" readonly value="${boardVO.viewcount}">
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="button" class="btn btn-primary" onclick="fn_modify()">Modify</button>
                            <button type="button" class="btn btn-danger" onclick="fn_delete(${boardVO.bno})">Delete</button>
                            <button type="button" class="btn btn-primary" onclick="fn_list()">List</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" id="btnReplyWrite">
                    댓글쓰기
                </button>
                <!-- 댓글목록 위치 -->
                <div id="replyList"></div>
                <!-- 댓글 페이지 위치 -->
                <div id="replyPager"></div>
            </div>
        </div>
    </div>
    <script>
        let actionForm = document.getElementById("actionForm") // form태그 참조
		
        function fn_modify() {
        	actionForm.action = "/board/modify";
        	actionForm.submit();
        }
        
        function fn_delete(bno) {
            if(!confirm(bno + " 번 게시물을 삭제하시겠습니까?")) return;
            // location.href = "/board/delete?bno=" + bno; // location.href 해당 주소로 옮겨감.

            // <form id="actionForm" action="/board/list" method="get">에서 delete키를 누르면 아래의 주소로 바뀜.
            actionForm.setAttribute("action", "/board/delete");
        	// actionForm.action = "/board/delete"; 위의 setAttribute와 동일함.
            actionForm.submit();
        }

        // list는 사실 bno값을 받을 필욘 없지만 그냥 있는 코드 같이 쓰고자 함.
        function fn_list() {
        	actionForm.setAttribute("action", "/board/list");
            actionForm.submit();
        }

        // JQuery 작업을 하기 위한 기본
        // ready()이벤트 메서드 : 브라우저가 모든 웹 페이지의 내용을 읽고 시작되는 이벤트
        $(document).ready(function() {
            // 댓글 페이지 번호 클릭 이벤트 $("JQuery에서 태그를 참조할 때 : 선택자")
            // 제일 많이 사용되는 액션 : click
            /* 아래 선택자에 참조되는 태그가 동적으로 생성된 경우에는 이벤트 설정 불가(엄청 중요)
            $("nav ul li a").on("click", function() {
            });
            */

            /* 동적 태그를 이벤트 설정하는 경우
            $("정적태그 선택자").on("이벤트명", "동적태그 선택자", function(익명함수) {
            });
            */
            $("div#replyPager").on("click", "li a", function(e) {
                e.preventDefault(); // a태그의 href속성의 링크 기능을 없애줌.
                replyPage = $(this).attr("href"); // 클릭했던 자신을 가리키는 것이 $(this)임.

                // 페이지 전환이 안 되어서 확인해보니 정상적으로 떠서 클라이언트에 오류가 없음을 확인.
                // 확인결과 Controller쪽에서 new Criteria(1, 5)로 값을 준 것이 유지되어서 그럼.
                // console.log("페이지", replyPage);

                let url = "/replies/pages/" + bno + "/" + replyPage;
                getPage(url);
            });

            // 댓글쓰기 대화상자 버튼클릭. $()안에 댓글쓰기 버튼태그를 참조하는 선택자 넣어야 함.
            // id는 css에서 #을 넣어줌. 여기의 function()은 함수명이 없는 익명함수.
            $("#btnReplyWrite").on("click", function() {
                // console.log("댓글 버튼 클릭");

                $("#replyDialog").modal('show');
            });

            // 1) 모달대화상자 댓글 등록
            $("#btnModalReplySave").on("click", function() {
                // console.log("댓글 저장 버튼 클릭");
                // $("#replyer").val()는 reply를 id로 쓰는 input 태그의 value값
                let replyer = $("#replyer").val();
                let retext = $("#retext").val();

                // 댓글데이터를 json포맷으로 서버에 전송/ 정확한 그 게시물에 대한 댓글 데이터니까 bno값이 들어감.
                // 1) 지금 바로 아래가 javascript object 문법임. 댓글등록데이터를 js의 Object문법으로 표현
                let replyData = {bno : ${boardVO.bno}, replyer : replyer, retext : retext};
                // 2) 위의 js object 문법을 JSON 표현법으로 변환. 댓글데이터를 JSON변환하여 서버에 전송
                //console.log(JSON.stringify(replyData));
                $.ajax({
                    type : 'post',
                    url : '/replies/new', // 댓글 저장 매핑주소
                    headers : { // 이 데이터가 어떤 데이터인지. {MIME TYPE, 약속된 표현(나중에 설명)}
                        "Content-Type" : "application/json", "X-HTTP-Method-Override" : "POST"
                    },
                    dataType : 'text', // 스프링 주소의 메서드 리턴타입. text, json, html 등이 들어갈 수 있음.
                    // ReplyController의 메서드의 리턴값이 무엇이냐에 따라 다르게 작성. 우리는 String이라고 써서 text를 적어준 것.
                    data : JSON.stringify(replyData), // 서버로 전송할 JSON 데이터
                    success : function(data) {
                        if(data == "success") {
                            alert("댓글 등록됨");
                            let url = "/replies/pages/" + bno + "/" + replyPage;
                            getPage(url);

                            // 댓글 작성자, 내용 초기화
                            $("#replyer").val(""); // val()은 내용을 읽어오는 것. ()는 내용 넣어줌. ""은 빈문자열로 내용 초기화
                            $("#retext").val(""); // 내용 초기화
                            $("#replyDialog").modal('hide'); // 모달 대화상자 숨기기
                        }
                    }
                });
            });

            // 2) modal 대화상자 댓글 수정(댓글 목록에서 수정버튼 클릭 시)
            $("#btnModalReplyUpdate").on("click", function() {
                // console.log("댓글 저장 버튼 클릭");
                // $("#replyer").val()는 reply를 id로 쓰는 input 태그의 value값

                let replyer = $("#replyer").val();
                let retext = $("#retext").val();

                // 댓글데이터를 json포맷으로 서버에 전송/ 정확한 그 게시물에 대한 댓글 데이터니까 bno값이 들어감.
                // 1) 댓글수정데이터를 js의 Object문법으로 표현
                let replyData = {rno : $("#reply_rno").html(), replyer : replyer, retext : retext};
                // 2) 위의 js object 문법을 JSON 표현법으로 변환. 댓글데이터를 JSON변환하여 서버에 전송
                // console.log(JSON.stringify(replyData));
                // return;

                $.ajax({
                    type : 'put', // REST API방식사용하여 수정엔 put방식 사용함.
                    url : '/replies/modify', // 댓글 수정 주소
                    headers : { // 이 데이터가 어떤 데이터인지. {MIME TYPE, 약속된 표현(나중에 설명)}
                        "Content-Type" : "application/json", "X-HTTP-Method-Override" : "PUT"
                    },
                    dataType : 'text', // 스프링 주소의 메서드 리턴타입. text, json, html 등이 들어갈 수 있음.
                    // ReplyController의 메서드의 리턴값이 무엇이냐에 따라 다르게 작성. 우리는 String이라고 써서 text를 적어준 것.
                    data : JSON.stringify(replyData), // 서버로 전송할 JSON 데이터
                    success : function(data) {
                        if(data == "success") {
                            alert("댓글 수정됨");
                            let url = "/replies/pages/" + bno + "/" + replyPage;
                            getPage(url);

                            // 댓글 번호, 작성자, 내용 초기화
                            $("#reply_rno").html("");
                            $("#replyer").val(""); // val()은 내용을 읽어오는 것. ()는 내용 넣어줌. ""은 빈문자열로 내용 초기화
                            $("#retext").val(""); // 내용 초기화

                            $("#replyDialog").modal('hide'); // 모달 대화상자 숨기기
                        }
                    }
                });
            });


        // 댓글 수정버튼을 클릭 시
        // $("정적태그 선택자").on("이벤트명", "동적태그 선택자", function() {});
        $("div#replyList").on("click", "button[name='btnReplyModify']", function() {
            // console.log("수정버튼을 클릭");

            // $(this) : 클릭한 수정버튼 태그를 참조
            let rno = $(this).data("rno"); // <button data-rno="500">수정</button>
            // 이 find()는 children이랑 다르게 후손들을 찾아줌.
            // 입력양식(<input>등)태그는 val메서드, 일반태그는 html()메서드 사용
            let replyer = $(this).parents("table#replytable").find("#replyer_" + rno).html();
            let retext = $(this).parents("table#replytable").find("#retext_" + rno).html();

            // console.log("rno", rno);
            // console.log("replyer", replyer);
            // console.log("retext", retext);

            // 모달 대화상자에 값을 출력하는 작업
            // val은 form, input등에만 쓰고, 일반태그는 html()써야 함.
            $("#reply_rno").html(rno); // 일반태그 <span>
            $("#replyer").val(replyer); // 해당 위치의 replyer 값 변경
            $("#retext").val(retext);

            $("#replyDialog").modal('show');
        });
});


        // 게시물 글 번호
        let bno = ${boardVO.bno}; // 이건 el문법
        let replyPage = 1; // 처음 보이는 화면은 1페이지
        let url = "/replies/pages/" + bno + "/" + replyPage; // 댓글목록과 댓글페이지 정보를 요청하는 매핑주소(ReplyController클래스의 주소)

        // console.log("url", url); // 웹사이트에서 확인이 되었으니 다시 주석처리
        getPage(url);

        // 댓글 목록 함수
        function getPage(url) {
            // getJSON 이 ajax 기능을 지원해줌.
            $.getJSON(url, function(data) {
                // ReplyController가 data.list    data.pageMaker 형태로 참조됨.
                // console.log("list", data.list);
                // console.log("pageMaker", data.pageMaker);

                /* 수동으로 했던 작업
                let result = "";

                for(let i = 0; i < data.list.length; i++) {
                    result += "댓글번호 : " + data.list[i].rno + "<br>";
                    result += "댓글내용 : " + data.list[i].retext + "<br>";
                }

                // JQuery는 css3 선택자를 그대로 사용 가능. id라서 #으로 참조. class면 .으로 참조.
                $("#replyList").html(result); // 이게 이게 웹사이트에 그대로 출력됨.
                */

                displayReplyData(data.list, $("#replyList"), $("#reply-template"));
                displayReplyPaging(data.pageMaker, $("#replyPager"));
            });
        }

        // 댓글 목록 데이터 바인딩
        // 매개변수 : replyData 댓글목록데이터, target 댓글 목록이 출력될 태그 위치, template 댓글목록 UI 핸들바 템플릿
        function displayReplyData(replyData, target, template) {
            // Handlebars. 명령어가 위에 삽입한 Handlebar코드에 들어있음. compile()메서드는 문법검사. 그래서 templateObj가 문법검사까지 마친 결과를 참조함.
            let templateObj = Handlebars.compile(template.html()); // template은 table태그가 있는 코드고, 거기에 .html()하면 태그 안의 html을 읽어옴.
            // 위의 table태그와 데이터가 합쳐진 결과가 replyHtml에 들어옴.
            let replyHtml = templateObj(replyData);

            // console.log("댓글목록 : " + replyHtml);

            // target이 이 위치 <div id="replyList"></div>를 가리킴.
            target.empty(); // target변수가 참조하는 태그위치에 내용을 지움. empty()하지 않으면 append()때문에 데이터가 밑으로 계속 누적되어 들어감.
            target.append(replyHtml); // target변수가 참조하는 태그위치에 자식레벨로 replyHtml변수의 내용을 추가함.
        }

        // 댓글 페이징 작업
        // pageData : 페이징에 필요한 데이터
        // target : 페이징 기능이 삽입될 위치
        function displayReplyPaging(pageData, target) {
            /* bootstrap의 pagination
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </ul>
            </nav>
            */
            let pageStr = '<nav aria-label="Page navigation example">';
                pageStr += '<ul class="pagination">';

            // 이전 표시 여부 작업
            if(pageData.prev) {
                pageStr += '<li class="page-item">';
                pageStr += '<a class="page-link" href="' + (pageData.startPage - 1) + '">Previous</a></li>';
            }

            // 페이지 번호 작업
            // active는 <li>태그 안에 들어가야 효과가 나타남.
            for(let i = pageData.startPage; i <= pageData.endPage; i++) {
                let curPageClass = (pageData.cri.pageNum == i) ? 'active' : '';
                pageStr += '<li class="page-item ' + curPageClass + '">';
                pageStr += '<a class="page-link" href="' + i + '">' + i + '</a></li>';
            }

            // 다음 표시 여부 작업
            if(pageData.next) {
                pageStr += '<li class="page-item">';
                pageStr += '<a class="page-link" href="' + (pageData.endPage + 1) + '">Next</a></li>';
            }

            pageStr += '</ul></nav>';

            // target변수가 참조하는 태그내용에 pageStr변수의 값을 삽입(대입) 그래서 empty같은거 사용 안함.
            target.html(pageStr);
        }

    </script>

    <!-- Modal -->
    <div class="modal fade" id="replyDialog" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">댓글 <span id="reply_rno"></span></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="replyer">writer</label>
                    <input type="text" class="form-control" id="replyer" placeholder="Enter writer">
                </div>
                <div class="form-group">
                    <label for="retext">content</label>
                    <textarea class="form-control" id="retext" rows="3"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="btnModalReplySave">등록</button>
                <button type="button" class="btn btn-primary" id="btnModalReplyUpdate">수정</button>
                <button type="button" class="btn btn-primary" id="btnModalReplyDelete">삭제</button>
            </div>
        </div>
    </div>
    </div>
</body>
</html>