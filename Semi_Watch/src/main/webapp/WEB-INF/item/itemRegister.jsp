<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String ctxPath = request.getContextPath();
%>



<jsp:include page="../header1.jsp" />

<style>
body > div.container {
    margin: 2% auto;
    
}


#whole {
    border: solid 0px black;
    display: flex;
    background-color: #e6e6e6;
}

#img {
    border: solid 0px orange;
    padding-top: 6%;
    background-color: white;
	width:60%;
    text-align: center;
}

.inputitem .form-group {
    display: flex;
    align-items: center;
    margin-left: 3%;
    margin-bottom: 2%;
    
}

.inputitem .control-label {
    font-size: 18px;
    width: 30%;
    text-align: right;
    margin-right: 10px;
}

.input-group {
    width: 65%;
    align-items: center;
    
}

.form-control {
    width: 100%;
    border-radius: 1%;
    margin-right: 3%;
}

.error {
    display: none;
    color: red;
    font-size: 12px;
    margin-top: 5px;
}

.fileDrop {
    display: inline-block;
    width: 100%;
    height: 100px;
    overflow: auto;
    background-color: #fff;
    padding-left: 10px;
    border: 1px solid #ccc;
}

.fileDrop > .fileList > .delete {
    display: inline-block;
    width: 20px;
    border: solid 1px gray;
    text-align: center;
}

.fileDrop > .fileList > .delete:hover {
    background-color: #000;
    color: #fff;
    cursor: pointer;
}

.fileDrop > .fileList > .fileName {
    padding-left: 10px;
}

.fileDrop > .fileList > .fileSize {
    padding-right: 20px;
    float: right;
}

.clear {
    clear: both;
}


</style>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js" ></script>

<script type="text/javascript">

$(document).ready(function(){
	
	$("span.error").hide();
	
	// "제품수량" 에 스피너 달아주기
	$("input#spinnerPqty").spinner({
		spin:function(event,ui){
		   if(ui.value > 100) {
		      $(this).spinner("value", 100);
		      return false;
		   }
		   else if(ui.value < 1) {
		      $(this).spinner("value", 1);
		      return false;
		   }
		}
	});// end of $("input#spinnerPqty").spinner()-------

	// ==>> 제품이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 시작 <<== //
	$(document).on("change", "input.img_file", function(e){
		
		const input_file = $(e.target).get(0);
		// jQuery선택자.get(0) 은 jQuery 선택자인 jQuery Object 를 DOM(Document Object Model) element 로 바꿔주는 것이다. 
        // 파일첨부할때는 jQuery는 작동이 안되기때문에 순수한 자바스크립트(document.get)로 바꿔주는것이다
		// DOM element 로 바꿔주어야 순수한 javascript 문법과 명령어를 사용할 수 있게 된다.
		
		// console.log(input_file);
		// ﻿﻿<input type="file" name="pimage1" class="infoData img_file" accept="image/*">
		
		// console.log(input_file.files); // 내부적인 속성
		/*	FileList {0(키값): File(값), length: 1}
			0(키값):File(값) {name(키값): 'berkelekle심플라운드01.jpg'(값), lastModified: 1605506138000, lastModifiedDate: Mon Nov 16 2020 14:55:38 GMT+0900 (GMT+09:00), webkitRelativePath: '', size: 71317, …}
			length:1
			[[Prototype]]:FileList
		*/
		// 즉, []는 배열표기법이 아니라 대괄호표기법일뿐이다. [속성:속성값]
		
		// console.log(input_file.files[0]);
		/* File {name: 'berkelekle심플라운드01.jpg', lastModified: 1605506138000, lastModifiedDate: Mon Nov 16 2020 14:55:38 GMT+0900 (GMT+09:00), webkitRelativePath: '', size: 71317, …}
			>>설명<<
            name : 단순 파일의 이름 string타입으로 반환 (경로는 포함하지 않는다.)
            lastModified : 마지막 수정 날짜 number타입으로 반환 (없을 경우, 현재 시간)
            lastModifiedDate: 마지막 수정 날짜 Date객체타입으로 반환
            size : 64비트 정수의 바이트 단위 파일의 크기 number타입으로 반환
            type : 문자열인 파일의 MIME 타입 string타입으로 반환 
                   MIME 타입의 형태는 type/subtype 의 구조를 가지며, 다음과 같은 형태로 쓰인다. 
                  text/plain
                  text/html
                  image/jpeg
                  image/png
                  audio/mpeg
                  video/mp4
                  ...
    	*/
    	
		// console.log(input_file.files[0].name); // berkelekle심플라운드01.jpg
            
            
		// 자바스크립트에서 file 객체의 실제 데이터(내용물)에 접근하기 위해 FileReader 객체를 생성하여 사용한다.
        const fileReader = new FileReader();    
        
		fileReader.readAsDataURL(input_file.files[0]); 
		// FileReader.readAsDataURL() --> 파일을 읽고, result속성에 파일을 나타내는 URL을 저장 시켜준다.
		
		fileReader.onload = function(){
		// FileReader.onload --> 파일 읽기 완료 성공시에만 작동하도록 하는 것임.	
			
			// console.log(fileReader.result);
			/*
              data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAeAB4AAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAg 
              이러한 형태로 출력되며, img.src 의 값으로 넣어서 사용한다.
            */
          	
            // img태그 무조건쓰고 순수 자바스크립트로만 넣어야한다 미리보기는 이렇게!
            document.getElementById("previewImg").src = fileReader.result;
			
		};
    	
	}); // end of $(document).on("change", "input.img_file", function(e){} 
	
	// ==>> 제품이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 끝 <<== //
	

	
	
});

</script>




<div id="whole">
    <div id="img" class="col-md-4">
        <img alt="" src="<%= ctxPath %>/images/FY7ZMP0WYAA0Icg.png" />
    </div>

    <div class="container">
        <h2 class="py-3 pl-4">상품등록</h2>

        <form class="inputitem" enctype="multipart/form-data">
            <table id="tblProdInput">
                <!-- 상품명 -->
                <tr class="form-group">
                    <td class="control-label">상품명</td>
                    <td class="input-group">
                        <input name="first_name" placeholder="상품명을 입력하세요." class="form-control" type="text" />
                        <span class="error">상품명은 필수입력 사항입니다.</span>
                    </td>
                </tr>

                <!-- 상품브랜드 -->
                <tr class="form-group">
                    <td class="control-label">상품브랜드</td>
                    <td class="input-group" style="width:30% !important;">
                        <select name="brand" class="infoData form-control" >
                            <option value="선택하세요">선택하세요</option>
                            <option value="G-SHOCK">G-SHOCK</option>
                            <option value="롤렉스">롤렉스</option>
                            <option value="세이코">세이코</option>
                            <option value="카시오">카시오</option>
                        </select>
                        <span class="error">필수선택</span>
                    </td>
                </tr>

                <!-- 상품 대표이미지 -->
                <tr class="form-group">
                    <td class="control-label">상품 대표이미지</td>
                    <td class="input-group">
                        <input type="file" name="pdimg1" class="infoData img_file" accept="image/*" />
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <!-- 제품수량 -->
                <tr class="form-group">
                    <td class="control-label">제품수량</td>
                    <td class="input-group" >
                        <input style="width: 30px !important;" id="spinnerPqty" name="pqty" value="1"  /> 개
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <!-- 제품정가 -->
                <tr class="form-group">
                    <td class="control-label">제품정가</td>
                    <td class="input-group" style="width: 35% !important;">
                        <input type="text" name="price"  class="form-control"/> 원
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <!-- 제품판매가 -->
                <tr class="form-group">
                    <td class="control-label">제품판매가</td>
                    <td class="input-group" style="width: 35% !important;">
                        <input type="text" name="saleprice" class="form-control"/> 원
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <!-- 제품설명 -->
                <tr class="form-group">
                    <td class="control-label">제품설명</td>
                    <td class="input-group">
                        <textarea name="pdcontent" rows="5" class="form-control" ></textarea>
                    </td>
                </tr>

                <!-- 제품포인트 -->
                <tr class="form-group">
                    <td class="control-label">제품포인트</td>
                    <td class="input-group" style="width: 33% !important;">
                        <input type="text" name="point" class="form-control" /> POINT
                        <span class="error">필수입력</span>
                    </td>
                </tr>

                <!-- 추가이미지파일 -->
                <tr class="form-group">
                    <td class="control-label">추가이미지파일(선택)</td>
                    <td class="input-group">
                        <span>파일을 1개씩 마우스로 끌어 오세요</span>
                        <div id="fileDrop" class="fileDrop"></div>
                    </td>
                </tr>

                <!-- 이미지파일 미리보기 -->
                <tr class="form-group">
                    <td class="control-label">이미지파일<br>미리보기</td>
                    <td class="input-group">
                        <img id="previewImg" width="300" />
                    </td>
                </tr>

                <!-- 버튼 -->
                <tr class="form-group float-right">
                    <td colspan="2" class="text-center">
                        <input type="button" value="제품등록" id="btnRegister" class="btn btn-info btn-lg" /> 
                        <input type="reset" value="취소" class="btn btn-danger btn-lg" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp" />