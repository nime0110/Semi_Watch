<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String ctxPath = request.getContextPath();
%>

<jsp:include page="../header1.jsp" />

<style>

/** Shop: Sorting **/
.shop_sorting {
	list-style: none;
	margin-bottom: 1%;
	border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.shop_sorting>li>a {
	display: block;
	padding: 20px 10px;
	margin-bottom: -1px;
	border-bottom: 2px solid transparent;
	color: black;
	font-weight: bolder;
	font-size: 11pt;
	-webkit-transition: all .05s linear;
	-o-transition: all .05s linear;
	transition: all .05s linear;
}

.shop_sorting>li>a:hover {
	color: #ed3e49;
	text-decoration: none;
}



.shop_sorting {
	display: flex;
	text-align: right;
	border-bottom: 0;
}

.shop_sorting>li {
	display: block;
}

.shop_sorting>li>a {
	padding: 10px 15px;
	margin-bottom: 10px;
	border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.shop_sorting>li>a.active {
	color: #ed3e49;
	font-weight: 600;
}


.button {
	border-radius: 10px;
	border: none;
	font-size: 15px;
	width: 35%;
	height: 30px;
	transition: all 0.5s;
	cursor: pointer;
	margin: 1%;
}

.button span {
	cursor: pointer;
	display: inline-block;
	position: relative;
	transition: 0.5s;
}

.button span:after {
	content: '\00bb';
	position: absolute;
	opacity: 0;
	top: 0;
	right: -2%;
	transition: 0.5s;
}

.button:hover span {
	padding-right: 25%;
}

.button:hover span:after {
	opacity: 1;
	right: 0;
}

/** Shop: Thumbnails **/
.shop_thumb {
	border: 1px solid rgba(0, 0, 0, 0.05);
	padding: 2%;
	margin-bottom: 2%;
	background-color: white;
	text-align: center;
	-webkit-transition: border-color 0.1s, -webkit-box-shadow 0.1s;
	-o-transition: border-color 0.1s, box-shadow 0.1s;
	transition: border-color 0.1s, box-shadow 0.1s;
}

.shop_thumb:hover {
	border-color: rgba(0, 0, 0, 0.07);
	-webkit-box-shadow: 0 5px 30px rgba(0, 0, 0, 0.07);
	box-shadow: 0 5px 30px rgba(0, 0, 0, 0.07);
}

.shop_thumb>a {
	color: #333333;
	text-decoration: none;
}


img[name='itemtimg']{
margin: auto;
  width: 200px;
  height: 200px;
  object-fit: cover;
  
}


.shop-thumb_title {
	color: #00008B;
	font-size: 16pt;
	font-weight: 600;
	text-decoration: none;
}

.shop-thumb_price {
	color: black;
	font-size: 11pt;
	font-weight: bolder;
	text-align: right;
	margin: 3% 10% 3% 0;
	text-decoration: line-through;
}

.shop-thumb_saleprice {
	color: blue;
	font-size: 11pt;
	font-weight: bolder;
	text-align: right;
	margin: 3% 10% 3% 0;
}

.shop-thumb_sale {
	
	color: red;
	font-size: 11pt;
	font-weight: bolder;
	text-align: right;
	margin: 3% 10% 3% 0;
}


.shop-thumb_brand{
	color: green;
	font-size: 14pt;
	font-weight: 600;
	text-decoration: none;
}

 .sidebar {
	position: fixed;
	width: 15%;

}

/* Sidebar links */
.sidebar a {
  display: block;
  color: black;
  padding: 16px;
  text-decoration: none;
  
}

/* Active/current link */
.sidebar a.active {
  background-color: #555;
  color: white;
}

/* Links on mouse-over */
.sidebar a:hover:not(.active) {
  background-color: white;
  color: black;
}
@media screen and (max-width: 700px) {
  .sidebar {
    width: 100%;
    height: auto;
    position: relative;
  }
  .sidebar a {float: left;}
  div.content {margin-left: 0;}
}

/* On screens that are less than 400px, display the bar vertically, instead of horizontally */
@media screen and (max-width: 400px) {
  .sidebar a {
    text-align: center;
    float: none;
  }
}

ul.pagination li {
	font-size: 12pt;
	border: solid 0px gray;

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

</style>

<%-- Font Awesome 6 Icons --%>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">



<script>

$(document).ready(function(){
    // 브랜드 탭을 클릭했을 때 폼태그에 담아서 전송하기
    $("div.sidebar a").click(function(e){
        e.preventDefault();

        $("div.sidebar a").removeClass("active");
        $(e.target).addClass("active");

        const brand = $(e.target).text();
        sessionStorage.setItem('brand', brand);

        $("input:hidden[name='sort']").val("신상품순");
        $("input:hidden[name='brand']").val(brand);

        sessionStorage.removeItem('searchWord');
        
        const frm = document.hiddensend;
        frm.action = "itemList.flex";
        frm.submit();
        
    }); // end of $("div.sidebar a").click(function(e){}
  

    // 정렬 탭을 클릭했을 때 폼태그에 담아서 전송하기
    $("ul.shop_sorting li a").click(function(e){
        e.preventDefault();

        $("ul.shop_sorting li a").removeClass("active");
        $(e.target).addClass("active");

        const sort = $(e.target).text();
        $("input:hidden[name='sort']").val(sort);

        const brand = sessionStorage.getItem('brand');
      
        $("input:hidden[name='brand']").val(brand);
     
        const searchWord = sessionStorage.getItem('searchWord');
        
        $("input:hidden[name='searchWord']").val(searchWord);
      
        const frm = document.hiddensend;
        frm.action = "itemList.flex";
        frm.submit();
        
    }); // end of $("ul.shop_sorting li a").click(function(e){ 정렬탭 클릭시
    	

    // 검색 버튼 클릭과 엔터 키 입력 이벤트 처리
    $("button:button[name='search']").click(gosearch);

    
    $("input:text[class='form-control']").keydown(function (e) {
        if (e.keyCode === 13) {
        	gosearch(e);
        }
    }); // end of $("input:text[class='form-control']").keydown(function (e) {
    	

    function gosearch(e) {
        e.preventDefault();

        const searchWord = $("input:text[class='form-control']").val();
        $("input:hidden[name='searchWord']").val(searchWord);
        sessionStorage.setItem('searchWord', searchWord);

        sessionStorage.removeItem('brand');
        
        const frm = document.hiddensend;
        frm.action = "itemList.flex";
        frm.submit();
        
    } // end of function gosearch
    

        
    
});
		
</script>

<div class="container pt-2">
	<div class="row">
		<div class="col-sm-4 col-md-3">

			<div class="well">
				<div class="row">
					<div class="col-sm-12">
						<div class="input-group">
							<input type="text" name="searchWord" class="form-control"
								placeholder="Search Product..." value="${requestScope.searchWord}">
							<input type="text" style="display: none;" />
							<button name="search" class="btn btn-light" type="button">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Filter -->
			<form class="shop_filter col-sm-8 col-md-9">
			    <div class="sidebar">
			        <h2 class="py-3">브랜드</h2>
			        <c:set var="brand" value="${requestScope.brand}" />
			        
			        <a href="#" class="<c:choose>
			        	<c:when test='${brand == "전체보기"}'>active</c:when>
			        	</c:choose>">전체보기</a>
			        	
			        <a href="#" class="<c:choose>
				        <c:when test='${brand == "G-SHOCK"}'>active</c:when>
				        </c:choose>">G-SHOCK</a>
				        
			        <a href="#" class="<c:choose>
				        <c:when test='${brand == "카시오"}'>active</c:when>
				        </c:choose>">카시오</a>
				        
			        <a href="#" class="<c:choose>
				        <c:when test='${brand == "롤렉스"}'>active</c:when>
				        </c:choose>">롤렉스</a>
				        
			        <a href="#" class="<c:choose>
				        <c:when test='${brand == "세이코"}'>active</c:when>
				        </c:choose>">세이코</a>
			    </div>
			</form>


		</div>

		<div class="col-sm-8 col-md-9">
			<!-- Filters -->
			<form>
			<ul class="shop_sorting d-flex justify-content-end">
			
			<c:set var="sort" value="${requestScope.sort}" />
			
				<li><a id="default" href="#" class="<c:choose>
				        <c:when test='${sort == "신상품순" or sort == "" }'>active</c:when>
				        </c:choose>">신상품순</a></li>
				<li><a href="#" class="<c:choose>
				        <c:when test='${sort == "인기상품순"}'>active</c:when>
				        </c:choose>">인기상품순</a></li>
				<li><a href="#" class="<c:choose>
				        <c:when test='${sort == "높은가격순"}'>active</c:when>
				        </c:choose>">높은가격순</a></li>
				<li><a href="#" class="<c:choose>
				        <c:when test='${sort == "낮은가격순"}'>active</c:when>
				        </c:choose>">낮은가격순</a></li>
			</ul>

			</form>


			<div class="row">
				
				<c:if test="${empty requestScope.productList}">
					<div style="height:500px;">
					<span>상품이 없습니다</span>
					</div>
				</c:if>
				
				<c:if test="${not empty requestScope.productList}"> 
				
				
				<c:forEach var="pvo" items="${requestScope.productList}" varStatus="status">
				
				          <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
				          
				          <fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
	
					<div class="col-sm-6 col-md-4">
						<div class="shop_thumb">
							<div class="position-relative overflow-hidden">
								<div class="shop-thumb_img">
									<a href=""><img class="img-fluid" name="itemtimg" 
										src="<%= ctxPath%>/images/product/${pvo.pdimg1}" alt=""></a>
								</div>
							
							</div>
							<span class="shop-thumb_brand">${pvo.brand}</span> <br>
							<a href="#"> <span class="shop-thumb_title">${pvo.pdname}</span>
							</a>
							<div class="shop-thumb_price">정가 : <fmt:formatNumber value="${pvo.price}" type="number" groupingUsed="true"/>원</div>
							<div class="shop-thumb_saleprice">판매가 : <fmt:formatNumber value="${pvo.saleprice}" type="number" groupingUsed="true"/>원</div>
							<div class="shop-thumb_sale">${pvo.discountPercent}% 할인
								<button type="button" class="btn btn-danger" style="margin-left: 3%;">
									<i class="fa-solid fa-heart"></i>
								</button>
							</div>
							<%--<div>
								 
								<button type="button" class="button btn-Light">
									<span>Buy</span>
								</button>
								<button type="button" class="button btn-dark">
									<span>Cart</span>
								</button> 
								
							</div>--%>
						</div>
					</div>
				
				</c:forEach>
				
				
				
			
				</c:if>

				
			</div>
			<!-- / .row -->

			<!-- Pagination -->

			<div class="row justify-content-center pt-3">
				
				<div id="pageBar">
			       <nav>
			          <ul class="pagination">
			          	<li>${requestScope.pageBar}</li>
			          </ul>
			       </nav>
			    </div>
				
			</div>
			<!-- / .row -->

		</div>
		<!-- / .col-sm-8 -->
	</div>
	<!-- / .row -->
</div>
	<form name="hiddensend">	
		<input type="hidden" name="brand" value="">
    	<input type="hidden" name="sort" value=""/>
		<input type="hidden" name="searchWord" value=""/>
	</form>
			

<jsp:include page="../footer.jsp" />