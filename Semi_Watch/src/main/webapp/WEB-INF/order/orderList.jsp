<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
   String ctxPath = request.getContextPath();
%>
    
<jsp:include page="../header1.jsp"></jsp:include>


<%-- 회원정보수정 관련 js --%>
<script type="text/javascript" src="<%= ctxPath%>/js/member/memberInfoChange.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<%-- 회원정보수정 관련 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberInfoChange.css" />


<script type="text/javascript">
$(document).ready(function() {
   
   
    
});// end of $(document).ready(function() ----------
   
</script>

<%-- 회원정보 내용 시작 --%>

   <%-- 상단 바 시작 --%>
   <div class="pt-3" id="topBar">
      <div >
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
   <%-- 상단 바 끝 --%>

   
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
   
         <%-- 내용입력하는 부분 시작 --%>
         <div class="col-xl-9 mt-4 table-container" style="border:solid 1px red;">
          	<table class="table table-bordered" id="OrderTable">
             	<thead class="MyOrderTbl"> 
			       <tr>
			          <td align="center">주문코드</td>
			          <td align="center">주문총액</td>
			          <td align="center">주문일자</td>
			       </tr>
	   			</thead>
	   			<tbody>
	   				<c:forEach var="order" item="${requestScope.orderList}">
		   				<tr>
		   					<td align="center">${order.ordercode}</td>
		   					<td align="center">${order.total_price}</td>
		   					<td align="center">${order.total_orderdate}</td>
		   				</tr>
					</c:forEach>
	   			</tbody>
            </table>
         </div>
         <%-- 내용입력하는 부분 끝 --%>
       </div>
   </div>

<%-- 회원내용 정보 끝 --%>

<jsp:include page="../footer.jsp"></jsp:include>