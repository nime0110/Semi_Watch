<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String ctxPath = request.getContextPath();

%>
<jsp:include page="../header1.jsp" />

<style>
body > div.container {
    margin: 2% auto;
    z-index: 1;
    
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

.control-label {
    font-size: 18px;
    width: 18%;
    text-align: left;
    margin: 5% 0 5% 5%;
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



</style>


<script type="text/javascript">

$(document).ready(function(){
	
	$("span.error").hide();
	
	// "제품수량" 에 스피너 달아주기
	
	for(let i=1; i<=3; i++){
		
		$(`input[name='spinnerPqty\${i}']`).spinner({
			spin:function(event,ui){
			   if(ui.value > 100) {
			      $(this).spinner("value", 100);
			      return false;
			   }
			   else if(ui.value < 1) {
			      $(this).spinner("value", 0);
			      return false;
			   }
			}
		});// end of $("input#spinnerPqty").spinner()-------
		
	} // end of for 스피너
	

	
 	
    $("input#btnRegister").click(function() {
    	
    	var formIsValid = false;
        
    	let cnt = 0;
    	
        // 세트별로 입력된 값 검사
        for (let i = 1; i <= 3; i++) {
        
        	const color = $(`select[name='color_select\${i}']`).val();
            const pqty = $(`input[name='spinnerPqty\${i}']`).val();
            
            // console.log(color);
            // console.log(pqty);
            
            if (color && pqty > 0) {
                $(`input:hidden[name='color\${i}']`).val(color);
                $(`input:hidden[name='pqty\${i}']`).val(pqty);
                
                // console.log("color" + i + ": " + $(`input:hidden[name='color\${i}']`).val());
                // console.log("pqty" + i + ": " + $(`input:hidden[name='pqty\${i}']`).val());
                
                cnt++;
                formIsValid = true;
            } else {
                $("input:hidden[name='color" + i + "']").val();
                $("input:hidden[name='pqty" + i + "']").val();
            }
        }// end of for
        
        
        if (formIsValid) {
        	 // alert('성공' + cnt);
        	 
        	const frm = document.hiddenitemSet;
        	
        	frm.method = "post";
        	frm.action = "itemSetup.flex";
        	frm.submit();
        	        	 
        } else {
            alert('적어도 하나의 컬러와 수량 세트를 입력해 주세요.');
        }
 		
 	}); // end of $("input#btnRegister").click(function() {
 	
 		
 		
	
}); // end of $(document).ready(function(){})

let count = 1;

function showInput() {
    if (count < 3) {
        count++;
        const inputDiv = document.getElementById('inputDiv' + count);
        inputDiv.style.display = '';
    } else {
        alert('더 이상 추가할 수 없습니다.');
    }
}
 	

 	
</script>




<div id="whole">
    <div id="img" class="col-md-4">
        <img alt="" src="<%= ctxPath %>/images/FY7ZMP0WYAA0Icg.png" />
    </div>

    <div class="container">
        <h2 class="py-3 pl-4">상품 재고수량 및 옵션등록</h2>

        <form name="inputitem" enctype="multipart/form-data">
            <table id="tblProdInput">
                <!-- 상품명 -->
                <tr class="form-group">
                    <td class="control-label">상품명</td>
                    <td class="input-group">
                        <p>${requestScope.pvo.pdname}</p>
                    </td>
                </tr>
                
                <tr class="form-group">
                    <td class="control-label">브랜드명</td>
                    <td class="input-group">
                        <p>${requestScope.pvo.brand}</p>
                    </td>
                </tr>

				<tr>
            		
         		</tr>
                <!-- 상품 대표이미지 -->
                <tr id="inputDiv1" class="form-group">
                	<td class="control-label">컬러명</td>
                    <td class="input-group">
                        <select name="color_select1" class="infoData form-control" >
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
                    <td class="prodInputName">상품재고수량</td>
            		<td align="left" style="border-top: hidden; border-bottom: hidden;">
	                    <input name="spinnerPqty1" value="0" style="width: 30px; height: 20px;"> 개
	               		<span class="error">필수입력</span>
           			 </td>
                    <td class="input-group">
	                    <button type="button" onclick="showInput()">+</button>
	        			<input type="hidden" value="" />
        			</td>
                </tr>
                
                
                <tr id="inputDiv2" class="form-group" style="display:none;">
                	<td class="control-label">컬러명</td>
                    <td class="input-group">
                        <select name="color_select2" class="infoData form-control" >
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
                    <td class="prodInputName">상품재고수량</td>
            		<td align="left" style="border-top: hidden; border-bottom: hidden;">
	                    <input name="spinnerPqty2" value="0" style="width: 30px; height: 20px;"> 개
	               		<span class="error">필수입력</span>
           			 </td>
                    
                </tr>
                <tr id="inputDiv3" class="form-group" style="display:none;">
                	<td class="control-label">컬러명</td>
                    <td class="input-group">
                        <select name="color_select3" class="infoData form-control" >
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
                    <td class="prodInputName">상품재고수량</td>
            		<td align="left" style="border-top: hidden; border-bottom: hidden;">
	                    <input name="spinnerPqty3" value="0" style="width: 30px; height: 20px;"> 개
	               		<span class="error">필수입력</span>
           			 </td>
                </tr>
                
                 

                
                <!-- 버튼 -->
                <tr class="form-group">
                    <td colspan="2" class="text-center">
                        <input type="button" value="제품등록" id="btnRegister" class="btn btn-info btn-lg" /> 
                        <input type="reset" value="취소" class="btn btn-danger btn-lg" />
                    </td>
                </tr>
            </table>
        </form>
        <form name="hiddenitemSet">
        	<input name="pdno" type="hidden" value=""/>
        	<input name="color1" type="hidden" value=""/>
        	<input name="pqty1" type="hidden" value=""/>
        	<input name="color2" type="hidden" value=""/>
        	<input name="pqty2" type="hidden" value=""/>
        	<input name="color3" type="hidden" value=""/>
        	<input name="pqty3" type="hidden" value=""/>
        </form>
    </div>
</div>

<jsp:include page="../footer.jsp" />