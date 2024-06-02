<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String ctxpath = request.getContextPath();
%>

<%-- 회원정보수정 관련 js --%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<%-- 회원정보수정 관련 css --%>
<link rel="stylesheet" type="text/css" href="<%= ctxpath%>/css/member/memberInfoChange.css" />

<%-- 회원정보 내용 시작 --%>
<body>
	<%-- 상단 바 시작 --%>
	<div class="pt-3" id="topBar">
		<div >
			<h2 id="myPage">My Page</h2> 
		</div>
		<div class="row text-center" style="padding: 10px 0 20px 0;">
			<div class="col-xl-5 text-left" style="padding: 20px 0 20px 60px; font-size: 18pt;">
				${sessionScope.loginuser.username}&nbsp;님
			</div>
			<div class="col-2 font15" align="center">
				<a class="nav-link topSbj" href="<%= ctxpath%>/item/itemCart.flex" style="color: white;">
					장바구니
					<div class="mt-1">${sessionScope.cnt.cart_cnt}&nbsp;건</div>
				</a> 
			</div>
			<div class="col-2 font15" align="center">
				<a class="nav-link topSbj" href="#" style="color: white;">
					마일리지
					<div class="mt-1"><fmt:formatNumber pattern="###,###" value="${sessionScope.loginuser.mileage}"/>&nbsp;P</div>
				</a>
			</div>
			<div class="col-2 font15" align="center">
				<a class="nav-link topSbj" href="#" style="color: white;">
					후기
					<div class="mt-1">${sessionScope.cnt.review_cnt}&nbsp;개</div>
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
				        		<a class="nav-link" href="<%=ctxpath %>/order/orderList.flex">주문배송조회</a>
				      		</li>
				      		<li class="mb-4">
				        		<a class="nav-link" href="#">상품 리뷰</a>
				      		</li>

				      		<li>
				      			<span class="h5" id="menu_first">나의 계정설정</span>
				      		</li>
				      		<li>
				        		<a class="nav-link" href="<%=ctxpath%>/member/memberInfoChange.flex">회원정보수정</a>
				      		</li>
				    	</ul>
				  	</div>
	            </nav>
	        </div>
			<%-- 왼쪽 사이드 메뉴 끝 --%>
			
			
			<%-- 본문 내용 들어가는 시작 --%>
			<div class="col-xl-9 mt-4">
			
				
			