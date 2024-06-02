<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- ==== JSTL (JSP Standard Tag Library) 사용하기 --%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
	String ctxPath = request.getContextPath(); //현재 컨텍스트 패스는 /MyMVC
%>
<jsp:include page="header1.jsp" />
<link rel="stylesheet" href="<%= ctxPath%>/css/reset.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="<%= ctxPath%>/js/index/swiper.js"></script>



<script type="text/javascript">

$(document).ready(function(){
	
	// ======= 추가이미지 캐러젤로 보여주기(Bootstrap Carousel 4개 표시 하되 1번에 1개 진행) 시작 ======= //
   	$('div#recipeCarousel').carousel({
       	interval : 2000  <%-- 2000 밀리초(== 2초) 마다 자동으로 넘어가도록 함(2초마다 캐러젤을 클릭한다는 말이다.) --%>
   	});

   	$('div.carousel div.carousel-item').each(function(index, elmt){
      	<%--
           	console.log($(elmt).html());
      	--%>    
      	<%--      
          	<img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle단가라포인트033.jpg">
          	<img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle덩크043.jpg">
          	<img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle트랜디053.jpg">
          	<img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle디스트리뷰트063.jpg">
      	--%>
      
       	let next = $(elmt).next();      <%--  다음엘리먼트    --%>
   	<%-- console.log(next.length); --%>  <%--  다음엘리먼트개수 --%>
   	<%--  1  1  1  0   --%>
   
   	<%-- console.log("다음엘리먼트 내용 : " + next.html()); --%>
   	<%--     
       	다음엘리먼트 내용 : <img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle덩크043.jpg">
       	다음엘리먼트 내용 : <img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle트랜디053.jpg">
       	다음엘리먼트 내용 : <img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle디스트리뷰트063.jpg">
       	다음엘리먼트 내용 : undefined
   	--%>    
         	if (next.length == 0) { <%-- 다음엘리먼트가 없다라면 --%>
           		<%--           
             	console.log("다음엘리먼트가 없는 엘리먼트 내용 : " + $(elmt).html());
            	--%>  
            	<%-- 
                 	다음엘리먼트가 없는 엘리먼트 내용 : <img class="d-block col-3 img-fluid" src="/MyMVC/images/berkelekle디스트리뷰트063.jpg">
            	--%>
         	
         	//  next = $('div.carousel div.carousel-item').eq(0);
         	//  또는   
         	//  next = $(elmt).siblings(':first'); <%-- 해당엘리먼트의 형제요소중 해당엘리먼트를 제외한 모든 형제엘리먼트중 제일 첫번째 엘리먼트 --%>
         	//  또는 
             	next = $(elmt).siblings().first(); <%-- 해당엘리먼트의 형제요소중 해당엘리먼트를 제외한 모든 형제엘리먼트중 제일 첫번째 엘리먼트 --%>
             	<%-- 
                  	선택자.siblings() 는 선택자의 형제요소(형제태그)중 선택자(자기자신)을 제외한 나머지 모든 형제요소(형제태그)를 가리키는 것이다.
                  	:first   는 선택된 요소 중 첫번째 요소를 가리키는 것이다.
                  	:last   는 선택된 요소 중 마지막 요소를 가리키는 것이다. 
                  	참조사이트 : https://stalker5217.netlify.app/javascript/jquery/
                  
                  	.first()   선택한 요소 중에서 첫 번째 요소를 선택함.
                  	.last()   선택한 요소 중에서 마지막 요소를 선택함.
                  	참조사이트 : https://www.devkuma.com/docs/jquery/%ED%95%84%ED%84%B0%EB%A7%81-%EB%A9%94%EC%86%8C%EB%93%9C-first--last--eq--filter--not--is-/ 
             	--%> 
         	} // end of if (next.length == 0)
         
         	$(elmt).append(next.children(':first-child').clone());
         	<%-- next.children(':first-child') 은 결국엔 img 태그가 되어진다. --%>
         	<%-- 선택자.clone() 은 선택자 엘리먼트를 복사본을 만드는 것이다 --%>
         	<%-- 즉, 다음번 클래스가 carousel-item 인 div 의 자식태그인 img 태그를 복사한 img 태그를 만들어서 
              	$(elmt) 태그속에 있는 기존 img 태그 뒤에 붙여준다. --%>
         
         	for(let i=0; i<2; i++) { // 남은 나머지 4개를 위처럼 동일하게 만든다.
             	next = next.next(); 
             
             	if (next.length == 0) {
             	//   next = $(elmt).siblings(':first');
             	//  또는
                	next = $(elmt).siblings().first();
              	}
             
             	$(elmt).append(next.children(':first-child').clone());
         	}// end of for--------------------------
       
       	console.log(index+" => "+$(elmt).html()); 
     
   	}); // end of $('div.carousel div.carousel-item').each(function(index, elmt)----
   	// ======= 추가이미지 캐러젤로 보여주기(Bootstrap Carousel 4개 표시 하되 1번에 1개 진행) 끝 ======= //		
	
	
});// end of $(document).ready(function()

</script>

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
        <div class="title_btn"><a href="<%= ctxPath%>/item/itemList.flex?brand=G-SHOCK&sort=신상품순&searchWord=">자세히 보기</a></div>
      </figcaption>
    </figure>
    <!-- banner // E -->
    

    <!-- collaboration // S -->
    <section class="product_list_wrap">
      <div class="product_list_title_wrap">
        <div>
          <h2 class="title">평범한 일상, 특별한 스타일🎈</h2>
          <p class="sub_title">타임리스의 다양한 콜라보 제품들을 확인해 보세요.</p>
        </div>
        <div class="title_btn"><a href="<%= ctxPath%>/item/itemList.flex?brand=카시오&sort=신상품순&searchWord=">자세히 보기</a></div>
      </div>
      <!-- Slider main container -->
      <div class="swiper__coverflow">
        <!-- Additional required wrapper -->
        <div class="swiper-wrapper">
          <!-- Slides -->
          <div class="swiper-slide">
            <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=177"><img src="${pageContext.request.contextPath}/images/index/9512_detail_010.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=178"><img src="${pageContext.request.contextPath}/images/index/10819_detail_069.png" alt="product2" /></a>
          </div>
          <div class="swiper-slide">
            <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=176"><img src="${pageContext.request.contextPath}/images/index/9945_detail_089.png" alt="product3" /></a>
          </div>
          <div class="swiper-slide">
            <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=175"><img src="${pageContext.request.contextPath}/images/index/image-product-4-thumbnail.jpg" alt="product4" /></a>
          </div>
          <div class="swiper-slide">
            <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=175"><img src="${pageContext.request.contextPath}/images/index/image-product-1-thumbnail.jpg" alt="product1" /></a>
          </div>
          <div class="swiper-slide">
            <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=175"><img src="${pageContext.request.contextPath}/images/index/image-product-2-thumbnail.jpg" alt="product2" /></a>
          </div>
        </div>
        <!-- scrollbar -->
        <div class="swiper-scrollbar"></div>
      </div>
    </section>
    <!-- collaboration // E -->

    <!-- video 2 // S -->
    <section class="video_wrap">
      <video src="videos/ROLEX_CUT_02.mp4" autoplay muted loop></video>
      <div class="title_wrap">
        <h3 class="title">세계의 운명을 결정짓는 사람들은 롤렉스를 착용합니다.</h3>
        <div class="title_btn"><a href="<%= ctxPath%>/item/itemList.flex?brand=롤렉스&sort=신상품순&searchWord=">자세히 보기</a></div>
      </div>
    </section>
    <!-- video 2 // E -->

    <!-- product slide // S -->
    <section class="product_list_wrap">
      <div class="product_list_title_wrap">
        <div>
          <h2 class="title">평범한 일상, 특별한 스타일🎈</h2>
          <p class="sub_title">타임리스의 다양한 신제품들을 확인해 보세요.</p>
        </div>
        <div class="title_btn"><a href="<%= ctxPath%>/item/itemList.flex?brand=G-SHOCK&sort=신상품순&searchWord=">자세히 보기</a></div>
      </div>
      
      <%-- 슬라이더 잠시 막아둔 것 
      <!-- Slider main container -->
      <div class="swiper__slide">
        <!-- Additional required wrapper -->
        
        <div class="swiper-wrapper" id="dpCatalog">
          <!-- Slides -->
          <!-- 여기에 데이터베이스에서 최신순으로 이미지 들어와야함 -->
        </div>
      
        <!-- pagination -->
        <div class="swiper-slide-pagination"></div>
      </div>
      --%>
    </section>
    <!-- product slide // E -->
    
  <%-- 캐로셀 시도해본 것 --%>  
      <!-- carousel main container -->
      <div class="row mx-auto my-auto" style="width: 100%;">
	      <div id="recipeCarousel" class="carousel slide w-100" data-ride="carousel">
	      	
	      	<div class="carousel-inner w-100" role="listbox" id="dpCatalog">
	      		
	      		<%-- 여기에 데이터베이스에서 최신순으로 이미지 들어와야함 --%>
			<c:forEach var="map" items="${requestScope.Carousel_List}" varStatus="status">
				<c:if test="${status.index == 0}">
					<div class="carousel-item active">
		                <img class="d-block col-3 img-fluid" src="/Semi_Watch/images/product/${map.pdimg1}" onclick="javascript:location.href='/Semi_Watch/item/itemDetail.flex?pdno=${map.pdno}'" style="cursor: pointer;" />  
			        </div>
				</c:if>
				<c:if test="${status.index > 0}">
					<div class="carousel-item">
		                <img class="d-block col-3 img-fluid" src="/Semi_Watch/images/product/${map.pdimg1}" onclick="javascript:location.href='/Semi_Watch/item/itemDetail.flex?pdno=${map.pdno}'" style="cursor: pointer;" />  
			        </div>
				</c:if>
			</c:forEach>
	      	</div>		
	        	<a class="carousel-control-prev" href="#recipeCarousel" role="button" data-slide="prev">
	            	<span class="carousel-control-prev-icon" aria-hidden="true"></span>
	            	<span class="sr-only">Previous</span>
	        	</a>
	        	<a class="carousel-control-next" href="#recipeCarousel" role="button" data-slide="next">
	            	<span class="carousel-control-next-icon" aria-hidden="true"></span>
	            	<span class="sr-only">Next</span>
	        	</a>
	      </div>
      </div>
  
    
    
<jsp:include page="footer.jsp" />
</div> 
</body>
</html>