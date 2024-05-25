<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath(); //현재 컨텍스트 패스는 /MyMVC
%>
<jsp:include page="header1.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/font/css/all.css" />
<body>
  <div id="content_wrap">
    <!-- banner // S -->
    <figure class="banner_wrap">
      <div class="banner_img_wrap">
        <img src="main_images/CASIO_ONETONE_POP_RYUJIN_CLEAN_A7_114214.jpg" alt="메인배너 이미지 1" />
        <img src="main_images/CASIO_ONETONE_POP_YEJI_CLEAN_A7_114223.jpg" alt="메인배너 이미지 2" />
      </div>
      <figcaption class="title_wrap">
        <h3 class="title">스타일로 완성하는 여름</h3>
        <p class="sub_title">타임리스 스타일링과 함께 나만의 스타일로 여름을 즐겨보세요.</p>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </figcaption>
    </figure>
    <!-- banner // E -->

    <!-- video 1 // S -->
    <section class="video_wrap">
      <video src="videos/ROLEX_CUT_01.mp4" autoplay muted loop></video>
      <div class="title_wrap">
        <h3 class="title">스타일로 완성하는 여름</h3>
        <p class="sub_title">타임리스 스타일링과 함께 나만의 스타일로 여름을 즐겨보세요.</p>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </div>
    </section>
    <!-- video 1 // E -->

    <!-- video 2 // S -->
    <section class="video_wrap">
      <video src="videos/ROLEX_CUT_02.mp4" autoplay muted loop></video>
      <div class="title_wrap">
        <h3 class="title">스타일로 완성하는 여름</h3>
        <p class="sub_title">타임리스 스타일링과 함께 나만의 스타일로 여름을 즐겨보세요.</p>
        <div class="title_btn"><a href="#">자세히 보기</a></div>
      </div>
    </section>
    <!-- video 2 // E -->

    <!-- collaboration // S -->
    <section class="product_list_wrap">
      <h2 class="product_list_title">스타일로 완성하는 여름</h2>
      <div class="title_btn"><a href="#">자세히 보기</a></div>
      <!-- Slider main container -->
      <div class="swiper">
        <!-- Additional required wrapper -->
        <div class="swiper-wrapper">
          <!-- Slides -->
          <div class="swiper-slide">
            <a href="#"></a><img src="main_images/9512_detail_010.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="main_images/10819_detail_069.png" alt="product2" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="main_images/9945_detail_089.png" alt="product3" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="images/image-product-4-thumbnail.jpg" alt="product4" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="images/image-product-1-thumbnail.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="#"><img src="images/image-product-2-thumbnail.jpg" alt="product2" /></a>
          </div>
        </div>
        <!-- If we need pagination -->
        <div class="swiper-pagination"></div>

        <!-- If we need navigation buttons -->
        <!-- <div class="swiper-button-prev"></div>
        <div class="swiper-button-next"></div> -->

        <!-- If we need scrollbar -->
        <!-- <div class="swiper-scrollbar"></div> -->
      </div>
    </section>
    <!-- collaboration // E -->

    <!-- footer // S -->
    <footer>
      <div>
        <!-- 프로젝트 팀명 -->
        <div class="team_name">Project Team 2.</div>
        <ul class="team_members">
          <li><a href="#">Heo Seongsim</a></li>
          <li><a href="#">member 1</a></li>
          <li><a href="#">member 2</a></li>
          <li><a href="#">member 3</a></li>
          <li><a href="#">member 4</a></li>
        </ul>
      </div>
      <div class="footer_info">
        <div class="project_name">Timeless</div>
        <div class="sns_box">
          <a href="#"><i class="fa-brands fa-github"></i></a>
          <a href="#"><i class="fa-brands fa-github"></i></a>
          <a href="#"><i class="fa-brands fa-github"></i></a>
        </div>
        <div class="copyright">© Project Team 2 Corp.</div>
      </div>
    </footer>
    <!-- footer // E -->
  </div>
</body>
</html>
<jsp:include page="footer.jsp" />