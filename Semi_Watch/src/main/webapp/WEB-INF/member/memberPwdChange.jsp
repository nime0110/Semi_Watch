<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctx_Path = request.getContextPath();
    //    /Semi_Watch
%>

<jsp:include page="../header1.jsp"></jsp:include>

<link href="<%= ctx_Path%>/css/gologin.css" rel="stylesheet">

<%-- 외부 JS --%>>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/editPwd.js"></script> 
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

    <form class="well form-horizontal" name="editPwdFrm" id="contact_form">
<fieldset>
<!-- Form Name -->
<legend><br><br>${sessionScope.loginuser.userid}&nbsp;회원 비밀번호수정</legend>

<!-- Text input-->
<br>
<br>
<div class="form-group">
  <label class="col-md-4 control-label" >비밀번호</label> 
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  		<input name="pw" class="form-control requiredInfo" id="pwd" maxlength="15" type="password"/>
  		<input type="hidden" name="userid" id="userid" value="${sessionScope.loginuser.userid}"/>
    </div>
    <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
    <span id="duplicate_pwd" style="color: red;"></span>
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
<br>
<br>
<!-- Button -->
<div class="form-group" id="selectbuttondiv">
  <div class="col-md-4" id="selectbutton">
    <button type="button" class="btn btn-warning" onclick="goEdit()">수정하기</button>
    <button type="reset" class="btn btn-warning" onclick="goReset()">취소하기</button>
  </div>
</div>

</fieldset>
</form>
</div><!-- /.container -->

</div><!-- /#whole -->



<jsp:include page="../footer.jsp"></jsp:include>