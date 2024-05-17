<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctx_Path = request.getContextPath();
    //    /Semi_Watch
%>

<jsp:include page="../header1.jsp"></jsp:include>

<link href="<%= ctx_Path%>/css/gologin.css" rel="stylesheet">

<%-- 외부 JS --%>>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/memberRegister.js"></script> 
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script> 

<%-- jQuaryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctx_Path%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>


<style>

#success_message{ display: none;}

body > div.container {
	margin: 2% auto;

}

 div.form-group {
 	border: solid 0px black;
 	margin-bottom: 1%;
 
 }

 div#registerdiv {
 	border: solid 0px blue;
 	width: 45%;
 	margin: -2% 0 1% 6%;
 	padding-left : -3%;
 	
 
 }
 
 div#whole {
 	border: solid 0px black;
 	display: flex;
 	
 	
 }
 
 div.input-group{
 	border: solid 0px orange;
 	display: flex;
 	width: 220%;
 	
 }
 
  div#img {
  	border: solid 0px orange;
  	margin-top : -5%;
  	padding-top : 9%;
  	padding-bottom : 10%;
  	padding-left : 6%
  	width : 80%;
  	background-color: white;
  	  	
  }
  
  #img > img {
  	width : 70%;
  	margin-top : 5%;
  	margin-left : 14%;
  	border-radius: 25%;
  	
  }
  
  div#selectbuttondiv{
	border: solid 0px black;
	width: 200%;  
  }
  
  div#selectbutton {
  	border: solid 0px red;
  	display : flex;
  	width: 100% !important;
  	
  	
  }
  
  form#contact_form {
  	border: solid 0px orange;
  	width : 110%;
  	margin-right : 20%;
  	
  }
  	
  input.form-control {
  	border: solid 0px red;
  	border-radius: 10px !important;
  	margin-right: 1%;	 	
  }
  
  #contact_form > fieldset > div:nth-child(3) > div > div > input,
  #contact_form > fieldset > div:nth-child(6) > div > div > input,
  #contact_form > fieldset > div:nth-child(8) > div > div > input {
  	
  	
  
  }
  
  
  
  span#emailcheck {
	border: solid 1px gray;
	background-color: white;
	border-radius: 5px;
	font-size: 8pt;
	display: inline-block;
	width: 80px;
	height: 30px;
	text-align: center;
	margin-left: 10px;
	cursor: pointer;
  }
  
  span#idcheck {
	border: solid 1px gray;
	background-color: white;
	border-radius: 5px;
	font-size: 8pt;
	display: inline-block;
	width: 80px;
	height: 30px;
	text-align: center;
	margin-left: 10px;
	cursor: pointer;
  }
  
  span#zipcodeSearch {
	border: solid 1px gray;
	background-color: white;
	border-radius: 5px;
	font-size: 8pt;
	display: inline-block;
	width: 80px;
	height: 30px;
	text-align: center;
	margin-left: 10px;
	cursor: pointer;
  }
  
  span.error {
  	font-weight:bold; 
  }

</style>

<div id="whole">
<div id="img">
<img alt="" src="<%= ctx_Path%>/images/FY7ZMP0WYAA0Icg.png">

</div>

<div class="container" id="registerdiv">

    <form class="well form-horizontal" action=" " method="post"  id="contact_form">
<fieldset>

<!-- Form Name -->
<legend>회원가입</legend>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label">성명</label>  
  <div class="col-md-4 inputGroupContainer">
  	<div class="input-group">
	  <input name="name" class="form-control requiredInfo" id="name" type="text"/>
    </div>
    <span class="error">성명은 필수입력 사항입니다.</span>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >아이디</label> 
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
	  <input name="userid" class="form-control requiredInfo" id="userid" type="text"/>
	  <span type="button" id="idcheck">아이디중복확인</span>
	  <span id="idCheckResult"></span>
    </div>
    <span class="error">아이디는 필수입력 사항입니다.</span>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >비밀번호</label> 
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="pwd" class="form-control requiredInfo" id="pwd" maxlength="15" type="password"/>
    </div>
    <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >비밀번호확인</label> 
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input class="form-control requiredInfo" id="pwdcheck" maxlength="15"  type="password"/>
    </div>
    <span class="error">암호가 일치하지 않습니다.</span>
  </div>
</div>

<!-- Text input-->
       <div class="form-group">
  <label class="col-md-4 control-label">이메일</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="email" class="form-control requiredInfo" id="email" maxlength="60" type="text"/>
  
  <%-- 이메일중복체크 --%>
  <span type="button" id="emailcheck">이메일중복확인</span>
  <span id="emailCheckResult"></span>
    </div>
    <span class="error">이메일 형식에 맞지 않습니다.</span>
  </div>
</div>


<!-- Text input-->
       
<div class="form-group">
  <label class="col-md-4 control-label">연락처</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="hp1" class="form-control" id="hp1" size="6" mexlength="3" value="010" readonly type="text"/>&nbsp;-&nbsp;
  <input name="hp2" class="form-control" id="hp2" size="6" mexlength="4" type="text"/>&nbsp;-&nbsp;
  <input name="hp3" class="form-control" id="hp3" size="6" mexlength="4" type="text"/>
    </div>
    <span class="error">휴대폰 형식이 아닙니다.</span>
  </div>
</div>

<!-- Text input-->
      
<div class="form-group">
  <label class="col-md-4 control-label">우편번호</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="postcode" class="form-control" id="postcode" size="6" type="text"/>
  <%-- 우편번호 찾기 --%>
  <span type="button" id="zipcodeSearch">우편번호 찾기</span>
    </div>
    <span class="error">우편번호 형식에 맞지 않습니다.</span>
  </div>
</div>

<!-- Text input-->
      
<div class="form-group">
  <label class="col-md-4 control-label">주소</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="address" class="form-control" id="address"
  size="40" maxlength="200" type="text"/>
    </div>
    <span class="error">주소를 입력하세요.</span>
  </div>
</div>

<!-- Text input-->
 
<div class="form-group">
  <label class="col-md-4 control-label">상세주소</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="detailAddress" class="form-control" id="detailAddress"
  size="40" maxlength="200" type="text"/>
    </div>
  </div>
</div>

<!-- Select Basic -->
   
<div class="form-group"> 
  <label class="col-md-4 control-label">참고항목</label>
    <div class="col-md-4 selectContainer">
    <div class="input-group">
    <input name="extraAddress" class="form-control" id="extraAddress"
    size="40" maxlength="200" type="text"/>
  </div>
</div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label">생년월일</label>  
   <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
        <input type="text" name="birthday" id="datepicker" maxlength="10"/>      
    </div>
    <span class="error">생년월일은 마우스로만 클릭하세요.</span>
  </div>
</div>

<!-- radio checks -->
 <div class="form-group">
 	<label class="col-md-4 control-label">성별</label>
    <div class="col-md-4">
        <div class="radio">
            <input type="radio" name="gender" id="male" value="1"/><label for="male" style="margin-left: 1.5%;">남자</label>
        </div>
        <div class="radio">
            <input type="radio" name="gender" id="female" value="2"/><label for="female" style="margin-left: 1.5%;">여자</label>
        </div>
    </div>
</div>

<!-- 약관동의 -->
<div class="form-group">
	<label for="agree">이용약관에 동의합니다.</label>&nbsp;&nbsp;<input type="checkbox" id="agree"/>
</div>

<!-- 약관 html -->
<div class="form-group">
	<iframe src="<%= ctx_Path%>/iframe_agree/agree.html" width="80%" height="150px"
	style="border: solid 1px navy;"></iframe>
</div>  


<!-- Button -->
<div class="form-group" id="selectbuttondiv">
  <div class="col-md-4" id="selectbutton">
    <button type="button" class="btn btn-warning" onclick="goRegister()">가입하기</button>
    <button type="reset" class="btn btn-warning" onclick="goReset()">취소하기</button>
  </div>
</div>

</fieldset>
</form>
</div><!-- /.container -->

</div><!-- /#whole -->



<jsp:include page="../footer.jsp"></jsp:include>