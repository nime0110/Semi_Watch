<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
   String ctxPath = request.getContextPath();
%>
    
<jsp:include page="../header1.jsp"></jsp:include>


<%-- 회원정보수정 관련 js --%>
<script type="text/javascript" src="<%= ctxPath%>/js/member/memberInfoChange.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<%-- 회원정보수정 관련 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberInfoChange.css" />

<style>
	#OrderTable,
	#OrderTable td,
	#OrderTable th{
		padding-left:0px !important;
		padding-right:0px !important;
	}
	

ul.pagination li {
   font-size: 12pt;
   border: solid 0px gray;

}
.pagination{

justify-content: center;
}


.pagination a {
    color: #555;
    float: left;
    padding: 8px 16px;
    text-decoration: none;
    transition: color .3s;
}

.pagination a.active {
   /*
    color: white;
    background-color: #2196F3;*/
    text-decoration: underline;
    font-weight: bolder;
    color:#2196F3;
}

.pagination a.active:hover,
.pagination a:hover:not(.active) {
    color: #2196F3;
}

.pagination .disabled {
    color: black;
    pointer-e
}    	

.userod {

	vertical-align: middle !important;
}	
	
</style>
<script type="text/javascript">
$(document).ready(function() {
	
	$("select[name='deliveryType']").change(function(e){
		
		const ordercode = $(e.target).closest('td').find("input:hidden[name='ordercode']").val();
		// lert(ordercode);
		func_choice($(e.target).val(),ordercode);
		
	}); 
	
	$("td.detail_ordercode").click(function(e){
	
		// alert($(e.target).text());
		
		const odrcode = $(e.target).text();
		
		const frm = document.hidden;
		frm.odrcode.value = odrcode;
		
		frm.method = "post";
		frm.action = "<%= ctxPath%>/admin/adminOrderDetail.flex";
		
		frm.submit();
		
	}); 
	
	
   
   
    
});// end of $(document).ready(function() ----------
		
function func_choice(deliveryType,ordercode){
	
	// alert(deliveryType);
	// alert(ordercode);
	$.ajax({
				
		url:"<%= ctxPath%>/admin/deliveryUpdateJSON.flex",
		data:{"deliveryType":deliveryType,
			"ordercode":ordercode},
		type:"post",
		dataType:"json",
		success:function(json){
			
			alert("주문번호" + ordercode+" 배송상태 변경 완료");
			location.href="<%= ctxPath%>/order/orderList.flex";
		},
		error: function(request, status, error){
           alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
		
	});  // end of $.ajax({
	
} // end of function func_choice(deliveryType,ordercode){
	
function deliveryComplete(ordercode){
	
	// alert(ordercode);
	
	$.ajax({
				
		url:"<%= ctxPath%>/order/deliveryCompleteJSON.flex",
		data:{"ordercode":ordercode},
		type:"post",
		dataType:"json",
		success:function(json){
			
			alert("주문번호" + ordercode+" 구매확정 완료");
			location.href="<%= ctxPath%>/order/orderList.flex";
		},
		error: function(request, status, error){
           alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
		
	});  // end of $.ajax({
	
	
} // end of function deliveryComplete(ordercode){


   
</script>

<%-- 회원정보 내용 시작 --%>
	<c:set var="userid" value="${requestScope.userid}" />
   <%-- 상단 바 시작 --%>
   <c:if test='${userid ne "admin"}'> 
   <div class="pt-3" id="topBar">
      <div>
         <h2 id="myPage">My Page</h2> 
      </div>
      <div class="row text-center" style="padding: 10px 0 20px 0;">
         <div class="col-xl-5" style="border: solid 1px blue; padding: 20px 0;">
            회원아이디
         </div>
         <div class="col" style="border: solid 1px blue;">
            <a class="nav-link" href="#" style="color: white;">
               장바구니
               <div>5 건</div>
            </a> 
         </div>
         <div class="col" style="border: solid 1px blue;">
            <a class="nav-link" href="#" style="color: white;">
               포인트
               <div>3000</div>
            </a>
         </div>
         <div class="col" style="border: solid 1px blue; ">
            <a class="nav-link" href="#" style="color: white;">
               쿠폰
               <div>5 개</div>
            </a>
         </div>
         <div class="col" style="border: solid 1px blue;">
            <a class="nav-link" href="#" style="color: white;">
               후기
               <div>5 개</div>
            </a>
         </div>
         
      </div>
   </div>
  
   <div class="container-fluid">
      <div class="row">
         <%-- 왼쪽 사이드 메뉴 시작--%>
           <div class="col-xl-2" id="subject" >
               <nav class="navbar sticky-top">
                  <div>
                   <ul class="navbar-nav mt-3" id="menu">
                      <li class="mb-1">
                           <span class="h5" id="menu_first">나의 쇼핑정보</span>
                        </li>
                        <li>
                          <a class="nav-link" href="#">주문배송조회</a>
                        </li>
                        <li>
                          <a class="nav-link" href="#">취소/교환/반품 내역</a>
                        </li>
                        <li class="mb-4">
                          <a class="nav-link" href="#">상품 리뷰</a>
                        </li>

                        <li>
                           <span class="h5" id="menu_first">나의 계정설정</span>
                        </li>
                        <li>
                          <a class="nav-link" href="#">회원정보수정</a>
                        </li>
                        <li>
                          <a class="nav-link" href="#">쿠폰</a>
                        </li>
                        <li>
                          <a class="nav-link" href="#">마일리지</a>
                        </li>
                        <li>
                          <a class="nav-link" href="<%=ctxPath%>/login/logout.flex">로그아웃</a>
                        </li>
                   </ul>
                 </div>
               </nav>
           </div>
         <%-- 왼쪽 사이드 메뉴 끝 --%>
         <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
		<fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
      <div class="pt-4 col-md-8" style="border:solid 0 px red; margin: 0 auto;">
			<p class="h4 text-center">&raquo;&nbsp;&nbsp;${loginuser.username} 님[ ${userid} ] 주문내역 목록&nbsp;&nbsp;&laquo;</p>
			<table class="table table-bordered" id="OrderTable">
	      		
	       <tr>
	          <td align="center">주문번호</td>
	          <td align="center">상품이미지</td>
	          <td align="center">상품명</td>
	          <td align="center">총결제금액</td>
	          <td align="center">주문일자</td>
	          <td align="center">상품상태</td>
	          <td align="center">배송확인</td>

	       </tr>
	        <c:forEach var="odr" items="${requestScope.order_map_List}">
	          <tr>
	          
	             <td class="userod detail_ordercode" align="center"><a href="">${odr.ordercode}</a></td>
	             <td class="userod" align="center"><img style="width: 70px; height:70px;"src="<%= ctxPath%>/images/product/${odr.pdimg1}"/></td>
	             <td class="userod" align="center">${odr.pdname}
	             <c:choose>
                  	<c:when test="${odr.cnt>1}"> 외 ${odr.cnt-1}건</c:when>
                  	<c:otherwise></c:otherwise>
                  </c:choose> 
	             </td>
	             <td class="userod" align="center">총 <fmt:formatNumber value="${odr.total_price}" type="number" groupingUsed="true" />원</td>
	             <td class="userod" align="center">${odr.total_orderdate}</td>
	             <td class="userod" align="center">${odr.delivery_status}</td>
	             <td class="userod" align="center">
	             <c:choose>
	             	<c:when test="${odr.delivery_status eq '배송중'}">
	             		<button class="btn btn-dark" type="button" onclick="deliveryComplete('${odr.ordercode}')" >구매확정</button>
	             	</c:when>
	             	<c:when test="${odr.delivery_status eq '주문완료'}">
	             		배송대기중
	             	</c:when>
	             	<c:when test="${odr.delivery_status eq '배송완료'}">
	             		구매확정완료
	             	</c:when>
	             </c:choose>
	             </td>
	          </tr>
	       </c:forEach>
	       </table>
	       		<div id="pageBar">
				    <nav>
				       <ul class="pagination">
				      	<li>${requestScope.pageBar}</li>
				       </ul>
				    </nav>
		    	</div>
		</div>
   
         </div>
      </div>
     </c:if>   
   
   
         <%-- 내용입력하는 부분 시작 --%>
         
		
			         
			       
         <c:if test='${userid eq "admin"}'>
         <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
		 <fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
            <div class="pt-4 col-md-8" style="border:solid 0 px red; margin: 0 auto;">
            	<p class="h4 text-center">&raquo;&nbsp;&nbsp;전 회원 주문내역보기&nbsp;&nbsp;&laquo;</p>
               	<p class="text-left">상세정보를 보려면 주문코드를 클릭하세요</p>
   				
   				<table class="table table-bordered" id="OrderTable">
                <tr>
                  <td align="center">주문코드</td>
                  <td align="center">아이디</td>
                  <td align="center">상품이름</td>
                  <td align="center">구매금액</td>
                  <td align="center">주문일자</td>
                  <td align="center">주문상품상태</td>
                  <td align="center">배송변경</td>
               </tr>
               <c:forEach var="odr" items="${requestScope.order_list_admin}">
               <tr>
                  <td align="center" style="cursor: pointer;"class="detail_ordercode">${odr.ordercode}</td>
                  <td align="center">${odr.fk_userid}</td>
                  <td align="center">${odr.pdname}
                  <c:choose>
                  	<c:when test="${odr.cnt>1}"> 외 ${odr.cnt-1}건</c:when>
                  	<c:otherwise></c:otherwise>
                  </c:choose> 
                  </td>
                  <td align="center">총 <fmt:formatNumber value="${odr.total_price}" type="number" groupingUsed="true" />원</td>
                  <td align="center">${odr.total_orderdate}</</td>
                  <td align="center">${odr.delivery_status}</td>
                  <td align="center">
                  	<select name="deliveryType" style="width:18%;">
                  		<option value="">선택하세요</option>
                  		<option value="1">주문완료</option>
                  		<option value="2">배송출발</option>
                  		<option value="3">배송완료</option>
                  	</select>
                  	<input name="ordercode" type="hidden" value="${odr.ordercode}" />
                 </td>
               </tr>
               </c:forEach> 
                            
               </table>
			   	<div class="row justify-content-center pt-3">
            
		            <div id="pageBar">
		                <nav>
		                   <ul class="pagination">
		                      <li>${requestScope.pageBar}</li>
		                   </ul>
		                </nav>
		             </div>
            
         </div>
			</div>
            </c:if>
			            
			            
			            
			           
			     
			   		
         <%-- 내용입력하는 부분 끝 --%>
       	<form name="hidden">
       		<input name="odrcode" type="hidden" value="" />
       	</form>

<%-- 회원내용 정보 끝 --%>

<jsp:include page="../footer.jsp"></jsp:include>