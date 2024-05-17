<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath(); //현재 컨텍스트 패스는 /MyMVC
%>
<jsp:include page="header1.jsp" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index.css" />
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/font/css/all.css" />
<body>
		<!-- Start Hero Section -->
			<div class="hero">
				<div class="container">
					<div class="row justify-content-between">
						<div class="col-lg-5">
							<div class="intro-excerpt">
								<h1>Modern Interior <span class="d-block">Design Studio</span></h1>
								<p class="mb-4">Donec vitae odio quis nisl dapibus malesuada. Nullam ac aliquet velit. Aliquam vulputate velit imperdiet dolor tempor tristique.</p>
								<p><a href="" class="btn btn-secondary me-2">Shop Now</a><a href="#" class="btn btn-white-outline">Explore</a></p>
							</div>
						</div>
						<div class="col-lg-7">
							<div class="hero-img-wrap">
								<img src="${pageContext.request.contextPath}/images/index/tagwide.png" class="img-fluid">
							</div>
						</div>
					</div>
				</div>
			</div>
		<!-- End Hero Section -->

		<!-- Start Product Section -->
		<div class="product-section">
			<div class="container">
				<div class="row">

					<!-- Start Column 1 -->
					<div class="col-md-12 col-lg-3 mb-5 mb-lg-0">
						<h2 class="mb-4 section-title">신상 시계 제목</h2>
						<p class="mb-4">시계 어쩌구저쩌구 </p>
						<p><a href="shop.html" class="btn">시계 목록 보러가기</a></p>
					</div> 
					<!-- End Column 1 -->

					<!-- Start Column 2 -->
					<div class="col-12 col-md-4 col-lg-3 mb-5 mb-md-0">
						<a class="product-item" href="cart.html">
							<img src="${pageContext.request.contextPath}/images/index/omega01.png" class="img-fluid product-thumbnail" style="width: 80%;">
							<h3 class="product-title">메탈 시계</h3>
							<strong class="product-price">$50.00</strong>
							<span class="icon-cross">
								<img src="${pageContext.request.contextPath}/images/index/cross.svg" class="img-fluid">
							</span>
						</a>
					</div> 
					<!-- End Column 2 -->

					<!-- Start Column 3 -->
					<div class="col-12 col-md-4 col-lg-3 mb-5 mb-md-0">
						<a class="product-item" href="cart.html">
							<img src="${pageContext.request.contextPath}/images/index/omega02.png" class="img-fluid product-thumbnail" style="width: 80%;">
							<h3 class="product-title">~~ 시계</h3>
							<strong class="product-price">$78.00</strong>

							<span class="icon-cross">
								<img src="${pageContext.request.contextPath}/images/index/cross.svg" class="img-fluid">
							</span>
						</a>
					</div>
					<!-- End Column 3 -->
					
					<!-- Start Column 4 -->
					<div class="col-12 col-md-4 col-lg-3 mb-5 mb-md-0">
						<a class="product-item" href="cart.html">
							<img src="${pageContext.request.contextPath}/images/index/omega04.png" class="img-fluid product-thumbnail" style="width: 80%;">
							<h3 class="product-title">~~시계</h3>
							<strong class="product-price">$43.00</strong>
							<span class="icon-cross">
								<img src="${pageContext.request.contextPath}/images/index/cross.svg" class="img-fluid">
							</span>
						</a>
					</div>
					<!-- End Column 4 -->

				</div>
			</div>
		</div>
		<!-- End Product Section -->

		<!-- Start Why Choose Us Section -->
		<div class="why-choose-section">
			<div class="container">
				<div class="row justify-content-between">
					<div class="col-lg-6">
						<h2 class="section-title">무료 배송 & 무료 반품</h2>
						<p>저희 쇼핑몰은 무료 배송과 무료 반품 혜택을 제공하여 여러분의 쇼핑 경험을 더욱 편리하고 즐겁게 만들어드립니다. 
							언제든지 원하시는 시계를 선택하고 편안하게 집에서 만나보세요. 
							그리고 만약에 만족하지 않는다면, 걱정마세요! 무료 반품으로 여러분의 만족도를 최우선으로 챙겨드립니다. 
						</p>
						<p><a href="shop.html" class="btn">About us</a></p>
						<div class="row my-5">
							<div class="col-6 col-md-6">
								<div class="feature">
									<div class="icon">
										<img src="${pageContext.request.contextPath}/images/index/truck.svg" alt="Image" class="imf-fluid">
									</div>
									<h3>신속한 무료 배송</h3>
									<p>추가 비용이나 긴 배송 시간의 스트레스 없이 구매를 즐길 수 있습니다.</p>
								</div>
							</div>

							<div class="col-6 col-md-6">
								<div class="feature">
									<div class="icon">
										<img src="${pageContext.request.contextPath}/images/index/bag.svg" alt="Image" class="imf-fluid">
									</div>
									<h3>편리한 반품</h3>
									<p>고객들에게 번거로움 없는 반품 서비스를 제공하여 프로세스를 간단하고 스트레스 없이 만들어 드립니다</p>
								</div>
							</div>

						</div>
					</div>
					<div class="col-lg-5">
						<div class="img-wrap">
							<img src="${pageContext.request.contextPath}/images/index/why-choose-us-img.png" alt="Image"style="width: 600px; margin-top:">
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Why Choose Us Section -->
		<!-- Start Blog Section -->
		<div class="blog-section">
			<div class="container">
				<div class="row mb-5">
					<div class="col-md-6">
						<h2 class="section-title">Recent Blog</h2>
					</div>
<!-- 					<div class="col-md-6 text-start text-md-end">
						<a href="#" class="more">View All Posts</a>
					</div> -->
				</div>

				<div class="row">

					<div class="col-12 col-sm-6 col-md-4 mb-4 mb-md-0">
						<div class="post-entry">
							<a href="#" class="post-thumbnail"><img src="${pageContext.request.contextPath}/images/index/post-1.jpg" alt="Image" class="img-fluid"></a>
							<div class="post-content-entry">
								<h3><a href="#">First Time Home Owner Ideas</a></h3>
								<div class="meta">
									<span>by <a href="#">Kristin Watson</a></span> <span>on <a href="#">Dec 19, 2021</a></span>
								</div>
							</div>
						</div>
					</div>

					<div class="col-12 col-sm-6 col-md-4 mb-4 mb-md-0">
						<div class="post-entry">
							<a href="#" class="post-thumbnail"><img src="${pageContext.request.contextPath}/images/index/post-2.jpg" alt="Image" class="img-fluid"></a>
							<div class="post-content-entry">
								<h3><a href="#">How To Keep Your Furniture Clean</a></h3>
								<div class="meta">
									<span>by <a href="#">Robert Fox</a></span> <span>on <a href="#">Dec 15, 2021</a></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 col-sm-6 col-md-4 mb-4 mb-md-0">
						<div class="post-entry">
							<a href="#" class="post-thumbnail"><img src="${pageContext.request.contextPath}/images/index/post-3.jpg" alt="Image" class="img-fluid"></a>
							<div class="post-content-entry">
								<h3><a href="#">Small Space Furniture Apartment Ideas</a></h3>
								<div class="meta">
									<span>by <a href="#">Kristin Watson</a></span> <span>on <a href="#">Dec 12, 2021</a></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Blog Section -->	
</body>
</html>
<jsp:include page="footer.jsp" />