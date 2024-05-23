<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String ctx_Path = request.getContextPath();
%>

<link rel="stylesheet" href="../font/css/all.css">
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/itemDetail/itemDetail.css
" />
<%-- 허성심 제작 페이지 --%>
<jsp:include page="../header1.jsp" />
<script type="text/javascript">
/*  아래 코드로 사진 변경 가능 */
  $(document).ready(function() {
	  //색상 변경
	  $('ul#choice li button img').on('click',function(){
	    var i = $(this).attr('src');
		console.log(i);
	    $('.image-box img').attr('src',i);
	    return false;
	  })
	 
		//첫번째 content로 애니메이트
		$('html,body').stop().animate({scrollTop: 0},2000);
		//메뉴 클릭하면 해당 위치 찾아가기
		$('.categori ul li a').on('click',function(){
		  //-첫째로 몇번째인지 알아야됨
		  var n = $(this).parent().index();
		  //해당 위치 찾아가기
		  var target =  $('.categori').eq(n).offset().top;
		  $('html,body').stop().animate({scrollTop: target},2000);
		  return false;
		})
		 $('.minus-item').click(function() {
	        var quantityInput = $('#product__quantity');
	        var currentValue = parseInt(quantityInput.val());
	        if (currentValue > 0) {
	          quantityInput.val(currentValue - 1);
	        }
	      });

	      $('.plus-item').click(function() {
	        var quantityInput = $('#product__quantity');
	        var currentValue = parseInt(quantityInput.val());
	        quantityInput.val(currentValue + 1);
	      });


	});

</script>

  <main>
    <article class="product">
      <section class="product__slider default-container" aria-label="Product preview">
        <button type="button" class="product__slider___btn-close-lightbox">
          <span class="sr-only" >Close lightbox</span>
          <span class="icon icon-close" aria-hidden="true"></span>
        </button>  
        <div class="image-box" aria-label="Product preview" role="region">
          <button type="button" class="btn-previousImage">
            <span class="sr-only">Previous Image</span>
            <span class="icon icon-previous" aria-hidden="true"></span>
          </button>
          <button type="button" class="btn-nextImage">
            <span class="sr-only">Next Image</span>
            <span class="icon icon-next" aria-hidden="true"></span>
          </button>
          <%-- <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-1.jpg" alt="Brown and white sneaker" class="image-box__src" data-product-id="item-cart-1" tabindex="0" aria-controls="lightbox" aria-expanded="false"> --%>
          <c:if test="${not empty requestScope.mainimgvo}">
          		<img src="${pageContext.request.contextPath}/images/itemDetail/${mainimgvo.imgfilename}" alt="Brown and white sneaker" id="productImage" class="image-box__src" data-product-id="item-cart-1" tabindex="0" aria-controls="lightbox" aria-expanded="false">
        		<!--  /Semi_Watch/images/itemDetail/56_thum.png -->
           </c:if>
        </div>
		        <ul class="product__thumbs default-container" id="choice" aria-label="Product thumbnails">
		          <li class="thumb-item">
		            <button type="button" class="thumb-item__btn" aria-label="change to image 1">
		               <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-1-thumbnail.jpg" alt="" data-thumb-index="0" role="presentation"> 
		              <%-- <img src="<%= ctx_Path %>/images/${imgvo.imgfilename}" alt="" data-thumb-index="0" role="presentation">  --%>
		            	<!-- /Semi_Watch/images/3_extra_1.png -->
		            </button>
		          </li>
		          <li class="thumb-item">
		            <button type="button" class="thumb-item__btn" aria-label="change to image 2">
		              <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-2-thumbnail.jpg" alt="" data-thumb-index="1" role="presentation"> 
		            <%-- <img src="<%= ctx_Path %>/images/${imgvo.imgfilename}" alt="" data-thumb-index="1" role="presentation"> --%>
		            </button>
		          </li>
		          <li class="thumb-item">
		            <button type="button" class="thumb-item__btn" aria-label="change to image 3">
		              <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-3-thumbnail.jpg" alt="" data-thumb-index="2" role="presentation">
		            <%-- <img src="<%= ctx_Path %>/images/${imgvo.imgfilename}" alt="" data-thumb-index="2" role="presentation"> --%>
		            </button>
		          </li>
		          <li class="thumb-item">
		            <button type="button" class="thumb-item__btn" aria-label="change to image 4">
		              <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-4-thumbnail.jpg" alt="" data-thumb-index="3" role="presentation">
		              <%-- <img src="<%= ctx_Path %>/images/${imgvo.imgfilename}" alt="" data-thumb-index="3" role="presentation"> --%>
		            </button>
		          </li>
		     
		        </ul>
<%--         <c:if test="${not empty requestScope.imgList}">
		    <ul class="product__thumbs default-container" id="choice" aria-label="Product thumbnails">
		        <c:forEach var="imgvo" items="${requestScope.imgList}" varStatus="status">
		            <li class="thumb-item">
		                <button type="button" class="thumb-item__btn" aria-label="change to image ${status.index + 1}">
		                    <img src="<%= ctx_Path %>/images/${imgvo.imgfilename}" alt="" data-thumb-index="${status.index}" role="presentation">
		                </button>
		            </li>
		        </c:forEach>
		    </ul>
		</c:if> --%>
      </section>
      <section class="product__content default-container" aria-label="Product content">
        <header>
          <h2 class="company-name" tabindex="0">CASIO</h2>
          <p class="product__name" tabindex="0"></p>
          <h3 class="product__title" tabindex="0" id="productName">DAYTONA</h3>
        </header>
        <p class="product__description" tabindex="0">
          터프니스를 추구하며 진화를 계속하는 G-SHOCK에서, 세부적인 파트까지 모두 원톤으로 완성한 One tone 시리즈입니다. 베이스 모델은 옥타곤 베젤의 GA-2100을 채용하고, 인기 있는 컬러링을 케이스, 베젤, 밴드, 다이얼의 각 파트에 이르기까지 원톤으로 통일하여 전체적으로 깊이감이 있는 모델로 완성했습니다. 스트릿 패션에도 최적화된 캐주얼하고 스타일링의 포인트로 활용하기 좋은 컬러 모델입니다.
        </p>
        <div class="product__price">
          <div class="discount-price">
            <span>￦</span>
            <p class="discount-price__value" tabindex="0">
              260,000 
              <span class="sr-only"></span>
            </p>
            <p class="discount-price__discount" tabindex="0">50%</p>
          </div>
          <div class="full-price">
            <p tabindex="0">
              135,000 
              <span class="sr-only"></span>
            </p>
          </div>
        </div>
        
        <form action="#" class="cart-form">
          <div class="cart-form__input-container" aria-label="Define the product quantity">
            <button type="button" class="btn-changeValue minus-item">
              <span class="sr-only">Minus one item</span>
              <span class="icon icon-minus" aria-hidden="true"></span>
            </button>
            <label for="product__quantity" class="sr-only">Set the quantity manually</label>
            <input type="number" min="0" value="0" id="product__quantity">
            <button type="button" class="btn-changeValue plus-item">
              <span class="sr-only">Plus one item</span>
              <span class="icon icon-plus" aria-hidden="true"></span>
            </button>
          </div>
          <button type="button" class="cart-form__add-btn">
            <span class="icon" aria-hidden="true"></span>
            Add to cart
          </button>
          <button type="button" class="cart-form__add-btn" id="wish_list">
            <span class="icon icon-cart" aria-hidden="true"></span>
          </button>
        </form>
      </section>
    </article>
  </main>
  
  <div class="lightbox" id="lightbox" role="dialog"></div>
   <!------------------------------------------------------------------ -->
  
   <!-- minibanner  -->
  <div id="minibanner" class="container">
  	<img src="${pageContext.request.contextPath}/images/itemDetail/mini_banner.jpg" alt=""></a>
  </div>
   <div id="content" class="container">
   <div id="itemcate" class="categori">
     <ul>
       <li><a href="#iteminfo">상품상세정보</a></li>
       <li><a href="#finalbox">배송/교환/반품</a></li>
       <li><a href="#bestReview">리뷰 &#40;20&#41;</a></li>
     </ul>
   </div>
   
   <div id="maininfo">
     <h2>GA-2100-2A2DR</h2>

     <div class="mainImg">
       <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-2.jpg" alt="상세이미지1" />
       <img src="${pageContext.request.contextPath}/images/itemDetail/image-product-3.jpg" alt="상세이미지2" />
     </div>
   </div>
 </div>
  <div id="finalbox" class="container">
    <div id="finalcate" class="categori">
      <ul>
        <li><a href="#iteminfo">상품상세정보</a></li>
        <li><a href="#finalbox">배송/교환/반품</a></li>
        <li><a href="#normalReview">리뷰 &#40;20&#41;</a></li>
      </ul>
    </div>
    <div id="infobox">
      <div id="info_leftbox">
        <div id="infobox1">
          <h2>배송 관련 안내</h2>
          <ol>
            <li>배송은 당일 평일 4시, 토요일 1시까지 입금확인된 물품에 대해 당일 배송을 원칙으로 합니다.</li>
            <li>저희 쇼핑몰은 CJ택배를 사용합니다.</li>
            <li>구매가 5만원 이상 제품에 대해 무료배송을 원칙으로 하고 있습니다.</li>
            <li>택배비 별도제품의 경우 택배비는 2,500원입니다.</li>
          </ol>
        </div>
        <div id="infobox2">
          <h2>반품&#47;교환</h2>
          <ol>
            <li>반품과 환불이 가능합니다.</li>
            <li>제품의 하자에 의한 반품 및 교환, 환불은 100&#37;이루어지며 <br>
            왕복 배송비를 부담합니다.</li>
            <li>반품 및 교환이 불가한 경우는 다음과 같습니다.<br>
            -제품 착용 및 사용 흔적이 있거나 밴드를 조절한 경우<br>
            -제품을 임의로 분리 및 분해한 흔적이 있는 경우<br>
            -제품 사용 후 변색 또는 이염된 경우 </li>
            <li>반품 및 교환이 가능하나 왕복택배비를 부담하셔야 하는 경우(구입 후 7일 이내)<br>
            -소비자의 단순변심에 의한 환불 및 교환</li>
          </ol>
          </div>
          <div id="infobox3">
            <h2>A&#47;S</h2>
            <ol>
              <li>손님의 부주의에 의한 파손은 소정의 수리비를 받고 수리가 가능합니다.</li>
              <li>수리하실 경우, 오프라인 매장에 방문하여 수리를 맡기시거나 택배로 접수 가능합니다.<br>
                수리 관련 택배비는 고객님 부담입니다.</li>
            </ol>
          </div>
        </div>
	</div>
</div>
  <div id="review" class="container">
<!-- 리뷰란 -->
    <div id="reviewcate" class="categori">
      <ul>
        <li><a href="#iteminfo">상품상세정보</a></li>
        <li><a href="#finalbox">배송/교환/반품</a></li>
        <li><a href="#bestReview">리뷰 &#40;20&#41;</a></li>
      </ul>
    </div>

    <div id="normalReview">
      <div id="reviews_">
        <ul id="reviewsel">
          <li><a href="#">베스트리뷰 |</a></li>
          <li><a href="#">작성일자순 |</a></li>
          <li><a href="#">최신순 </a></li>
        </ul>
        <div id="reviewBoard">
          <table>
            <tr>
              <td>
                <p>솔직 구매후기 남깁니다~ 처음 구매해봤어요</p>
                <a href="#">
                노래 한곡 한곡 따뜻한 주제가 돋보이는 아름다운 한글가사의 노래를 모아봤어요. 바삐 돌아가는 하루에 빠른비트의 음악들도 좋지만. 가끔은 그 옛날 연필로 가사 ...
                더미데이터입니다. 더미데이터입니다.더미데이터입니다.더미데이터입니다.더미데이터입니다.더미데이터입니다.더미데이터입니다.더미데이터입니다.더미데이터입니다.더미데이터입니다.
                
                </a>
              </td>
              <td>
                <p>
                  <span>작성일자</span>
                  2024.05.15
                </p>
                <p>
                  <span>작성자</span>
                  nva_1**
                </p>
              </td>
            </tr>
            <tr>
              <td>
                <p>솔직 구매후기 남깁니다~ 처음 구매해봤어요</p>
                <a href="#">...더보기</a>
              </td>
              <td>
                <p>
                  <span>작성일자</span>
                  2021.02.24
                </p>
                <p>
                  <span>작성자</span>
                  nva_1**
                </p>
              </td>
            </tr>
            <tr>
              <td>
                <p>솔직 구매후기 남깁니다~ 처음 구매해봤어요</p>
                <a href="#">...더보기</a>
              </td>
              <td>
                <p>
                  <span>작성일자</span>
                  2021.02.24
                </p>
                <p>
                  <span>작성자</span>
                  nva_1**
                </p>
              </td>
            </tr>
          </table>
        </div>
        <div id="writeReview">
          <a href="#">리뷰쓰기</a>
        </div>
        <div id="rpageNumber">
          <a href="#">
            <img src="${pageContext.request.contextPath}/images/itemDetail/12345allowleft.png" 
            alt="왼쪽 화살표">
          </a>
          <span>
            <a href="#">1</a>
            <a href="#">2</a>
            <a href="#">3</a>
            <a href="#">4</a>
            <a href="#">5</a>
            <a href="#">6</a>
            <a href="#">7</a>
            <a href="#">8</a>
            <a href="#">9</a>
            <a href="#">10</a>
          </span>
          <a href="#">
            <img src="${pageContext.request.contextPath}/images/itemDetail/12345allowright.png" 
            alt="오른쪽 화살표">
          </a>
        </div>
      </div>
    </div>
  </div>
