<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>2조 홈페이지</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">



<%-- 공용 부트스트랩 --%>
<link href="<%= ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" rel="stylesheet">

<%-- 공용 제이쿼리/부트스트랩 --%>
<script src="<%= ctxPath%>/js/jquery-3.7.1.min.js" type="text/javascript"></script>
<script src="<%= ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctxPath%>/jquery-ui-1.13.1.custom/jquery-ui.min.js" ></script>

<%-- 헤더용 CSS <수정금지> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/normalize.css" class="headerCss" /> --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/main.css"  class="headerCss"/>

<%-- 헤더용 JS <수정금지> --%>
<script type="text/javascript" src="<%= ctxPath%>/js/header/products.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/header/cart.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/header/focusable-lightbox.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/header/main.js"></script>

</head>
<body>
<%-- start header --%>
 <header class="top-header">
    <div class="top-header__left">
      <button class="nav-btn" type="button" aria-controls="main-nav" aria-expanded="false">
        <span class="sr-only">Menu button</span>
        <img src="<%= ctxPath%>/images/header/icon-menu.svg" alt="" role="presentation">
      </button>
      <h1 class="logo">
        <a href="index.html" class="invisible" tabindex="0">
        <img src="<%= ctxPath%>/images/header/watch.svg">
        </a>
      </h1>
        <nav class="main-nav" id="main-nav" aria-label="Main menu">
          <div class="main-nav__content-container">
            <button class="main-nav__close-btn" type="button">
              <span class="sr-only">Close menu</span>
              <span class="icon icon-close" aria-hidden="true"></span>
            </button>
            <ul class="main-nav__links" style="z-index:9999">
              <li>
                <a href="<%= ctxPath %>/index.flex" class="nav-item">HOME</a>
              </li>
              <li>
                <a href="<%= ctxPath %>/item/itemList.flex" class="nav-item">Shop</a>
              </li>
              <li>
                <a href="<%= ctxPath %>/about/aboutus.flex" class="nav-item">About us</a>
              </li>
              <li>
                <a href="<%= ctxPath %>/serviceCenter.flex" class="nav-item">A/S center</a>  
              </li>
            <%-- admin start --%>
            <c:if test="${not empty sessionScope.loginuser and sessionScope.loginuser.userid == 'admin'}"> 
              <li>
                <a href="#" style="color:white !important; text-decoration: none !important; margin-top:3px;" class="nav-link dropdown-toggle menufont_size text-primary" id="navbarDropdown" data-toggle="dropdown">
                	관리자전용
                </a>
                 <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	                 <a class="dropdown-item text-primary" href="<%=ctxPath%>/member/memberList.flex">회원목록</a>
	                 <a class="dropdown-item text-primary" href="<%=ctxPath%>/item/itemRegister.flex">제품등록</a>
	                 <a class="dropdown-item text-primary" href="<%=ctxPath%>/member/reviewList.flex">리뷰관리</a>
	                 <div class="dropdown-divider"></div>
	                 <a class="dropdown-item text-primary" href="<%=ctxPath%>/item/orderList.flex">전체주문내역</a>
              	 </div>
              </li>
             </c:if>
            <%-- admin end --%>  
            </ul>
          </div>
        </nav>
    </div>
    <%-- wishList start --%>
    <div class="top-header__right">
      <button class="top-header__btn-cart" type="button" aria-controls="cart-section" aria-expanded="false">
        <span class="sr-only">Button wishlist</span>
        <span class="icon icon-cart" aria-hidden="true"></span>
        <span class="items-quantity">
          <span class="value">0</span><span class="sr-only">items</span>
        </span>
      </button>
      <a href="<%= ctxPath %>/item/itemCart.flex">
        <img src="<%= ctxPath%>/images/header/icon-cart.svg" style="margin-right:30px;" role="presentation">
      </a>
    <%-- user start --%>
     <c:if test="${empty sessionScope.loginuser}"> 
      <a href="<%= ctxPath %>/login/login.flex">
        <img src="<%= ctxPath%>/images/header/user.svg" style="margin-right:30px;" role="presentation">
      </a>
      </c:if>
      <c:if test="${not empty sessionScope.loginuser}"> 
	      <a class="user-container" aria-label="User section" href="<%= ctxPath %>/member/memberInfoChange.flex">
	        <img src="<%= ctxPath%>/images/member/usernormal.jpg" alt="" class="user-container__img" role="presentation">
	      </a>
      </c:if>
    <%-- user end --%>
    </div>
    <section class="cart-section" id="cart-section" aria-live="polite">
      <h3 class="cart-section__title">위시리스트</h3>
      <div class="cart-section__body">
        <p class="empty-msg">위시리스트가 비어있습니다. <br>좋아하는 상품을 찜해보세요!</p> 
        <ul class="cart-section__products">
        </ul>
        <button type="button" class="cart-section__btn-checkout">Checkout</button>
      </div>
      <div style="display:flex; flex-direction: column; border-radius:0px !important;">  
	    <button class="btn btn-dark" onclick="addCart()">장바구니로 이동하기</button>
      </div>
    </section>
    <%-- cart end  --%>
  </header>
  <%-- header end --%>