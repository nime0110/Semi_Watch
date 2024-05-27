<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
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

<script type="text/javascript">

	$(document).ready(function(){
	

	});// end of $(document).ready(function(){})-----------

	function goSearch(){
		
		const brand = $("select#brand").val();
		
		const area = $("select#area").val();
		
		if(brand == "" || area == ""){
			alert("브랜드 혹은 지역을 선택하지 않았습니다.");
			return;
		}
		
		if(brand =="1" && area =="1"){
			$("div.showMap").html('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d101257.96375230934!2d126.75135381790906!3d37.524156866096135!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357c9f3b0e33974f%3A0x2db6b1089f3cd38f!2sKairos%20Watch!5e0!3m2!1sko!2skr!4v1716739078844!5m2!1sko!2skr" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>');
		}
		
		if(brand =="2" && area =="1"){
			$("div.showMap").html('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d101251.08281378214!2d126.79428491971784!3d37.52922626591081!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x35703d7cddcc70c9%3A0xeaa1866d8b1f70e3!2z7Jik66mU6rCA!5e0!3m2!1sko!2skr!4v1716738092382!5m2!1sko!2skr" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>');
		}
		
		if(brand=="3" && area =="1"){
			$("div.showMap").html('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d101254.33508866561!2d126.7349123954773!3d37.526830287390176!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x357c9f5fc4641153%3A0xd5934b1502230894!2zRy1TSE9DSyDtmITrjIDrsLHtmZTsoJDrqqnrj5nsoJA!5e0!3m2!1sko!2skr!4v1716739219940!5m2!1sko!2skr" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>');
		}
		
		
	}// end of function goSearch()



</script>


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
		<select class="form-select" aria-label="Default select example" id="brand">
		  <option selected value="" style="text-align: center;">== 제품 브랜드 ==</option>
		  <option value="1">롤렉스</option>
		  <option value="2">오메가</option>
		  <option value="3">G-SHOCK</option>
		</select>
		<br>
		<br>    
		<select class="form-select" aria-label="Default select example" id="area">
		  <option selected value="" style="text-align: center;">== 거주 지역 ==</option>
		  <option value="1">서울강서</option>
		  <option value="2">서울강남</option>
		  <option value="3">서울강북</option>
		</select>	
	</div>
	<br>
	<button type="button" class="btn btn-secondary" id="searchBtn" onclick="goSearch()">서비스센터위치찾기</button>
	<br>
	<br>
	<br>    
	<div class="showMap">
	</div>

</div>

<jsp:include page="footer.jsp"></jsp:include>