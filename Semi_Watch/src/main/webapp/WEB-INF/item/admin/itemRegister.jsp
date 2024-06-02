<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String ctxPath = request.getContextPath();

%>

<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
<jsp:include page="../../header1.jsp" />

<style>
	body {
		font-family: 'Montserrat', sans-serif;
	}

	.container {
		margin: 2% auto;
		z-index: 1;
	}

	#whole {
		display: flex;
		background-color: #e6e6e6;
	}

	#img {
		padding-top: 15%;
		width: 70%;
	}

	img#previewImg {
		position: absolute;
		width: 250px;
		height: 250px;
		top: 25px;
	}

	.form-group {
		margin-bottom: 20px;
	}

.error {
    display: none;
    color: red;
    font-size: 12px;
    margin-top: 5px;
}
div.fileDrop{ display: inline-block; 
                  width: 100%; 
                  height: 100px;
                  overflow: auto;
                  background-color: #fff;
                  padding-left: 10px;}
                 
 div.fileDrop > div.fileList > span.delete{display:inline-block; width: 20px; border: solid 1px gray; text-align: center;} 
 div.fileDrop > div.fileList > span.delete:hover{background-color: #000; color: #fff; cursor: pointer;}
 div.fileDrop > div.fileList > span.fileName{padding-left: 10px;}
 div.fileDrop > div.fileList > span.fileSize{padding-right: 20px; float:right;} 
 span.clear{clear: both;} 

	td.control-label {
		font-weight: bold;
		vertical-align: middle;
		text-align: right;
	}

	td.input-group {
		display: flex;
		align-items: center;
		padding-left: 5%;
	}

	td.input-group input,
	td.input-group select {
		width: 100%;
		text-align: center;
	}

	#previewImg {
		max-width: 250px;
		max-height: 250px;
	}



</style>


<script type="text/javascript">

$(document).ready(function(){
	
	$("span.error").hide();
	
	$(document).on("change", "input.sal", function(e){
		
		// alert('ㅎ');
		const sal = Number($(e.target).val());
		
		if(isNaN(sal)){
			
			$(e.target).val('');
		}
		
		const point = sal/100;
		
		if(!isNaN(point) ){
			
			$("input:text[name='point']").val(point);
		}
		else{
			$("input:text[name='point']").val('');
		}
		
	}); // end of $(document).on("change", "input.sal", function(e){
	
	// ==>> 제품이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 시작 <<== //
	$(document).on("change", "input.img_file", function(e){
		
		const input_file = $(e.target).get(0);
		     
		// 자바스크립트에서 file 객체의 실제 데이터(내용물)에 접근하기 위해 FileReader 객체를 생성하여 사용한다.
        const fileReader = new FileReader();    
        
		fileReader.readAsDataURL(input_file.files[0]); 
		// FileReader.readAsDataURL() --> 파일을 읽고, result속성에 파일을 나타내는 URL을 저장 시켜준다.
		
		fileReader.onload = function(){
		// FileReader.onload --> 파일 읽기 완료 성공시에만 작동하도록 하는 것임.	
			
            // img태그 무조건쓰고 순수 자바스크립트로만 넣어야한다 미리보기는 이렇게!
            document.getElementById("previewImg").src = fileReader.result;
			
		};
    	
	}); // end of $(document).on("change", "input.img_file", function(e){} 
	
	// ==>> 제품이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 끝 <<== //
	
	<%-- === jQuery 를 사용하여 드래그앤드롭(DragAndDrop)을 통한 파일 업로드 시작 === --%>
	
		let file_arr = []; // 첨부되어진 파일 정보를 담아둘 배열 !!
		
		$("div#fileDrop").on("dragenter", function(e){
			
			e.preventDefault(); // 즉, e.preventDefault(); 는 해당 이벤트 이외에 별도로 브라우저에서 발생하는 행동을 막기 위해 사용하는 것이다.
			
			e.stopPropagation(); // 즉, 이벤트 버블링을 막기위해서 사용하는 것이다. 
			
			/* "dragenter" 이벤트는 드롭대상인 박스 안에 Drag 한 파일이 최초로 들어왔을 때 */
			
		// end of $("div#fileDrop").on("dragenter", function(e){})
		}).on("dragover", function(e){ 
		/* "dragover" 이벤트는 드롭대상인 박스 안에 Drag 한 파일이 머물러 있는 중일 때. 필수이벤트이다. dragover 이벤트를 적용하지 않으면 drop 이벤트가 작동하지 않음 */ 
	    	e.preventDefault();
	        e.stopPropagation();
	        $(this).css("background-color", "#ffd8d8");
	     
	     // end of }).on("dragover", function(e){    
	    }).on("dragleave", function(e){ /* "dragleave" 이벤트는 Drag 한 파일이 드롭대상인 박스 밖으로 벗어났을 때  */
	        e.preventDefault();
	        e.stopPropagation();
	        $(this).css("background-color", "#fff");
	        
	    // end of }).on("dragleave", function(e){     
	    }).on("drop", function(e){      
	    	/* "drop" 이벤트는 드롭대상인 박스 안에서 Drag 한것을 Drop(Drag 한 파일(객체)을 놓는것) 했을 때. 필수이벤트이다. */
	    	e.preventDefault();
	    	
	    	var files = e.originalEvent.dataTransfer.files;  
           
		   
		   if(files != null && files != undefined) {
				
				let html = "";
				
				const f = files[0]; // 어차피 files.length 의 값이 1 이므로 위의 for문을 사용하지 않고 files[0] 을 사용하여 1개만 가져오면 된다.
				
				let fileSize = f.size/1024/1024;  /* 파일의 크기는 MB로 나타내기 위하여 /1024/1024 하였음 */
				
				if( !(f.type == 'image/jpeg' || f.type == 'image/png') ) {
				   alert("jpg 또는 png 파일만 가능합니다.");
				   $(this).css("background-color", "#fff");
				   return;
				}
				
				else if(fileSize >= 10) {
				   alert("10MB 이상인 파일은 업로드할 수 없습니다.!!");
				   $(this).css("background-color", "#fff");
				   return;
				}
				
				else {
					
				   file_arr.push(f);
				   const fileName = f.name; // 파일명   
				
				   fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
				   
				   
				  html += 
                       "<div class='fileList'>" +
                           "<span class='delete'>&times;</span>" +
                           "<span class='fileName'>"+fileName+"</span>" +
                           "<span class='fileSize'>"+fileSize+" MB</span>" +
                           "<span class='clear'></span>" +
                       "</div>";
                       
                  $(this).append(html);
                  // 여기서 this 는 $("div#fileDrop").on("dragenter", function(e){ 의 $("div#fileDrop") 다!!!
                	  
					// ===>> 이미지파일 미리보기 시작 <<=== // 
                  // 자바스크립트에서 file 객체의 실제 데이터(내용물)에 접근하기 위해 FileReader 객체를 생성하여 사용한다.
              	  // console.log(f);
                  const fileReader = new FileReader();
                  fileReader.readAsDataURL(f); // FileReader.readAsDataURL() --> 파일을 읽고, result속성에 파일을 나타내는 URL을 저장 시켜준다. 
                
                  fileReader.onload = function() { // FileReader.onload --> 파일 읽기 완료 성공시에만 작동하도록 하는 것임. 
                  // console.log(fileReader.result); 
                  
                   document.getElementById("previewImg").src = fileReader.result;
                  };
                  // ===>> 이미지파일 미리보기 끝 <<=== //
				 
				
				} // end of else (용량에도 문제없고 정상적인 png, jpg 인경우)
				
			}// end of if(files != null && files != undefined)
				
			$(this).css("background-color", "#fff");
			// 단일 css 주는건 , 복수개는 {}
	       
	    }); // end of $("div#fileDrop").on("dragenter", function(e){

	 	// == Drop 되어진 파일목록 제거하기 == //
	 	$(document).on("click", "span.delete" , function(e) {
	 		
	 		let idx = $("span.delete").index($(e.target));
	 		// alert("인덱스 : " + idx);
	 		
	 		file_arr.splice(idx, 1);
	 		// console.log(file_arr);
	 		
	 		<%-- 자바스크립트 배열에서 요소삭제하는방법! .splice와 ( )
            배열명.splice() : 배열의 특정 위치에 배열 요소를 추가하거나 삭제하는데 사용한다. 
                            삭제할 경우 리턴값은 삭제한 배열 요소이다. 삭제한 요소가 없으면 빈 배열( [] )을 반환한다.
    
           	배열명.splice(start, 0, element);  // 배열의 특정 위치에 배열 요소를 추가하는 경우 
                       start   - 수정할 배열 요소의 인덱스
                       0       - 요소를 추가할 경우
                       element - 배열에 추가될 요소
           
            배열명.splice(start, deleteCount); // 배열의 특정 위치의 배열 요소를 삭제하는 경우    
                        start   - 수정할 배열 요소의 인덱스
                        deleteCount - 삭제할 요소 개수
    		--%> 
	 		
            $(e.target).parent().remove();
            document.getElementById("previewImg").src = "";
	 	
	 	
	 	}); // end of 추가이미지 x누를때 삭제되게하는 이벤트처리
	 	
	    <%-- === jQuery 를 사용하여 드래그앤드롭(DragAndDrop)을 통한 파일 업로드 끝 === --%>
	 	
	 	// 제품등록하기
	 	$("input:button[id='btnRegister']").click( function() {
	 		
	 		$("span.error").hide();
	 		
	 		let is_infoData_OK = true;
	 		
	 		// 필수로 입력해야할 태그에다가 class=infoData 를 주었다
	 		$(".infoData").each(function(index, elmt){
	 		// 해당클래스들(필수입력란) 반복문으로 유효성을 검사한다	
	 		
	 			const val = $(elmt).val().trim();
	 		
	 			if(val == ""){
	 				
	 				$(elmt).next().show();
	 				is_infoData_OK = false;
	 				return false; // 일반적인 for문의 break; 와 같은 기능 (언제뭐쓰는지 다시 복습)
	 				
	 			} // end of if val이 공백을 제거하고 비어있는지(입력이 안됐는지)
	 			
	 		}); // end of .each 유효성검사하는 for문
	 		
	 		if(is_infoData_OK){
	 			
	 			var formData = new FormData($("form[name='inputitem']").get(0)); 
	 			// 폼에 작성된 모든것 $("form[name='prodInputFrm']").get(0) 폼 에 작성된 모든 데이터 보내기
	            
	 			if(file_arr.length > 0) { // 추가이미지파일을 추가했을 경우
	 				
	 				// 첨부한 파일의 총합의 크기가 10MB 이상 이라면 전송을 하지 못하게 막는다.(필수는 아님)
	 				
	 				let sum_file_size = 0;
	 			
	 				for(let i=0; i<file_arr.length; i++){
	 					
	 					sum_file_size += file_arr[i].size;
	 					
	 				} // end of for
	 				
	 				if( sum_file_size >= 10*1024*1024 ) { // 첨부한 파일의 총합의 크기가 10MB 이상 이라면 
	                    alert("첨부한 추가이미지 파일의 총합의 크기가 10MB 이상이라서 제품등록을 할 수 없습니다.!!");
	                    return; // 종료
	                }
	 				else {
	 				// 첨부한 파일의 총합의 크기가 10MB 미만 이라면, formData 속에 첨부파일 넣어주기
	 					
	 					// 현재 추가선택파일의 표시되는 태그는 div다, 그러므로 formData.append("key값", 값)을 담아서 넘겨준다.
	 					
	 					formData.append("attachCount", file_arr.length); // 추가이미지파일의 배열의 길이(추가이미지 갯수)를 보내줘야 컨트롤러에서 반복돌린대
	 					
	 					file_arr.forEach(function(item, index){
	 					
	                		formData.append("attach"+index, item); // 추가첨부파일 파라미터명에다가 index를 추가하기. item 이 첨부파일이다.
	                		
	                	}); // end of file_arr.forEach(function( elmt,index,array){
	 					
	 				} // end of else
	 			
	 			} // end of if(file_arr.length > 0) {} 
	 			// end of 추가이미지파일을 추가했을 경우
	 			
	 			$.ajax({
	 				
	 				<%-- url : "<%= ctxPath%>/shop/admin/productRegister.up", --%>
	                url : "${pageContext.request.contextPath}/admin/itemRegister.flex",
	                type : "post",
	                data : formData,
	                
	                // 파일 전송할때 필수로 입력해야함 (default true)
	                processData:false,  // 파일 전송시 설정 
	                contentType:false,  // 파일 전송시 설정
	 			
	                dataType:"json",
	                success:function(json){
	                	         
	                   // console.log("~~~ 확인용 : " + JSON.stringify(json));
	                   
	                    if(json.result == 1) {
	                    	
	                    	const pdno = json.setpdno;
	                    	// alert(json.setpdno);
	                    	// alert(pdno);
	                    	
	                    	alert("색상별 재고수량 페이지로 이동합니다.");	 
	                    	
	                    	$("input:hidden[name='setpdno']").val(pdno);
	                    	
	                    	const frm = document.hiddenitemSet;
	                    	
	                    	frm.method = "get";
	                    	frm.action = "itemSetup.flex";
	 	                	frm.submit();
	                   
	                    }// end of if if(json.result == 1) {
	                 
	                    
	                },
	                error: function(request, status, error){
	                // alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	                   alert("첨부된 파일의 크기의 총합이 20MB 를 초과하여 제품등록이 실패했습니다.ㅜㅜ");
	              	} 
	 			
	 			}); // end of .ajax
	 			
	 		} // end of if 필수입력 유효성검사에 문제가 없을경우
	 			
	 	}); // end of $("input:button[id='btnRegister']").click( function() {
	

	 	
	
});
	 	


</script>



<div id="whole">
	<div id="img">
		<img alt="" src="/Semi_Watch/images/FY7ZMP0WYAA0Icg.png" />
	</div>

	<div class="container">
		<h2 class="py-3 pl-5">상품등록</h2>

		<form name="inputitem" enctype="multipart/form-data">
			<table id="tblProdInput" class="table">
				<tr class="form-group">
					<td class="control-label">상품명</td>
					<td class="input-group" style="width:60%;">
						<input name="pdname" placeholder="상품명을 입력하세요." class="form-control infoData" type="text" />
						<span class="error">상품명은 필수입력 사항입니다.</span>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">상품브랜드</td>
					<td class="input-group" style="width:60%;">
						<select name="brand" class="form-control infoData">
							<option value="">선택하세요</option>
							<option value="G-SHOCK">G-SHOCK</option>
							<option value="롤렉스">롤렉스</option>
							<option value="세이코">세이코</option>
							<option value="카시오">카시오</option>
						</select>
						<span class="error">필수선택</span>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">상품 대표이미지</td>
					<td class="input-group" >
						<input type="file" name="pdimg1" class="form-control-file infoData img_file" accept="image/*" />
						<span class="error">필수입력</span>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">상품 정가</td>
					<td class="input-group" style="width:60%;">
						<input type="text" name="price" class="form-control" />
						<span>&nbsp;원</span>
						<span class="error">필수입력</span>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">상품 판매가</td>
					<td class="input-group" style="width:60%;">
						<input type="text" name="saleprice" class="form-control sal" />
						<span>&nbsp;원</span>
						<span class="error">필수입력</span>
					</td>
				</tr>

				<tr class="form-group" style="height: 170px;">
					<td class="control-label">제품설명</td>
					<td class="input-group" style="width:60%;">
						<textarea name="pdcontent" rows="5" class="form-control"></textarea>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">상품 상세이미지</td>
					<td class="input-group">
						<input type="file" name="pd_contentimg" class="form-control-file infoData img_file" accept="image/*" />
						<span class="error">필수입력</span>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">상품 구매시 적립 포인트</td>
					<td class="input-group" style="width:60%;">
						<input type="text" name="point" class="form-control" />
						<span>&nbsp;POINT</span>
						<span class="error">필수입력</span>
					</td>
				</tr>

				<tr class="form-group">
					<td class="control-label">추가이미지파일(선택)</td>
					<td class="input-group" style="width:60%;">
						<span class="pb-2">파일을 1개씩 마우스로 끌어 오세요</span>
						<div id="fileDrop" class="fileDrop"></div>
					</td>
				</tr>

				<tr class="form-group" style="height: 300px;">
					<td class="control-label">이미지파일 미리보기</td>
					<td class="input-group">
						<img id="previewImg" src="<%= ctxPath%>/images/no-productimg.png" />
					</td>
				</tr>

				<tr class="form-group">
					<td colspan="2" style="text-align: center;">
						<input type="button" value="제품등록" id="btnRegister" class="btn btn-info btn-lg mr-5" />
						<input type="reset" value="취소" class="btn btn-danger btn-lg" />
					</td>
				</tr>
			</table>
		</form>

		<form name="hiddenitemSet">
			<input name="setpdno" type="hidden" value="" />
		</form>
	</div>
</div>

<jsp:include page="../../footer.jsp" />