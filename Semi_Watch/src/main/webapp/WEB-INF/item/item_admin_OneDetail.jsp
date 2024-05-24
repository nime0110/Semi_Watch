<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<%
    String ctxPath = request.getContextPath();
   
%>

<jsp:include page="../header1.jsp" /> 

<style type="text/css">

  table.table-bordered > tbody > tr > td:nth-child(1) {
      width: 25%;
      font-weight: bold;
      text-align: right;
  }

</style>

<script type="text/javascript">
  
  $(document).ready(function(){ 
  
  	$("div#smsResult").hide();
  	
  	$("button#btnSend").click( function(){
  	
  		// console.log( $("input#reservedate").val() + " " + $("input#reservetime").val() );
  		// 2024-05-14 10:00
  		
  		
  		let reserverdate = $("input#reservedate").val();
  		// openAPI(문자전송) 같은것을 이용하려면 202405141000 형태로 바꿔줘야만한다!
  		reserverdate = reserverdate.split("-").join("");
  		// ["2024","05","14"] ==> "20240514"
  		// console.log(reserverdate);
  		
  		let reservertime = $("input#reservetime").val()
  		reservertime = reservertime.split(":").join("");
  		
  		const datetime = reserverdate + reservertime
  		
  		// console.log(datetime);
  		// 202405141046
  		
  		let dataObj;
        
        if( reservedate == "" || reservetime == "" ) {
           // 문자를 바로 보내기인 경우 
           dataObj = {"mobile":"${requestScope.mvo.mobile}",
                    "smsContent":$("textarea#smsContent").val()};
        }
        else {
           // 예약문자 보내기인 경우
           dataObj = {"mobile":"${requestScope.mvo.mobile}",
                    "smsContent":$("textarea#smsContent").val(),
                    "datetime":datetime};
        }
  		
  		
  		
  		$.ajax({
            url:"${pageContext.request.contextPath}/member/smsSend.up",
            
            type:"get",
            data:dataObj,
            <%-- {"getparameter 해오는 필드명":"값"} --%>
            dataType:"json",
            success:function(json){ 
               // json 은 {"group_id":"R2GWPBT7UoW308sI","success_count":1,"error_count":0} 처럼 된다. 
               
               if(json.success_count == 1) {
                  $("div#smsResult").html("<span style='color:red; font-weight:bold;'>문자전송이 성공되었습니다.^^</span>");
               }
               else if(json.error_count != 0) {
                  $("div#smsResult").html("<span style='color:red; font-weight:bold;'>문자전송이 실패되었습니다.ㅜㅜ</span>");
               }
               
               $("div#smsResult").show();
               $("textarea#smsContent").val("");
            },
            error: function(request, status, error){
               alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
         });
  		
  		
  	});
  	
  	
  
});// end of $(document).ready(function(){})-----------
  
</script>  


<div class="container">

	<c:if test="${empty requestScope.pvo}">
		존재하지 않는 회원 입니다.
	</c:if>
	
	<c:if test="${not empty requestScope.pvo}">
	
		
	
		<p class="h3 text-center mt-5 mb-4">::: ${requestScope.mvo.name} 님의 회원 상세정보 :::</p>
		
		<table class="table table-bordered" style="margin: 0 auto;">
		
         <tr>
            <td>아이디&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.userid}</td>
         </tr>
         <tr>
            <td>회원명&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.name}</td>
         </tr>
         <tr>
            <td>이메일&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.email}</td>
         </tr>
         <tr>
            <td>연락처&nbsp;:&nbsp;</td>
            <td></td>
         </tr>
         
         <tr>
            <td>우편번호&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.postcode}</td>
         </tr>
         <tr>
            <td>주소&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.address}&nbsp;
                ${requestScope.mvo.detailaddress}&nbsp;
                ${requestScope.mvo.extraaddress}
            </td>
         </tr>
         
         <tr>
         	<td>우편번호&nbsp;:&nbsp;</td>
            <td>
            	<c:choose>
            		<c:when test="${requestScope.mvo.gender == '1'}">남</c:when>
            		<c:otherwise>여</c:otherwise>
            	</c:choose>
            </td>	
         </tr>
         <tr>
            <td>생년월일&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.birthday}</td>
         </tr>
         <tr>
            <td>만나이&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.age}&nbsp;세</td>
         </tr>
         <tr>
            <td>코인액&nbsp;:&nbsp;</td>
            <td>
               <fmt:formatNumber value="${requestScope.mvo.coin}" pattern="###,###" />&nbsp;원
            </td>
         </tr>
         <tr>
            <td>포인트&nbsp;:&nbsp;</td>
            <td>
               <fmt:formatNumber value="${requestScope.mvo.point}" pattern="###,###" />&nbsp;POINT
            </td>
         </tr>
         <tr>
            <td>가입일자&nbsp;:&nbsp;</td>
            <td>${requestScope.mvo.registerday}</td>
         </tr>
         
		 </table>
		 
		 
	</c:if>
	
	<div class="text-center mb-5">
       <button type="button" class="btn btn-secondary" onclick="javascript:location.href='itemUpdateList.flex'">회원목록[처음으로]</button> 
       <button type="button" class="btn btn-success mx-5" onclick="javascript:history.back()">상품정보 수정</button>
       <button type="button" class="btn btn-success mx-5" onclick="javascript:history.back()">상품 삭제</button>
       							
       								
       <button type="button" class="btn btn-primary" onclick="javascript:location.href='${pageContext.request.contextPath}${requestScope.goBackURL}'">회원목록[검색된 결과]</button>								
	</div>

</div>

<jsp:include page="../footer.jsp"/>