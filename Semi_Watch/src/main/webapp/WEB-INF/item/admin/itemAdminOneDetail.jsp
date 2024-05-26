<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<%
    String ctxPath = request.getContextPath();
%>

<jsp:include page="../../header1.jsp" /> 

<style type="text/css">
  table.table-bordered > tbody > tr > td:nth-child(1) {
      width: 25%;
      font-weight: bold;
      text-align: right;
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
    
</style>

<script type="text/javascript">
$(document).ready(function() {

    $("span.error").hide();

    // Spinner 초기화
    for(let i = 1; i <= 3; i++){
        $(`input[name='spinnerPqty\${i}']`).spinner({
            spin: function(event, ui) {
                if (ui.value > 100) {
                    $(this).spinner("value", 100);
                    return false;
                } else if (ui.value < 1) {
                    $(this).spinner("value", 0);
                    return false;
                }
            }
        });
    }

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

    // Drop 되어진 파일목록 제거하기
    $(document).on("click", "span.delete", function(e) {
        let idx = $("span.delete").index($(e.target));
        file_arr.splice(idx, 1);
        $(e.target).parent().remove();
        document.getElementById("previewImg").src = "";
    });

    // 제품 등록하기
    $("input:button[id='btnUpdate']").click(function() {
        console.log("Update button clicked");

        let formIsValid = false;
        let cnt = 0;

        // 세트별로 입력된 값 검사
        for (let i = 1; i <= 3; i++) {
            const color = $(`select[name='color_select\${i}']`).val();
            const pqty = $(`input[name='spinnerPqty\${i}']`).val();

            if (color && pqty > 0) {
                $(`input:hidden[name='color\${i}']`).val(color);
                $(`input:hidden[name='pqty\${i}']`).val(pqty);
                cnt++;
                formIsValid = true;
            } else {
                $(`input:hidden[name='colo\r${i}']`).val('');
                $(`input:hidden[name='pqty\${i}']`).val('');
            }
        }

        $("span.error").hide();

           let is_infoData_OK = true;
               
/*        $(".infoData").each(function(index, elmt) {
            const val = $(elmt).val().trim();
            if (val == "") {
                $(elmt).next().show();
                is_infoData_OK = false;
                alert('안녕나야');
                return false;
            }
        });
*/
        if (is_infoData_OK) {
            console.log("Form data is valid");

            var formData = new FormData($("form[name='inputitem']").get(0));
            formData.forEach(function(value, key) {
                console.log(key + ": " + value);
            });

            if (file_arr.length > 0) {
                let sum_file_size = 0;
                file_arr.forEach(function(item) {
                    sum_file_size += item.size;
                });

                if (sum_file_size >= 10 * 1024 * 1024) {
                    alert("첨부한 추가이미지 파일의 총합의 크기가 10MB 이상이라서 제품등록을 할 수 없습니다.!!");
                    return;
                } else {
                    formData.append("attachCount", file_arr.length);
                    file_arr.forEach(function(item, index) {
                        formData.append("attach" + index, item);
                    });
                }
            }

            $.ajax({
                url: "${pageContext.request.contextPath}/admin/itemAdminUpdate.flex",
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                dataType: "json",
                success: function(json) {
                    if (json.result == 1) {
                        alert('상품수정 완료!');
                        location.href = "${pageContext.request.contextPath}/admin/itemUpdateList.flex";
                    }
                },
                error: function(request, status, error) {
                    alert("상품수정 실패!");
                    location.href = "${pageContext.request.contextPath}/admin/itemUpdateList.flex";
                }
            });
        }
    });

    // 추가 입력칸 보여주기
    let count = 1;
    window.showInput = function() {
        if (count < 3) {
            count++;
            document.getElementById('inputDiv' + count).style.display = '';
        } else {
            alert('더 이상 추가할 수 없습니다.');
        }
    };

    // 추가 입력칸 숨기기
    window.HideInput = function() {
        if (count >= 2) {
            document.getElementById('inputDiv' + count).style.display = 'none';
            count--;
        }
    };

    // 상품 삭제 함수
    window.product_delete = function(pdno) {
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/itemDelete.flex",
            type: "POST",
            data: { "pdno": pdno },
            dataType: "json",
            success: function(json) {
                if (json.result == 1) {
                    alert('상품이 성공적으로 삭제되었습니다.');
                    location.href = "itemUpdateList.flex";
                }
            },
            error: function() {
                alert('상품 삭제 중 오류가 발생했습니다.');
            }
        });
    };

}); // end of document.ready

</script>  

<div class="container">
	<c:if test="${empty requestScope.pvo}">
		등록되지 않은 상품 입니다.
	</c:if>
	
	<c:if test="${not empty requestScope.pvo}">
		<p class="h3 text-center mt-5 mb-4">${requestScope.pvo.pdno}번의 상품 상세정보</p>
	 <form name="inputitem" enctype="multipart/form-data">
		<table class="table table-bordered" style="margin: 0 auto;">
         <tr>
            <td>상품번호</td>
            <td colspan="3">${requestScope.pvo.pdno}
            <input type="hidden" name="pdno" value="${requestScope.pvo.pdno}" /></td>
         </tr>
         <tr>
            <td>상품명</td>
            <td colspan="3">
                <input type="text" name="pdname" id="pdname" maxlength="30" class="requiredInfo" value="${requestScope.pvo.pdname}" />
                <span class="error">상품명은 필수입력 사항입니다.</span>
            </td>
         </tr>
         <tr>
            <td>상품브랜드</td>
            <td colspan="3">
                <select name="brand" class="infoData form-control" style="width:20%;">
                    <option value="${requestScope.pvo.brand}">${requestScope.pvo.brand}</option>
                    <option value="G-SHOCK">G-SHOCK</option>
                    <option value="롤렉스">롤렉스</option>
                    <option value="세이코">세이코</option>
                    <option value="카시오">카시오</option>
                </select>
                <span class="error">필수선택</span>
            </td>
         </tr>
         <tr>
            <td>상품정가</td>
            <td colspan="3">
            	<input type="text" name="price" id="price" maxlength="30" class="requiredInfo" value="${requestScope.pvo.price}" />&nbsp;원
                <span class="error">상품정가는 필수입력 사항입니다.</span>
            </td>
         </tr>
         <tr>
            <td>판매가</td>
            <td colspan="3">
            	<input type="text" name="saleprice" id="saleprice" maxlength="30" class="requiredInfo sal" value="${requestScope.pvo.saleprice}" />&nbsp;원
                <span class="error">판매가는 필수입력 사항입니다.</span>
            </td>
         </tr>
         <tr>
            <td>적립 포인트</td>
            <td colspan="3">
               <input type="text" name="point" id="point" maxlength="30" class="requiredInfo" value="${requestScope.pvo.point}" />&nbsp; Point
               <span class="error">적립 포인트는 필수입력 사항입니다.</span>
            </td>
         </tr>
         <tr>
            <td>상품 구분 설정</td>
            <td colspan="3">
              	<select name="pdstatus" class="infoData form-control" style="width:20%;">
                 	<option value="${requestScope.pvo.pdstatus}">
                 	<c:choose>
                 	<c:when test="${requestScope.pvo.pdstatus == 1}">일반상품</c:when>
                 	<c:when test="${requestScope.pvo.pdstatus == 2}">인기상품</c:when>
                 	</c:choose>
                 	</option>
                    <option value="1">일반상품</option>
                    <option value="2">인기상품</option>
                </select>
               <span class="error">상품 설명은 필수입력 사항입니다.</span>
            </td>
         </tr>
         <tr>
            <td>상품 설명</td>
            <td colspan="3">
               <textarea name="pdcontent" rows="5" class="form-control" style="width:50%;">${requestScope.pvo.pd_content}</textarea>
               <span class="error">상품 설명은 필수입력 사항입니다.</span>
            </td>
         </tr>
         <c:forEach begin="1" end="3" varStatus="status">
         <tr id="inputDiv${status.count}" style="${status.count > 1 ? 'display:none;' : ''}">
         	<td>컬러명</td>
            <td>
                <select name="color_select${status.count}" class="infoData form-control">
                 	<option value="">컬러를 선택하세요</option>
                    <option value="black">블랙</option>
                    <option value="white">화이트</option>
                    <option value="gray">회색</option>
                    <option value="pink">분홍</option>
                    <option value="red">빨강</option>
                    <option value="navy">남색</option>
                    <option value="green">초록</option>
                    <option value="blue">파랑</option>
                    <option value="yellow">노랑</option>
                    <option value="none">단일컬러</option>
                </select>
            </td>
            <td>상품재고수량</td>
            <td colspan="2">
              <input name="spinnerPqty${status.count}" value="0" style="width: 30px; height: 20px;"> 개
                            
              <c:if test="${status.count == 1}">
              <button type="button" onclick="showInput()" style="border: none; margin-left:5%;">+</button>
              </c:if>
              
              <c:if test="${status.count > 1}">
              <button type="button" onclick="HideInput()" style="border: none; margin-left:5%;">-</button>
              </c:if>
               <input type="hidden" name="color${status.count}" value="" />
	           <input type="hidden" name="pqty${status.count}" value="" />
 			</td>
         </tr>
         </c:forEach>
         
         <tr>
         	<td>상품 대표이미지</td>
         	 
            <td colspan="2"> <span>등록된 상품 대표이미지</span>
            <img src="${pageContext.request.contextPath}/images/product/${requestScope.pvo.pdimg1}" style="width:40%;" />
            <span>등록된 상품 대표이미지 파일명<br>${requestScope.pvo.pdimg1}</span>
            </td>
            <td>
               <input type="file" name="pdimg1" class="infoData img_file" accept="image/*" />
               <span class="error">필수입력</span>
            </td>
         </tr>
         <tr>
            <td>상품 상세정보이미지</td>
            <td colspan="2">
            	<span>등록된 상세정보이미지 파일명<br>${requestScope.pvo.pd_contentimg}</span>
            </td>
            <td>
               <input type="file" name="pd_contentimg" class="infoData img_file" accept="image/*" />
               <span class="error">필수입력</span>
            </td>
         </tr>
         <tr>
             <td class="control-label">추가이미지파일(선택)</td>
             <td colspan="3" class="input-group">
                 <span>파일을 1개씩 마우스로 끌어 오세요</span>
                 <div id="fileDrop" class="fileDrop"></div>
             </td>
             <td colspan="2">
	             <span>등록된 추가이미지 <c:if test="${empty requestScope.imglist}"> 없음</c:if>
	                 	<c:if test="${not empty requestScope.imglist}">
	                 		<c:forEach var="img" items="${requestScope.imglist}" varStatus="status">
	                 			${img.imgfilename}<br>  
	                 		</c:forEach>
	                 	</c:if>
	             </span>
             </td>
         </tr>

         <!-- 이미지파일 미리보기 -->
         <tr>
             <td class="control-label">이미지파일<br>미리보기</td>
             <td colspan="3">
                 <img id="previewImg" style="width:40%; height:300px;" src="" />
             </td>
         </tr>
         <tr>
            <td>상품등록일자</td>
            <td colspan="3">${requestScope.pvo.pdinputdate}</td>
         </tr>
      </table>
      <div class="text-center mt-4">
      	  <c:set var="pdno" value="${requestScope.pvo.pdno}" />
          <input type="button" id="btnUpdate" value="상품수정하기" class="btn btn-primary">
      
          <input type="button" id="btnDelete" value="상품삭제하기" onclick="product_delete('${pdno}')" class="btn btn-danger">
      		
          <input type="button" id="btnCancel" value="상품수정취소" class="btn btn-warning">
      </div>
    </form>
	</c:if>
	
</div>

<jsp:include page="../../footer.jsp" /> 
