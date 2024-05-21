<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String ctxPath = request.getContextPath();
%>

<!-- Bootstrap CSS -->
<link href="<%= ctxPath%>/css/reset.css" rel="stylesheet">
<link href="<%= ctxPath%>/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link href="<%= ctxPath%>/css/tiny-slider.css" rel="stylesheet">

<style>

.btn.btn-primary {
    background: #3b5d50;
    border-color: #3b5d50; 
}
    
.btn.btn-primary:hover {
    background: #314d43;
    border-color: #314d43; 
}

/* 입력란과 라벨을 가로로 배열 */
  #rememberiddiv {
     border: solid 0px black;
    display: flex;
    align-items: center; /* 세로 중앙 정렬 */
  }

  /* 체크박스 입력란의 오른쪽 마진 조절 */
  #rememberiddiv .form-check-input {
    margin-right: 5px;
  }    

label.form-check-label{
   border: solid 0px black;
   margin-right: 51%;

}

input.form-check-input{
   margin-bottom: 1.2%;
   margin-right: 1% !important;
}    

</style>



<jsp:include page="../header1.jsp"></jsp:include>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/login/login.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript">
   $(document).ready(function(){

      // === 로그인을 하지 않은 상태일 때 
        //     로컬스토리지(localStorage)에 저장된 key가 'saveid' 인 userid 값을 불러와서 
        //     input 태그 userid 에 넣어주기 ===
        if(${empty sessionScope.loginuser}) {
           //값비우기 
           $("input#loginUserid").val("");
           $("input#loginPwd").val("");

           const loginUserid = localStorage.getItem('saveid');
           if(loginUserid != null) {
              $("input#loginUserid").val(loginUserid); //값을 유저아이디 input 태그에 넣고
              $("input:checkbox[id='saveid']").prop("checked", true); // 체크를 해주겠다는 것
           }
        }
      //////////////////////////////////////////////////////////////////////
           
      // == 아이디 찾기에서 close 버튼을 클릭하면 iframe 의 form 태그에 입력된 값을 지우기 == //
      $("button.idFindClose").click(function() {
         
         const iframe_idFind = document.getElementById("iframe_idFind"); 
           // 대상 아이프레임을 선택한다.

           const iframe_window = iframe_idFind.contentWindow;
           
           iframe_window.func_form_reset_empty();
           // func_form_reset_empty() 함수는 idFind.jsp 파일에 정의해 둠.
           
      }); //end of $("button.idFindClose").click(function() ---------------------------------------
      
      //비밀번호 찾기에서 close 버튼 클릭시 새로고침
      $("button.passwdFindClose").click(function() {         
            javascript:history.go(0);
      });
   })
</script>
    



<section class="vh-100" style="background-color: #eee;">
  <div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col-lg-12 col-xl-11">
        <div class="card text-black" style="border-radius: 25px;">
          <div class="card-body p-md-5">
            <div class="row justify-content-center">
              <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">시계가게</p>

                <form class="mx-1 mx-md-4"  name="loginFrm" action="<%= ctxPath%>/login/login.flex" method="post">

                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                    <div data-mdb-input-init class="form-outline flex-fill mb-0">
                      <input type="text" name="userid" id="loginUserid" class="form-control" placeholder="ID"/>
                      <label class="form-label" for="form3Example1c" style="display:none">Your ID</label>
                    </div>
                  </div>

                  <div class="d-flex flex-row align-items-center mb-4">
                    <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                    <div data-mdb-input-init class="form-outline flex-fill mb-0">
                      <input type="password" name="pwd" id="loginPwd" class="form-control" placeholder="PASSWORD"/>
                      <label class="form-label" for="form3Example3c" style="display:none">Your PASSWORD</label>
                    </div>
                  </div>         

                  <div class="form-check d-flex justify-content-center mb-5" id="rememberiddiv">
                    <input class="form-check-input me-2" type="checkbox" value="" id="saveid" />
                    <label class="form-check-label" for="saveid">
                      Remember ID 
                    </label>
                    <input type="checkbox" name="loginChk" value="true" >로그인 상태 유지<br/>
                  </div>

                  <div class="d-grid mb-4">
                        <button type="submit" id="btnSubmit" class="btn btn-primary">로그인</button>
                    </div>
                  <div> 
                    <ul class="find_wrap" id="find_wrap" style="display:flex;">
                      <li><a style="cursor : pointer" target="_blank" class="find_passwd" data-toggle="modal" 
                      data-target="#userIdfind" data-dismiss="modal">아이디찾기</a> ㅣ</li>
                      <li><a style="cursor : pointer" target="_blank" class="find_id" data-toggle="modal" 
                      data-target="#passwdFind" data-dismiss="modal" data-backdrop="static">비밀번호찾기</a> ㅣ</li>
                      <li><a style="cursor : pointer" class="member_register" href="<%= ctxPath %>/member/memberRegister.flex">회원가입</a>
                      </li>
                      </ul>
              </div> 
                </form>

              </div>
              <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                <img src="../images/bvlgari07.png" class="img-fluid" alt="Sample image">

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>


<%-- ****** 아이디 찾기 Modal 시작 ****** --%>
     <%--<div class="modal fade" id="userIdfind"> 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다.    --%>
      <div class="modal fade" id="userIdfind" data-backdrop="static"> <%-- 데이터 백드롭을 통해 버튼 입력 외 창이 닫히지 않게 한다. --%>
       <div class="modal-dialog">
         <div class="modal-content">
         
           <!-- Modal header -->
           <div class="modal-header">
             <h4 class="modal-title">아이디 찾기</h4>
             <button type="button" class="close idFindClose" data-dismiss="modal">&times;</button>
           </div>
           
           <!-- Modal body -->
           <div class="modal-body">
             <div id="idFind">
                <iframe id="iframe_idFind" style="border: none; width: 100%; height: 350px;" src="<%= ctxPath%>/login/idFind.flex">
                </iframe>
             </div>
           </div>
           
           <!-- Modal footer -->
           <div class="modal-footer">
             <button type="button" class="btn btn-danger idFindClose" data-dismiss="modal">Close</button>
           </div>
         </div>
         
       </div>
     </div>
<%-- ****** 아이디 찾기 Modal 끝 ****** --%>
   
   
<%-- ****** 비밀번호 찾기 Modal 시작 ****** --%>
     <div class="modal fade" id="passwdFind"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>
       <div class="modal-dialog">
         <div class="modal-content">
         
           <!-- Modal header -->
           <div class="modal-header">
             <h4 class="modal-title">비밀번호 찾기</h4>
             <button type="button" class="close passwdFindClose" data-dismiss="modal">&times;</button>
           </div>
           
           <!-- Modal body -->
           <div class="modal-body">
             <div id="pwFind">
                <iframe style="border: none; width: 100%; height: 350px;" src="<%= ctxPath%>/login/pwdFind.flex">  
                </iframe>
             </div>
           </div>
           
           <!-- Modal footer -->
           <div class="modal-footer">
             <button type="button" class="btn btn-danger passwdFindClose" data-dismiss="modal">Close</button>
           </div>
         </div>
         
       </div>
     </div> 
   
<%-- ****** 비밀번호 찾기 Modal 끝 ****** --%>




<jsp:include page="../footer.jsp"></jsp:include>