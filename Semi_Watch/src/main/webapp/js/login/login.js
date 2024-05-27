$(document).ready(function () {
  $("button#btnSubmit").click(function() { 
    goLogin(); //로그인 시도한다
  });

  
  $("input#loginPwd").bind("keydown", function (e) {
    if(e.keyCode == 13) { //암호입력란에 엔터
      goLogin();
    }
  });

  $("input#loginUserid").bind("keydown", function (e) {
    if(e.keyCode == 13) { //유저아이디에 엔터
      goLogin();
    }
  });

}); //end of jqready ----------------------------------------------

// Function Declaration

// === 로그인 처리 함수 === //
function goLogin() {
  // alert("확인용 로그인 처리 하러 간다");
  // 아이디를 입력을 안하거나 공백
  const userid = $("input#loginUserid").val().trim();
  const passwd = $("input#loginPwd").val().trim();

  if(userid == "") {
    alert("아이디를 입력하세요!");
    $("input#loginUserid").val("").focus();
    return;
  }
  if(passwd == "") {
    alert("암호를 입력하세요!");
    ("input#loginPwd").val("").focus();
    return;
  }

  if($("input:checkbox[id='saveid']").prop("checked")) {
    // alert("아이디 저장 체크를 하셨습니다.");
    localStorage.setItem('saveid', $("input:text[id='loginUserid']").val());
  } else {
    // alert("아이디 저장 체크를 해제하셨습니다.");
    localStorage.removeItem('saveid');
  }
  
  /*
  == 보안상 민감한 데이터는 로컬 혹은 세션스토리지에 저장시켜두면 안된다
  if($("input:checkbox[id='savepwd']").prop("checked")) {
    localStorage.setItem('savepwd', $("input#loginPwd").val());
  } else {
    // alert("아이디 저장 체크를 해제하셨네요");
    localStorage.removeItem('savepwd');
  }
  */
  

  const frm = document.loginFrm; //폼의 name이 loginFrm
  frm.submit();

} // end of function goLogin()----------------------------

/* --------------------------- ✨로그아웃/코인충전 처리 함수 -------------------- 
function goLogOut(ctx_Path) {
  // 로그아웃을 처리해 준다 -> 자바가 처리할수있기 때문에 로그아웃을 처리해주는 페이지로 이동을 시켜주기로 함
  // JS가 밖으로 빠져나와져있으면 ctxPath를 모르기 때문에 상대경로를 해준다 = 물론 MyMVC/해줄순 있겠지만 WAS에서 컨텍스트 패스를 바꿀수도 있기 때문에 안됨
  // 그렇기 때문에 매개변수로 받음
  location.href=`${ctx_Path}/login/logout.up`;
  
}// end of function goLogOut(ctx_Path)-----------------------------------------------------

// === 코인충전 결제금액 선택하기(실제로 카드 결제) === //
function goCoinPurchaseTypeChoice(userid, ctx_Path) {
  
  // 너비 650, 높이 570 인 팝업창을 화면 가운데 위치시키기
  const width = 650;
  const height = 570;
  
  const left = Math.ceil((window.screen.width - width) / 2); //정수로 만듬(Math.ceil)
  //내 모니터의 화면 넓이가 window.screen.width
  // 1400 - 650 = 750/2 ==> 375

  const top = Math.ceil((window.screen.height - height) / 2); //정수로 만듬(Math.ceil)

  // 코인충전 팝업창 띄우기 --------------------
  // get으로 하지만 보안적인 부분을 다 막으면 됨!! 
  const url = `${ctx_Path}/member/coinPurchaseTypeChoice.up?userid=${userid}`;
  window.open(url, "coinPurchaseTypeChoice", `left=${left}, top=${top}, width=${width}, height=${height}`);
  
} //end of function goCoinPurchaseTypeChoice(userid,ctx_Path) ---------------
*/

/*
// === 포트원(구 아임포트) 결제를 해주는 함수 === //
function goCoinPurchaseEnd(ctxPath,coinmoney,userid) {
  // alert(`확인용 부모창의 함수 호출함.\n결제금액:${coinmoney}원, 사용자아이디:${userid}, 컨텍스트패스:${ctxPath}`);

  const width = 1000;
  const height = 600;
  const left = Math.ceil((window.screen.width - width) / 2);
  const top = Math.ceil((window.screen.height - height) / 2);

  const url = `${ctxPath}/member/coinPurchaseEnd.up?coinmoney=${coinmoney}&userid=${userid}`;
  window.open(url, "coinPurchaseEnd", `left=${left}, top=${top}, width=${width}, height=${height}`);
  //주소, coinPurchaseEn = 팝업창 이름
} //end of function goCoinPurchaseEnd() --------------------------------------    

// ==== DB 상의 tbl_member 테이블에 해당 사용자의 코인금액 및 포인트를 증가(update)시켜주는 함수 === //
function goCoinUpdate(ctxPath, userid, coinmoney) {
  console.log(`~~ 확인용 userid : ${userid}, coinmoney : ${coinmoney}원`);
  //ajax
  $.ajax({
    url : ctxPath+"/member/coinUpdateLoginUser.up",
    data : {"userid" : userid,
            "coinmoney" : coinmoney}, // data 속성은 http://localhost:9090/MyMVC/member/coinUpdateLoginUser.up 로 전송해야할 데이터를 말한다.
    type : "post",  // type 을 생략하면 type : "get" 이다.

    async : true,   // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
                   // async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.
    
    dataType : "json",  // Javascript Standard Object Notation.  dataType은 /MyMVC/member/coinUpdateLoginUser.up '로 부터' 실행되어진 결과물을 받아오는 데이터타입을 말한다. 
                        // 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/coinUpdateLoginUser.up 로 부터 받아오는 결과물은 xml 형식이어야 한다. 
                        // 만약에 dataType:"json" 으로 해주면 /MyMVC/member/coinUpdateLoginUser.up 로 부터 받아오는 결과물은 json 형식이어야 한다.              

    success : function(json){
      //성공되어지면 /MyMVC/member/coinUpdateLoginUser.up '로 부터' 실행되어진 결과물을 받아옴
      //console.log("확인용 json => ", json);
      //확인용 json =>  {loc: '/MyMVC/index.up', message: '허성심님의300,000원 결제가 완료되었습니다', n: 1}      
      
      alert(json.message); // message: '허성심님의300,000원 결제가 완료되었습니다' 를 띄움
      location.href = json.loc; //즉 loc: '/MyMVC/index.up'로 이동
      location.href = history.go(0);
      // if(json.n) {

      // }
      // else {

      // }

    },
    
    error: function(request, status, error){
        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
    }

});
} //end of function goCoinUpdate(userid, coinmoney)  -------------------------

*/
