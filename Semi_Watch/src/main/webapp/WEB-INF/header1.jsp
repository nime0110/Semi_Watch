<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>2조 홈페이지</title>

<%-- 공용 부트스트랩 --%>
<link href="<%= ctxPath%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" rel="stylesheet">

<%-- 공용 제이쿼리/부트스트랩 --%>
<script src="<%= ctxPath%>/js/jquery-3.7.1.min.js" type="text/javascript"></script>
<script src="<%= ctxPath%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" type="text/javascript"></script>

<%-- 헤더용 CSS <수정금지> --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/main.css" />

<%-- 헤더용 JS <수정금지> --%>
<script type="text/javascript" src="<%= ctxPath%>/js/header/cart.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/header/main.js"></script>
<script type="text/javascript" src="<%= ctxPath%>/js/header/products.js"></script>


</head>
<body>
<%-- start header --%>
 <header class="top-header">
    <div class="top-header__left">
      <button class="nav-btn" type="button" aria-controls="main-nav" aria-expanded="false">
        <span class="sr-only">Menu button</span>
        <img src="images/icon-menu.svg" alt="" role="presentation">
      </button>
      <h1 class="logo">
        <a href="index.html" class="invisible" tabindex="0" >sneakers</a>
      </h1>
        <nav class="main-nav" id="main-nav" aria-label="Main menu">
          <div class="main-nav__content-container">
            <button class="main-nav__close-btn" type="button">
              <span class="sr-only">Close menu</span>
              <span class="icon icon-close" aria-hidden="true"></span>
            </button>
            <ul class="main-nav__links">
              <li>
                <a href="#" class="nav-item">Home</a>
              </li>
              <li>
                <a href="#" class="nav-item">Shop</a>
              </li>
              <li>
                <a href="#" class="nav-item">About us</a>
              </li>
              <li>
                <a href="#" class="nav-item">임시 메뉴</a>
              </li>
              <li>
                <a href="#" class="nav-item">Contact Us</a>
              </li>
            </ul>
          </div>
        </nav>
    </div>
    <%-- cart start --%>
    <div class="top-header__right">
      <button class="top-header__btn-cart" type="button" aria-controls="cart-section" aria-expanded="false">
        <span class="sr-only">Button cart</span>
        <span class="icon icon-cart" aria-hidden="true"></span>
        <span class="items-quantity">
          <span class="value">0</span><span class="sr-only">items</span>
        </span>
      </button>
      <button class="user-container" type="button" aria-label="User section">
        <img src="images/image-avatar.png" alt="" class="user-container__img" role="presentation">
      </button>
    </div>
    <section class="cart-section" id="cart-section" aria-live="polite">
      <h3 class="cart-section__title">Cart</h3>
      <div class="cart-section__body">
        <p class="empty-msg">Your cart is empty.</p>
        <ul class="cart-section__products"></ul>
        <button type="button" class="cart-section__btn-checkout">Checkout</button>
      </div>
    </section>
    <%-- cart end  --%>
  </header>
  <%-- header end --%>