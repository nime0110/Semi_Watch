<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath(); //현재 컨텍스트 패스는 /MyMVC
%>
<jsp:include page="header1.jsp" />
<link rel="stylesheet" href="<%= ctxPath%>/css/reset.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="<%= ctxPath%>/js/index/swiper.js"></script>

<body>
 
  <div id="content_wrap">
    <!-- banner // S -->
    <figure class="banner_wrap">
      <div class="banner_img_wrap">
        <img src="${pageContext.request.contextPath}/images/index/CASIO_ONETONE_POP_RYUJIN_CLEAN_A7_114214.jpg" alt="메인배너 이미지 1" />
        <img src="${pageContext.request.contextPath}/images/index/CASIO_ONETONE_POP_YEJI_CLEAN_A7_114223.jpg" alt="메인배너 이미지 2" />
      </div>
      <figcaption class="title_wrap">
        <h3 class="title">ITZY✨스타일로 완성하는 여름</h3>
        <p class="sub_title">ITZY 착용 시계와 함께 나만의 스타일로 여름을 즐겨보세요.</p>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </figcaption>
    </figure>
    <!-- banner // E -->
    
    <!-- video 1 // S -->
    <section class="video_wrap">
      <video src="videos/ROLEX_CUT_02.mp4" autoplay muted loop></video>
      <div class="title_wrap">
        <h3 class="title">스타일로 완성하는 여름</h3>
        <p class="sub_title">타임리스 스타일링과 함께 나만의 스타일로 여름을 즐겨보세요.</p>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </div>
    </section>
    <!-- video 1 // E -->

    <!-- collaboration // S -->
    <section class="product_list_wrap">
      <div class="product_list_title_wrap">
        <div>
          <h2 class="title">평범한 일상, 특별한 스타일🎈</h2>
          <p class="sub_title">타임리스의 다양한 콜라보 제품들을 확인해 보세요.</p>
        </div>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </div>
      <!-- Slider main container -->
      <div class="swiper__coverflow">
        <!-- Additional required wrapper -->
        <div class="swiper-wrapper">
          <!-- Slides -->
          <div class="swiper-slide">
            <a href="#"></a><img src="${pageContext.request.contextPath}/images/index/9512_detail_010.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/10819_detail_069.png" alt="product2" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/9945_detail_089.png" alt="product3" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/image-product-4-thumbnail.jpg" alt="product4" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/image-product-1-thumbnail.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/image-product-2-thumbnail.jpg" alt="product2" /></a>
          </div>
        </div>
        <!-- scrollbar -->
        <div class="swiper-scrollbar"></div>
      </div>
    </section>
    <!-- collaboration // E -->

    <!-- video 2 // S -->
    <section class="video_wrap">
      <video src="videos/ROLEX_CUT_01.mp4" autoplay muted loop></video>
      <div class="title_wrap">
        <h3 class="title">스타일로 완성하는 여름</h3>
        <p class="sub_title">타임리스 스타일링과 함께 나만의 스타일로 여름을 즐겨보세요.</p>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </div>
    </section>
    <!-- video 2 // E -->

    <!-- product slide // S -->
    <section class="product_list_wrap">
      <div class="product_list_title_wrap">
        <div>
          <h2 class="title">평범한 일상, 특별한 스타일🎈</h2>
          <p class="sub_title">타임리스의 다양한 콜라보 제품들을 확인해 보세요.</p>
        </div>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </div>
      <!-- Slider main container -->
      <div class="swiper__slide">
        <!-- Additional required wrapper -->
        <div class="swiper-wrapper">
          <!-- Slides -->
          <div class="swiper-slide">
            <a href="#"></a><img src="${pageContext.request.contextPath}/images/index/9512_detail_010.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/10819_detail_069.png" alt="product2" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/9945_detail_089.png" alt="product3" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/image-product-4-thumbnail.jpg" alt="product4" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/image-product-1-thumbnail.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="${pageContext.request.contextPath}/images/index/image-product-2-thumbnail.jpg" alt="product2" /></a>
          </div>
        </div>
        <!-- pagination -->
        <div class="swiper-slide-pagination"></div>
      </div>
    </section>
    <!-- product slide // E -->
<jsp:include page="footer.jsp" />
</div> 
</body>
</html>