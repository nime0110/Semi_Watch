<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String ctx_Path = request.getContextPath();
%>

<link rel="stylesheet" href="../font/css/all.css">

<jsp:include page="../header1.jsp" />
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
<%-- 허성심 제작 페이지 --%>
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/css/itemDetail/itemDetail.css" />


<script type="text/javascript" src="<%= ctx_Path%>/js/item/itemDetail.js"></script>
<script type="text/javascript">
	

</script>

  <main>
    <article class="product">
  <!-- 라이트박스용, 아직 미작성 -->
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
          <c:if test="${not empty requestScope.pvo}">
          		<img src="${pageContext.request.contextPath}/images/product/${pvo.pdimg1}" alt="${pvo.pdname}" id="productImage" class="image-box__src" data-product-id="item-cart-1" tabindex="0" aria-controls="lightbox" aria-expanded="false">
        		<!--  /Semi_Watch/images/itemDetail/56_thum.png -->
           </c:if>
        </div>
	
		<c:if test="${not empty requestScope.imgList}">
		    <ul class="product__thumbs default-container" id="choice" aria-label="Product thumbnails">
		        <c:forEach var="imgfilename" items="${requestScope.imgList}" varStatus="status">
		            <li class="thumb-item">
		                <button type="button" class="thumb-item__btn" aria-label="change to image ${status.index + 1}">
		                    <img src="${pageContext.request.contextPath}/images/product/${imgfilename}" alt="" data-thumb-index="${status.index}" role="presentation">
		                </button>
		            </li>
		        </c:forEach>
		    </ul>
		</c:if> 
      </section>
          <!-- 라이트박스용 -->
      <section class="product__content default-container" aria-label="Product content">
        <header>
    	  <input type="hidden" id="productno" name="pdno" value="${pvo.pdno}">
          <input type="hidden" id="productpoint" name="point" value="${pvo.point}"> 
          <h2 class="company-name" tabindex="0">${pvo.brand}</h2>
          <p class="product__name" tabindex="0"></p>
          <h3 class="product__title" tabindex="0" id="productName">${pvo.pdname}</h3>
        </header>
        <p class="product__description" tabindex="0">${pvo.pd_content}</p>
        <div class="product__price">
          <div class="discount-price">
            <span>￦</span>
            <p class="discount-price__value" tabindex="0">
              <fmt:formatNumber value="${pvo.saleprice}" type="number" pattern="#,##0" />
              <span class="sr-only"></span>
            </p>
            <p class="discount-price__discount" tabindex="0">${pvo.discountPercent}%</p>
          </div>
          <div class="full-price" style="margin-bottom:30px;">
            <p tabindex="0">
             <fmt:formatNumber value="${pvo.price}" type="number" pattern="#,##0" />
              <span class="sr-only"></span>
            </p>
          </div>
        </div>  
		  <!-- 색상선택  -->
		<c:if test="${not empty requestScope.colorList}">
		    <c:set var="hasValidColor" value="false" />
		    <c:forEach var="colorname" items="${requestScope.colorList}">
		        <c:if test="${colorname != 'none'}">
		            <c:set var="hasValidColor" value="true" />
		        </c:if>
		    </c:forEach>
		    <c:if test="${hasValidColor}">
		        <span style="margin-top:30px;">컬러 선택하기</span>
		        <select id="color_select" style="margin-top:10px;">
		            <option value="" disabled selected>선택하세요</option>
		            <c:forEach var="colorname" items="${requestScope.colorList}">
		                <c:if test="${colorname != 'none'}">
		                    <option value="${colorname}">
		                        ${colorname}
		                    </option>
		                </c:if>
		            </c:forEach>
		        </select>
		    </c:if>
		</c:if>
		<!-- 색상선택끝 -->
        <form action="#" class="cart-form" style="margin-top:30px;">
         <input type="hidden" id="productno" name="pdno" value="${pvo.pdno}">
          <input type="hidden" id="productpoint" name="point" value="${pvo.point}">
          <div class="cart-form__input-container" aria-label="Define the product quantity">
            <button type="button" class="btn-changeValue minus-item">
              <span class="sr-only">Minus one item</span>
              <span class="icon icon-minus" aria-hidden="true"></span>
            </button>
            <label for="product__quantity" class="sr-only">Set the quantity manually</label>
            <input type="number" min="1" value="1" id="product__quantity">
            <button type="button" class="btn-changeValue plus-item">
              <span class="sr-only">Plus one item</span>
              <span class="icon icon-plus" aria-hidden="true"></span>
            </button>
          </div>
          <button type="button" class="cart-form__add-btn" id="addCart">
            Add to cart
          </button>
          <button type="button" class="cart-form__add-btn" id="buy">
            구매하기
          </button>
          <button type="button" class="cart-form__add-btn" id="wish_list">
            <span class="icon icon-cart" aria-hidden="true"></span>
          </button>
        </form>
      </section>
    </article>
    <!-------------------------- 구매하기 폼 보내기 ------------------------------->
	<form name="buyFrm" >
    	  <input type="hidden" id="productno" name="str_pdno" value="${pvo.pdno}">
          <input type="hidden" id="str_cart_qty" name="str_cart_qty" value="">
          <input type="hidden" id="productpoint" name="productpoint" value="">
          <input type="hidden" id="str_pd_detailno" name="str_pd_detailno" value="">
          <input type="hidden" id="str_pdPriceArr" name="str_pdPriceArr" value="">
          <input type="hidden" id="str_pdPointArr" name="str_pdPointArr" value="">
	</form>
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
     <h2>${pvo.pdname}</h2>

     <div class="mainImg">
       <img src="${pageContext.request.contextPath}/images/product/${pvo.pd_contentimg}" alt="상세이미지1" style="width:90em;" />
     </div>
   </div>
 </div>
  <div id="finalbox" class="container">
    <div id="finalcate" class="categori">
      <ul>
        <li><a href="#iteminfo">상품상세정보</a></li>
        <li><a href="#finalbox">배송/교환/반품</a></li>
        <li><a href="#normalReview">리뷰 &#40;${rvMapList[0].reviewcount}&#41;</a></li>
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
    <div id="reviewcate" class="categori">
      <ul>
        <li><a href="#iteminfo">상품상세정보</a></li>
        <li><a href="#finalbox">배송/교환/반품</a></li>
        <li><a href="#bestReview">리뷰 &#40;${rvMapList[0].reviewcount}&#41;</a></li>
      </ul>
    </div>

<!-- 리뷰란 -->
    <div id="normalReview">
      <div id="reviews_">
      	<c:if test="${not empty requestScope.rvMapList}">
	      	<div id="rateAndCount">
	      		<div id="rateView">
	      			<span></span>
					<div id="avgRateYo" data-rateyo-read-only="true"></div>
					<h4>평균별점 <span>${rvMapList[0].avg_starpoint}</span></h4>
	      		</div>
				<h4>전체 리뷰수 <span>${rvMapList[0].reviewcount}</span></h4>
			</div>
		</c:if>
		<c:if test="${empty requestScope.rvMapList}">
			<div id="rateAndCount">
	      		<div id="rateView">
					<div id="rateYozero" data-rateyo-read-only="true"></div>
					<h4>평균별점 <span>0</span></h4>
	      		</div>
				<h4>전체 리뷰수 <span>0</span></h4>
			</div>
		</c:if>
        <ul id="reviewsel">
          <li><a href="#">베스트리뷰 |</a></li>
          <li><a href="#">오래된순 |</a></li>
          <li><a href="#">최신순 </a></li>
        </ul>
        <div id="reviewBoard">
          <table>
	          <c:if test="${not empty requestScope.rvMapList}">
		       <c:forEach var="rvMap" items="${requestScope.rvMapList}">
			       <tr>
		              <td>
			            <div id="ratingDiv">
			              <div class="rateYoOne" data-rating="${rvMap.starpoint}"></div>
						  <p class="ratingCount">별점 <span class="ratingOneSpan">${rvMap.starpoint}</span>점</p>
			            </div>
		                <p>${rvMap.review_content}</p>
		              </td>
		              <td>
		                <p>
		                  <span>작성일자</span>
		                  ${rvMap.review_date}
		                </p>
		                <p>
		                  <span>작성자</span>
		                  ${rvMap.fk_usermask}
		                </p>
		              </td>
		            </tr> 
	            </c:forEach>
	            </c:if>
	            <c:if test="${empty requestScope.rvMapList}">
		            <div id="notReviewDiv">
			            <p id="notReviewP"> 아직 상품 리뷰가 작성되지 않은 상품입니다. <br>
			            	첫 상품 리뷰 작성자가 되어보세요!</p>
		            </div>
	            </c:if>
          </table>
        </div>
        <div id="writeReview">
          <a href="#">리뷰쓰기</a>
        </div>
        <!-- review popup start -->
        <!--
        <div id="reviewPopup" class="popup" style="display:none;">
	    <div class="popup-content">
	        <span class="close">&times;</span>
	        <h2>리뷰 작성하기</h2>
	        <div id="rateYo" ></div>
			<p>Selected Rating: <span id="ratingValue">0</span></p>
	        <textarea id="reviewText" rows="4" cols="50" placeholder="리뷰는 20자 이상 작성 가능합니다."></textarea>
	        <br>
	        <button id="submitReviewBtn">리뷰 제출하기</button>
	    </div>
		</div> 
        <!-- review popup end -->
        <ul id="rpageNumber" class="pagination">
        <%-- 	<li>	        	
	          <a href="#">
	            <img src="${pageContext.request.contextPath}/images/itemDetail/12345allowleft.png" 
	            alt="왼쪽 화살표">
	          </a>
        	</li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">6</a></li>
            <li><a href="#">7</a></li>
            <li><a href="#">8</a></li>
            <li><a href="#">9</a></li>
            <li><a href="#">10</a></li>
            <li>	
	          <a href="#">
	            <img src="${pageContext.request.contextPath}/images/itemDetail/12345allowright.png" 
	            alt="오른쪽 화살표">
	          </a>
           </li> --%>
           	
			${requestScope.pageBar}
			
        </ul>
      </div>
    </div>
  </div>
<jsp:include page="../footer.jsp" />