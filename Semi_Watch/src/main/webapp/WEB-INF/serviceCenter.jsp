<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<jsp:include page="header1.jsp"></jsp:include>

<style type="text/css">

@import url(//fonts.googleapis.com/css?family=Montserrat:300,400,500);


.team3 {
  font-family: "Montserrat", sans-serif;
  color: #8d97ad;
  font-weight: 300;
}

.team3 h1,
.team3 h2,
.team3 h3,
.team3 h4,
.team3 h5,
.team3 h6 {
  color: #3e4555;
}

.team3 .font-weight-medium {
  font-weight: 500;
}

.team3 .bg-light {
  background-color: #f4f8fa !important;
}

.team3 .subtitle {
  color: #8d97ad;
  line-height: 24px;
  font-size: 13px;
}

.team3 ul {
  margin-top: 30px;
}

.team3 h5 {
  line-height: 22px;
  font-size: 18px;
}

.team3 ul li a {
  color: #8d97ad;
  padding-right: 15px;
  -webkit-transition: 0.1s ease-in;
  -o-transition: 0.1s ease-in;
  transition: 0.1s ease-in;
}

.team3 ul li a:hover {
  -webkit-transform: translate3d(0px, -5px, 0px);
  transform: translate3d(0px, -5px, 0px);
	color: #316ce8;
}

.team3 .title {
  margin: 30px 0 0 0;
}

.team3 .subtitle {
  margin: 0 0 20px 0;
  font-size: 13px;
}

#our_character {
	weigh : 500px;
	height : 400px;

}

div.service_container {
	display : 
	background-color: white;
	text-align: center;
}

select.form-select {

	width : 300px;
	height: 60px;

}

</style>


<div class="py-5 team3 bg-light">
  <div class="container">
    <div class="row justify-content-center mb-4">
      <div class="col-md-7 text-center">
        <h3 class="mb-3">전국 서비스 센터 위치 안내</h3>
        <h6 class="subtitle font-weight-normal">최상의 서비스를 통해 고객만족을 실현하겠습니다.</h6>
      </div>
    </div>
    </div>
    </div>
    
<%-- 서비스 센터 선택항목 --%>

<div class="service_container">
	<div class="selectFrm">
		<select class="form-select" aria-label="Default select example">
		  <option selected>Open this select menu</option>
		  <option value="1">One</option>
		  <option value="2">Two</option>
		  <option value="3">Three</option>
		</select>
		<br>
		<br>    
		<select class="form-select" aria-label="Default select example">
		  <option selected>제품 브랜드</option>
		  <option value="1">One</option>
		  <option value="2">Two</option>
		  <option value="3">Three</option>
		</select>
		<br>
		<br>    
		<select class="form-select" aria-label="Default select example">
		  <option selected>거주 지역</option>
		  <option value="1">One</option>
		  <option value="2">Two</option>
		  <option value="3">Three</option>
		</select>	
	</div>
	
	
	<br>
	<br>
	<br>    
	<div>지도</div>

</div>

<jsp:include page="footer.jsp"></jsp:include>