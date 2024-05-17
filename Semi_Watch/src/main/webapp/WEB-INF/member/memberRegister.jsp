<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctx_Path = request.getContextPath();
    //    /Semi_Watch
%>

<jsp:include page="../header1.jsp"></jsp:include>

<link href="<%= ctx_Path%>/css/gologin.css" rel="stylesheet">


<script type="text/javascript">

$(document).ready(function(){
	
	$("span.error").hide();
	
	
	$('input#datepicker').keyup( (e) => {

        $(e.target).val("").next().show();


   });// end of $('input#datepicker').keyup( (e)

	
	
});

</script> 

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
	  <input name="first_name" placeholder="홍길동" class="form-control"  type="text"/>
    </div>
    <span class="error">성명은 필수입력 사항입니다.</span>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >아이디</label> 
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
	  <input name="last_name" placeholder="honggd" class="form-control"  type="text"/>
	  <span id="idcheck">아이디중복확인</span>
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
  <input name="last_name" placeholder="honggd" class="form-control"  type="text"/>
    </div>
    <span class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >비밀번호확인</label> 
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="last_name" placeholder="honggd" class="form-control"  type="text"/>
    </div>
    <span class="error">암호가 일치하지 않습니다.</span>
  </div>
</div>

<!-- Text input-->
       <div class="form-group">
  <label class="col-md-4 control-label">이메일</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="email" placeholder="honggd@gmail.com" class="form-control"  type="text"/>
  
  <%-- 이메일중복체크 --%>
  <span id="emailcheck">이메일중복확인</span>
  <span id="emailCheckResult"></span>
    </div>
    <span class="error">이메일 형식에 맞지 않습니다.</span>
  </div>
</div>


<!-- Text input-->
       
<div class="form-group">
  <label class="col-md-4 control-label">휴대전화</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="phone" placeholder="010-1234-1234" class="form-control" type="text"/>
    </div>
    <span class="error">휴대폰 형식이 아닙니다.</span>
  </div>
</div>

<!-- Text input-->
      
<div class="form-group">
  <label class="col-md-4 control-label">우편번호</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="postcode" placeholder="12345" class="form-control" type="text"/>
  <%-- 우편번호 찾기 --%>
  <span id="zipcodeSearch">우편번호 찾기</span>
    </div>
    <span class="error">우편번호 형식에 맞지 않습니다.</span>
  </div>
</div>

<!-- Text input-->
      
<div class="form-group">
  <label class="col-md-4 control-label">주소</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="address" placeholder="주소" class="form-control" type="text"/>
    </div>
    <span class="error">주소를 입력하세요.</span>
  </div>
</div>

<!-- Text input-->
 
<div class="form-group">
  <label class="col-md-4 control-label">상세주소</label>  
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <input name="detailaddress" placeholder="상세주소" class="form-control"  type="text"/>
    </div>
  </div>
</div>

<!-- Select Basic -->
   
<div class="form-group"> 
  <label class="col-md-4 control-label">참고항목</label>
    <div class="col-md-4 selectContainer">
    <div class="input-group">
    <input name="extraaddress" placeholder="참고항목" class="form-control" type="text"/>
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
            <label>
                <input type="radio" name="gender" value="man"/> 남자
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="gender" value="woman"/> 여자
            </label>
        </div>
    </div>
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