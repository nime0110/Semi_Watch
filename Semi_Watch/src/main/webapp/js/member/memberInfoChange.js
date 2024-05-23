
$(document).ready(function(){

    const ctxPath = document.getElementById("ctxPath").value;

    // 변경 row 안보이게 함
    $("tr#change_password_area").hide();
    $("tr#change_email_area").hide();
    $("tr#change_post_area").hide();
    $("tr#change_mobile_area").hide();
    $("tr#change_profile_image_area").hide();

    // $("table#userinfo").find("tr#change_password_area").hide();
    // $("table#userinfo").find("tr#change_email_area").hide();




    // ==== 비밀번호 관련 내용  시작 === //
    /*
    // 현재비밀번호 마스킹 처리
    const currentpwd= $("input:hidden[name='currentPwd']").val();
    const maskedPwd = '*'.repeat(currentpwd.length);
    $("td#cpwdview").text(maskedPwd);

    // 비밀번호 표시 버튼을 클릭했을 때의 동작을 정의합니다.
    $('img#viewEye').click(function(e) {
        // 버튼의 부모
        const parent = $(e.target).parent();
        const passwordInput = parent.find('input#confirmPassword');
        
        if (passwordInput.attr('type') == 'password') {
        	passwordInput.attr('type','text');
            $(e.target).attr("src",`${ctxPath}/images/eye-show.png`);
        } else {
        	passwordInput.attr('type','password');
        	$(e.target).attr("src",`${ctxPath}/images/eye-hide.png`);
        }
    });
    */

    // 비밀번호 변경 버튼 누르면
    $("button#change_pwd").click(function(){
        
        $("tr#password_area").hide();
        $("tr#change_password_area").show();

    }); // end of $("button#change_btn").click(function() -------

    
    // 신규비밀번호가 양식에 맞는지 확인
    $("input#newPassword").blur( (e) => {
        $("span#pwd_check2").empty();
    //	const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g; 
    //  또는
        const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
        // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성 
        
        const bool = regExp_pwd.test($(e.target).val());	
        
        if(!bool) {
            // 암호가 정규표현식에 위배된 경우 
            
        //	$(e.target).next().show();
        //  또는
            $("span#pwd_check").html("숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호이여야 합니다.");
            $(e.target).val("");
        }
        else {
            // 암호가 정규표현식에 맞는 경우 
            $("span#pwd_check").empty();
        }
        
    });// 아이디가 newPassword 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.
    
    // 신규 비밀번호 확인
    $("input#confirmPassword").blur( (e) => {
        
        if( $("input#newPassword").val() != $(e.target).val() ) {
            // 암호와 암호확인값이 틀린 경우 

            $("span#pwd_check2").html("비밀번호가 일치하지 않습니다.");
            $(e.target).val("");
            $("input#newPassword").val("");
        }
        else {
            // 암호와 암호확인값이 같은 경우
            $("span#pwd_check2").empty();
        }
        
    });// pwdcheck 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.	

    // 비밀번호 취소 버튼 눌렀을 때 입력한 내용 다 초기화 하고 비밀번호 변경 탭 안보임
    $("button:reset[id='pwdcancle']").click(function(){
        $("span#pwd_wrong").empty();
        $("span#pwd_check").empty();
        $("span#pwd_check2").empty();

        $("tr#password_area").show();
        $("tr#change_password_area").hide();

    });

    // ==== 비밀번호 관련 내용  끝 === //



    // ==== 이메일 관련 내용 시작 === //
    // 이메일 변경 버튼 누르면
    $("button#change_email").click(function(){
        
        $("tr#email_area").hide();
        $("tr#change_email_area").show();
        $("button#send_authentication_email").attr("disabled", true);
        $("button#submit_email").attr("disabled", true);
        

        $("div#email_authTemp").hide(); // 인증코드 입력부분 숨기기

    }); // end of $("button#change_btn").click(function() -------

    // 이메일 변경 취소 버튼 누르면
    $("button:reset[id='emailcancle']").click(function(){
        $("span#email_check").empty();
        $("span#authTempKey_check").empty();

        $("tr#change_email_area").hide();
        $("tr#email_area").show();
        $("div#email_authTemp").hide(); // 인증코드 입력부분 숨기기

        // 다시 입력할 수 있도록 풀어주기
        $("input#newEmail").removeAttr("disabled");


    });

    // 신규이메일 유효성 검사
    $("input#newEmail").blur(e => {

        // 이메일 유효성검사
        //   const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  
        //  또는
       const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
       // 이메일 정규표현식 객체 생성

        const bool = regExp_email.test($(e.target).val());
        
        if(!bool){ // 이메일이 정규표현식에 위배된 경우
            
            ///$(e.target).next().show();  // <span>태그는 보여라
            // 또는
            $("span#email_check").html("이메일 양식에 맞게 입력하세요.").css("color", "red");
            $(e.target).val("");
            $("button#send_authentication_email").attr("disabled", true);
            
        }
        else{ // 공백이 아닌 글자를 입력했을 경우
            $("button#send_authentication_email").removeAttr("disabled");
            $("span#email_check").empty();

        }

    });// email 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.

    // 인증번호발송 버튼 누르면
    $("button#send_authentication_email").click(function(){
        // alert("인증하기버튼 클릭!");
        // sendCode(); // 인증코드 발송 함수
        const newEmail = $("input:text[name='newEmail']").val().trim();
		
        if(newEmail == ""){
            alert("이메일이 올바르지 않습니다.");
            return false;	// 종료
        }

        $.ajax({
            url:"sendEmailCode.flex",
            data:{"newEmail":$("input#newEmail").val()
                 ,"userid":$("input:hidden[id='userid']").val()
                 ,"username":$("td#username").text()},

            type:"post",
            
            async:true, // 비동기 방식  
            
            dataType:"json", 
            
            success:function(json){

                if(json.sendMailSuccess) {  // 메일 전송 되어지면
                    // alert("인증코드 생성까지 되었습니다.");
                    // 초기화면 보여지도록
                    
                    $("div#email_authTemp").show();
                    $("input#email_authTempKey").focus();
                    $("span#email_check").html("인증코드가 발송되었습니다.").css("color","blue");
                    alert("인증코드가 발송되었습니다.");
                    
                    
                    // 이메일값 넣고, 인증번호 발송, 완료 버튼 비활성화,
                    $("input:text[name='newEmail']").attr("disabled", true);
                    $("button#send_authentication_email").attr("disabled", true);
                    $("button#submit_email").attr("disabled", true);

                    // 다시할 수 있으므로 인증버튼 초기화
                    $("button#authTempKey_check").removeAttr("disabled");
                    $("span#authTempKey_checkMent").empty();
                    
                 }
                 else{
                    alert("인증코드 발송을 실패하였습니다.");
                    $("input:text[name='newEmail']").attr("disabled", false);
                    $("button#send_authentication_email").attr("disabled", true);
                    $("span#email_check").html("인증번호 발송을 실패하였습니다. 다시 진행해주세요").css("color","red");
                 }
                
            },
            
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }


        });


    });// end of $("button#send_authentication_email").click(function()----------


    // 인증하기 버튼 클릭시
    $("button#authTempKey_check").click(function(){
        const input_confirmCode = $("input#email_authTempKey").val().trim();
        
        // alert("확인용 인증버튼 클릭함! "+input_confirmCode);
        
        if(input_confirmCode == ""){
            alert("인증코드를 입력하세요!!");
            $("span#authTempKey_checkMent").text("인증코드를 입력하세요!!").css("color","red");
            return; // 종료
        }

        // $("span#authTempKey_checkMent").empty();

        $.ajax({
            url:"verifyCertification.flex",
            data:{"emailAuthTempKey":$("input:text[name='emailAuthTempKey']").val()
                 ,"infoUpdate":$("input:hidden[name='infoUpdateE']").val()},

            type:"post",
            
            async:true, // 동기 방식  
            
            dataType:"json", 
            
            success:function(json){

                if(json.codeCheckAnswer){
                    alert("인증이 완료되었습니다. 변경하시려면 완료버튼을 눌러주세요");
                    $("button#authTempKey_check").attr("disabled", true);
                    $("span#email_check").empty();
                    $("span#authTempKey_checkMent").html("인증이 완료되었습니다.").css("color", "blue");
                    $("button#submit_email").removeAttr("disabled");
                }
                else{
                    alert("인증을 실패하였습니다. 다시 인증해주세요");
                    $("input#newEmail").removeAttr("disabled");
                    $("input#newEmail").focus();
                    $("span#email_check").empty();
                    $("div#email_authTemp").hide();
                    $("input#email_authTempKey").val("");
                    $("button#authTempKey_check").removeAttr("disabled");
                    $("button#submit_email").attr("disabled", true);
                    // $("span#mobileAuthTempKey_checkMent").html("인증을 실패하였습니다. 다시 인증해주세요").css("color", "red");
                }
                
            },
            
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });

        
    });// end of $("button.btn-info").click(function()----------

    // ==== 이메일 관련 내용 끝 === //




    // === 전화번호 관련 내용 시작 === //
    // 전화번호 변경 버튼 클릭 시
    $("button#change_btn").click(function(){
        $("tr#mobile_area").hide();
        $("tr#change_mobile_area").show();
        $("div#mobile_authTemp").hide();

        $("span#mobile_check").empty();
        $("span#mobileAuthTempKey_checkMent").empty();
        $("button#send_authentication_mobile").attr("disabled", true);
        $("button#mobileAuthTempKey_check").attr("disabled", true);

    });

    // 전화번호 유효성 검사
    $("input#newMoblie").blur( (e) => {
      
        //   const regExp_hp2 = /^[1-9][0-9]{3}$/;  
        //  또는
        const regExp_hp2 = new RegExp(/^[0][1][0][0-9]{8}$/);  
        // 연락처 정규표현식 객체 생성 
        
        const bool = regExp_hp2.test($(e.target).val());
        
        if(!bool) {
            // 연락처 국번이 정규표현식에 위배된 경우 

            $("span#mobile_check").html("올바른 전화번호를 입력하세요.").css("color","red");
            $(e.target).val("");
            $("button#send_authentication_mobile").attr("disabled", true);
            
             
        }
        else {
            $("button#send_authentication_mobile").removeAttr("disabled");
            $("span#mobile_check").empty();
        }
        
    });// 아이디가 hp2 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.


    // 인증번호 발송 버튼 클릭 시
    $("button#send_authentication_mobile").click(function(){
        const newMoblie = $("input#newMoblie").val().trim();

        if(newMoblie==""){
            alert("전화번호를 올바르게 입력하세요!!");
        }
        alert("전화번호 인증번호를 전송하였습니다.");

        $.ajax({
            url:"smsSendMy.flex",
            data:{"newMoblie":$("input#newMoblie").val()
                 ,"userid":$("input#userid").val()},

            type:"post",
            
            async:false, // 비동기 방식  
            
            dataType:"json", 
            
            success:function(json){

                if(json.success_count == 1) {
                    $("span#mobile_check").html("인증번호가 발송되었습니다.").css("color","blue");
                    $("input#newMoblie").attr("disabled",true);
                    $("button#send_authentication_mobile").attr("disabled",true);
                    $("button#mobileAuthTempKey_check").attr("disabled",false);

                    $("div#mobile_authTemp").show();

                    $("input#mobile_authTempKey").focus();
                    
                 }
                 else if(json.error_count != 0) {
                    $("span#mobile_check").html("인증번호 발송을 실패하였습니다. 다시 진행해주세요").css("color","red");
                 }
                
            },
            
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });

    });// end of  $("button#send_authentication_mobile").click(function()---

    // 인증하기 버튼 클릭 시
    $("button#mobileAuthTempKey_check").click(function(e){
        $(e.target).attr("disabled", true);

        $.ajax({
            url:"verifyCertification.flex",
            data:{"mobileAuthTempKey":$("input#mobile_authTempKey").val()
                 ,"newMoblie":$("input#newMoblie").val()
                 ,"infoUpdate":$("input:hidden[name='infoUpdateM']").val()},

            type:"post",
            
            async:false, // 비동기 방식  
            
            dataType:"json", 
            
            success:function(json){
                
                if(json.codeCheckAnswer){
                    alert("인증이 완료되었습니다. 변경하시려면 완료버튼을 눌러주세요");
                    $("button#mobileAuthTempKey_check").attr("disabled", true);
                    $("span#mobile_check").empty();
                    $("span#mobileAuthTempKey_checkMent").html("인증이 완료되었습니다.").css("color", "blue");
                    $("button#submit_mobile").removeAttr("disabled");
                }
                else{
                    alert("인증을 실패하였습니다. 다시 인증해주세요");
                    $("input#newMoblie").removeAttr("disabled");
                    $("input#newMoblie").focus();
                    $("span#mobile_check").empty();
                    $("div#mobile_authTemp").hide();
                    $("input#mobile_authTempKey").val("");
                    $("button#mobileAuthTempKey_check").removeAttr("disabled");
                    $("button#submit_mobile").attr("disabled", true);
                    // $("span#mobileAuthTempKey_checkMent").html("인증을 실패하였습니다. 다시 인증해주세요").css("color", "red");
                }
                
            },
            
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });
    });

    // 취소버튼 클릭 시
    $("button#mobilecancle").click(()=>{
        $("tr#mobile_area").show();
        $("tr#change_mobile_area").hide();
        $("input#newMoblie").removeAttr("disabled");
        $("button#submit_mobile").attr("disabled", true);

    });


    // === 전화번호 관련 내용 끝 === //




    // === 주소 관련 내용 시작 === //
    // 주소 변경 버튼 클릭시
    $("button#change_post").click(function(){
        $("tr#change_post_area").show();
        $("tr#post_area").hide();
    });

    // 취소 버튼 클릭시
    $("button#postcancle").click(function(){
        $("tr#change_post_area").hide();
        $("tr#post_area").show();
    });


    // 우편번호 찾기 버튼 클릭시
    $("button#findpost").click(function(){
        alert("주소찾기버튼누름");
		
		// 주소를 쓰기가능 으로 만들기
		$("input#postcode").removeAttr("readonly");
        
        // 참고항목을 쓰기가능 으로 만들기
        $("input#address").removeAttr("readonly");
        
        // 주소를 활성화 시키기
	//	$("input#address").removeAttr("disabled");
        
        // 참고항목을 활성화 시키기
    //  $("input#extraAddress").removeAttr("disabled");
		
		new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                let addr = ''; // 주소 변수
                let extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    // document.getElementById("extraAddress").value = extraAddr;
                
                } else {
                    // document.getElementById("extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode;
                
                document.getElementById("address").value = addr+extraAddr;

                document.getElementById("addrinput").value = addr;
                document.getElementById("extraAddrinput").value = extraAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("addressDetail").focus();
            }
        }).open();
        
        // 주소를 읽기전용(readonly) 로 만들기
        $("input#postcode").attr("readonly", true);
        
        // 참고항목을 읽기전용(readonly) 로 만들기
        $("input#address").attr("readonly", true);
        
        // 주소를 비활성화 로 만들기
    //  $("input#address").attr("disabled", true);
        
        // 참고항목을 비활성화 로 만들기
    //  $("input#extraAddress").attr("disabled", true);
        
	});// end of $("img#zipcodeSearch").click()------------
    
    


});// end of $(document).ready(function()------

// 여기부분은 온로드 안에 넣어버릴거임
// 다 안에 넣움

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 비밀번호 변경 완료 버튼 눌렀을 경우 호출되는 함수
function pwdUP(){

    const password = $("input#password").val().trim();  // 현재비밀번호 확인값
    const newPassword = $("input#newPassword").val().trim();    // 신규비밀번호 값
    const confirmPassword = $("input#confirmPassword").val().trim();    // 비밀번호 체크 값
        
/*
    if(password != currentpwd){ // 입력한 현재비밀번호와 현재비밀번호가 다를경우
        alert("비밀번호가 다릅니다.");
        $("span#pwd_wrong").html("비밀번호가 일치하지 않습니다.");
        $("input#password").val("");
        $("input#newPassword").val("");
        $("input#confirmPassword").val("");
        
        return; // 함수 종료
    }
*/
    // 현재비밀번호 입력한 값이 일치하는지 체크한 후 새로울 때만 변경 가능
    correctpwd = true;
    $.ajax({
        url:"pwdDuplicateCheck.flex",
        data:{"newpassword":$("input#password").val()
             ,"userid":$("td#userid").text()},
        type:"post",
        
        async:false, // 비동기 방식  
        
        dataType:"json", 
        
        success:function(json){
            
            if(json.isExists) {// 입력한 암호가 현재비밀번호가 일치하지 않는 경우
                alert("현재 비밀번호 와 일치하지 않습니다.");
                $("input#password").val("");
                $("input#newPassword").val("");
                $("input#confirmPassword").val("");
                $("span#pwd_wrong").html("현재가 일치하지 않습니다."); 
                correctpwd = false;
                return;
            }
            
        },
        
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });


    // 변경한 비밀번호가 기존에 사용한 비밀번호와 동일한지 체크한 후 새로울 때만 변경할 수 있도록 한다.
    isNewPwd = true;

    // ajax 로 준다음 체크한다.
    $.ajax({
        url:"pwdDuplicateCheck.flex",
        data:{"newpassword":$("input#newPassword").val()
            ,"userid":$("td#userid").text()},
        type:"post",
        
        async:false, // 비동기 방식  
        
        dataType:"json", 
        
        success:function(json){
            
            if(json.isExists) {// 입력한 암호가 이미 사용중
                alert("현재 사용중인 비밀번호로 비밀번호 변경은 불가합니다.");
                $("input#password").val("");
                $("input#newPassword").val("");
                $("input#confirmPassword").val("");
                $("span#pwd_wrong").html("현재 사용중인 비밀번호로 비밀번호 변경은 불가합니다."); 
                isNewPwd = false;
                return;
            }
            
        },
        
        error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
    });

    if(correctpwd && isNewPwd && password != "" && newPassword != "" && confirmPassword != "") { 
        // 변경한 암호가 새로운 암호일 경우이고, 값이 모두 작성된 상태라면
        alert("DB에 사용자 정보를 수정하러 간다.");
        
        const frm = document.pwdForm;
        frm.action = "memberInfoChangeEnd.flex";
        frm.method = "post";
        frm.submit();

    }

}// end of function pwdUP()




// 이메일 변경 완료 버튼 눌르면
function emailUP(){
    const newEmail = $("input:text[name='newEmail']").val().trim();
		
    if(newEmail == ""){
        alert("이메일이 올바르지 않습니다.");
        $("div#email_authTemp").hide();
        return;	// 종료
    }

    const Frm = document.emailForm;
    
    Frm.action = "memberInfoChangeEnd.flex";
    Frm.method = "post";
    Frm.submit();

}// end of function emailUP()----



function mobileUP(){
    const newMoblie = $("input:text[name='newMoblie']").val().trim();
		
    if(newMoblie == ""){
        alert("전화번호가 올바르지 않습니다.");
        $("div#mobile_authTemp").hide();
        return;	// 종료
    }

    const Frm = document.mobileForm;
    
    Frm.action = "memberInfoChangeEnd.flex";
    Frm.method = "post";
    Frm.submit();


}// end of function mobileUP()----





// 우편번호 변경 버튼 클릭 시
function postUP(){
    
    const postcode = $("input:text[name='postcode']").val().trim();
    const address = $("input:text[name='address']").val().trim();
    const addressDetail = $("input:text[name='addressDetail']").val().trim();

    console.log("확인용 postcode : "+postcode);
    console.log("확인용 address : "+address);
    console.log("확인용 addressDetail : "+addressDetail);

    if(postcode == "" && address == ""){
        alert("우편번호 찾기를 클릭하여 주소값을 입력하세요!!");
        return;
    }

    if(addressDetail == ""){
        alert("상세주소 값을 입력하세요.");
        $("input:text[name='addressDetail']").val("");
        return;
    }


    // 키값 정리
    // 우편번호 postcode
    // 도로명   addr
    // 구주소   extraAddr
    // 상세주소 addressDetail


    const Frm = document.postForm;
    
    Frm.action = "memberInfoChangeEnd.flex";
    Frm.method = "post";
    Frm.submit();

    
}// end of function postUP()