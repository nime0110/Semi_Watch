<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<%
    String ctxPath = request.getContextPath();
    // Semi_Watch
%>
<jsp:include page="../../header1.jsp" />

<style type="text/css">

  table.table-bordered > tbody > tr > td:nth-child(1) {
      width: 25%;
      font-weight: bold;
      text-align: right;
  }

</style>

<script type="text/javascript">
  
  $(document).ready(function(){ 
  	
	  
  	
  });// end of $(document).ready(function(){})-----------
  
  function goDeleteReview(){
	  
	  const frm = document.reviewnoFrm;
	  
	  let reviewno = frm.reviewno.value;
	  
	  frm.method = "post";
	  frm.action = "<%= ctxPath%>/member/reviewDel.flex";
	  
	  if (confirm("정말 삭제하시겠습니까??")){    //확인
		  frm.submit();
	  }
	  else{   //취소
	      return;
	  }
	  
 
  }// end of function goDeleteReview() 
  
</script>
<div class="container">
	<c:if test= "${empty requestScope.rvo}">
		존재하지 않는 회원입니다<br>
	</c:if>
	
	<c:if test= "${not empty requestScope.rvo}">
	
		<p class="h3 text-center mt-5 mb-4">::: ${requestScope.rvo.mvo.username} 님의 리뷰 상세정보 :::</p>
		<table class="table table-bordered" style="width: 60%; margin: 0 auto;">
         <tr>
            <td>리뷰번호&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.reviewno}</td>
         </tr>
         <tr>
            <td>브랜드&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.pvo.brand}</td>
         </tr>
         <tr>
            <td>제품명&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.pvo.pdname}</td>
         </tr>
         <tr>
            <td>제품이미지&nbsp;:&nbsp;</td>
            <td>
            <img src="<%= ctxPath%>/images/product/${requestScope.rvo.pvo.pdimg1}" class="img-thumbnail" width="130px" height="100px"/>
            </td>
         </tr>
         <tr>
            <td>유저아이디&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.mvo.userid}</td>
         </tr>
         <tr>
            <td>리뷰내용&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.review_content }</td>
         </tr>
         <tr>
            <td>별점&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.starpoint}</td>
         </tr>
         <tr>
            <td>리뷰작성일자&nbsp;:&nbsp;</td>
            <td>${requestScope.rvo.review_date}</td>
         </tr>
		</table>
	

	</c:if>
	<div class="text-center mb-5">
       <button type="button" class="btn btn-secondary" onclick="javascript:location.href='reviewList.flex'">리뷰목록[처음으로]</button> 
       <button type="button" class="btn btn-success mx-5" onclick="javascript:location.href='${pageContext.request.contextPath}${requestScope.goBackURL}'">리뷰목록[검색된결과]</button>
    	<!-- ctxPath인 http://localhost:9090/Semi_Watch/까지가 빠져있으므로 이렇게 끼워넣어 준다. -->
    	<button type="button" class="btn btn-secondary" id="deletebtn" onclick="goDeleteReview()">리뷰삭제하기</button>
    </div>
</div>

<form name="reviewnoFrm">
	<input type="hidden" name="reviewno" value="${requestScope.rvo.reviewno}"/>
</form>

<jsp:include page="../../footer.jsp" /> 