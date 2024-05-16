<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String ctxPath = request.getContextPath();
%>

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

.shop_sorting>li>a:hover, .shop_sorting>li>a:focus {
	color: #ed3e49;
	text-decoration: none;
}

.shop_sorting>li.active>a {
	color: #ed3e49;
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

.shop_sorting>li.active>a {
	font-weight: 600;
}

/* Checkboxes */
/*
    .shop__filter {
        padding: 20px;
    }
.checkbox input[type="checkbox"] {
  display: none;
}
.checkbox label {
  padding-left: 0;
}
.checkbox label:before {
  content: "";
  display: inline-block;
  vertical-align: middle;
  margin-right: 15px;
  width: 20px;
  height: 20px;
  line-height: 20px;
  background-color: #eee;
  text-align: center;
  font-family: "FontAwesome";
}

.checkbox input[type="checkbox"]:checked + label::before {
  content: "\f00c";
}



#floatingDiv {
	border-radius: 3%;
	  background-color: #F0F8FF;
    position: fixed;
	padding: 1% 0 0 1%;
    width: 15%;

  }
*/
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

.shop-thumb_img {
	position: relative;
	margin-bottom: 2%;
	overflow: hidden;
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
}

#top-button {
	border-radius: 10%;
	background-color: light;
	position: fixed;
	margin: 1% 0 0 12%;
	width: 4.5%;
	padding-left: 0.8%;
}

ul.pagination li {
	font-size: 13pt;
	font-weight: bold;
}

.dp-space.show {
	height: 18%; /* 필요에 따라 높이를 조정 */
}

.dp-space {
	margin-bottom: 20%; /* 드롭다운 버튼들 사이의 간격 추가 */
	height: 0;
	transition: height 0.3s ease;
}

.dp-space button {
	width: 70%;
}
</style>
<script>
	$(document).ready(function() {

		$("ul.shop_sorting li:first-child").addClass("active");

		$(".pagination li").on("click", function() {
			// 다른 li 요소에서 active 클래스를 제거합니다.
			$(".pagination li.active").removeClass("active");

			// 클릭된 li 요소에 active 클래스를 추가합니다.
			$(this).addClass("active");
		});

		$('#dropdown').on('click', function() {
			$('.dp-space').toggleClass('show');
		});

	});
</script>
<%-- Font Awesome 6 Icons --%>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<jsp:include page="../header1.jsp" />

<div class="container">
	<div class="row">
		<div class="col-sm-4 col-md-3">


			<form>
				<div class="well">
					<div class="row">
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" class="form-control"
									placeholder="Search products...">
								<button class="btn btn-success" type="button">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>





			<!-- Filter -->
			<form class="shop_filter">

				<!-- Checkboxes -->
				<div class="text-left">
					<div class="dp-space">
						<button class="btn btn-light dropdown-toggle btn-gradient"
							type="button" id="dropdown" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">브랜드</button>
						<div class="dropdown-menu" aria-labelledby="dropdown1">
							<a class="dropdown-item" href="#">전체보기</a> <a
								class="dropdown-item" href="#">카시오</a> <a class="dropdown-item"
								href="#">G-Shock</a> <a class="dropdown-item" href="#">까르띠에</a>
							<a class="dropdown-item" href="#">세이코</a> <a
								class="dropdown-item" href="#">롤렉스</a>
						</div>
					</div>

					<div class="dp-space">
						<button class="btn btn-light dropdown-toggle" type="button"
							id="dropdown" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">카테고리</button>
						<div class="dropdown-menu" aria-labelledby="dropdown2">
							<a class="dropdown-item" href="#">전체 보기</a> <a
								class="dropdown-item" href="#">남성 시계</a> <a
								class="dropdown-item" href="#">여성 시계</a> <a
								class="dropdown-item" href="#">ACC</a>
						</div>
					</div>
				</div>


				<%-- 
		<div id="floatingDiv" class="pt-7">
        <h3 class="headline pb-2">
          <span>Brand</span>
        </h3>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_1" checked="">
          <label for="shop-filter-checkbox_1">G-Shock</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_2">
          <label for="shop-filter-checkbox_2"></label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_3">
          <label for="shop-filter-checkbox_3">Columbia</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_4">
          <label for="shop-filter-checkbox_4">Tommy Hilfiger</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_all">
          <label for="shop-filter-checkbox_all">Not specified</label>
        </div>
        <%-- 
        <div class="dropdown">
			<!-- 버튼태그에서 아래로 보여주는걸 의미함 -->
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Dropdown button
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" href="#">Adidas</a>
		    <a class="dropdown-item active" href="#">Calvin Klein</a>
		    <a class="dropdown-item" href="#">Columbia</a>
		    <a class="dropdown-item" href="#">Tommy Hilfiger</a>
		    <a class="dropdown-item" href="#">Not specified</a>
		  </div>
		</div>
		--%>

				<%-- 
        <h3 class="pt-3 pb-2 headline">
          <span>Material</span>
        </h3>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_1" value="" checked="">
          <label for="shop-filter-radio_1">100% Cotton</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_2" value="">
          <label for="shop-filter-radio_2">Bamboo</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_3" value="">
          <label for="shop-filter-radio_3">Leather</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_4" value="">
          <label for="shop-filter-radio_4">Polyester</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_5" value="">
          <label for="shop-filter-radio_5">Not specified</label>
        </div>
        
        <%-- 
        <div class="dropdown">
			<!-- 버튼태그에서 아래로 보여주는걸 의미함 -->
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Material
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" href="#">100% Cotton</a>
		    <a class="dropdown-item active" href="#">Bamboo</a>
		    <a class="dropdown-item" href="#">Leather</a>
		    <a class="dropdown-item" href="#">Polyester</a>
		    <a class="dropdown-item" href="#">Not specified</a>
		  </div>
		</div>
		--%>

				<%-- 	<div class="float-right" id="top-button"><a style="text-decoration: none; color:black;" href="#">Top&nbsp;▲</a></div>
		</div>
		--%>

				<div class="float-right" id="top-button">
					<a style="text-decoration: none; color: black;" href="#">Top&nbsp;▲</a>
				</div>


			</form>


		</div>

		<div class="col-sm-8 col-md-9">
			<!-- Filters -->
			<ul class="shop_sorting d-flex justify-content-end">
				<li><a href="#">신상품순</a></li>
				<li><a href="#">인기상품순</a></li>
				<li><a href="#">높은가격순</a></li>
				<li><a href="#">낮은가격순</a></li>
			</ul>



			<div class="row">

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>
							<div
								class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>
							<div
								class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>



				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

				<div class="col-sm-6 col-md-4">
					<div class="shop_thumb">
						<div class="position-relative overflow-hidden">
							<div class="shop-thumb__img">
								<a href=""><img class="img-fluid"
									src="<%=ctxPath%>/images/product/watchTest.png" alt=""></a>
							</div>

						</div>
						<a href="#"> <span class="shop-thumb_title"> 티쏘 젠틀맨
								실리시움 </span>
						</a>
						<div class="shop-thumb_price">1,140,000원</div>
						<div>
							<button type="button" class="button btn-Light">
								<span>Buy</span>
							</button>
							<button type="button" class="button btn-dark">
								<span>Cart</span>
							</button>
						</div>
					</div>
				</div>

			</div>
			<!-- / .row -->

			<!-- Pagination -->

			<div class="row justify-content-center">
				<div class="text-center">
					<ul class="pagination">
						<li><a href="#">«</a></li>
						<li><a class="page-link" href="#">1</a></li>
						<li><a class="page-link" href="#">2</a></li>
						<li><a class="page-link" href="#">3</a></li>
						<li><a href="#">»</a></li>
					</ul>
				</div>
			</div>
			<!-- / .row -->

		</div>
		<!-- / .col-sm-8 -->
	</div>
	<!-- / .row -->
</div>



<jsp:include page="../footer.jsp" />