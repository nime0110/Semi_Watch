
let right_regExp = false;

$(document).ready(function(){
	
	$("span.error").hide();
	$("input#pwd").focus();
	

	// 비밀번호
	$("input#pwd").blur( (e) => { 

     // const regExp_pwd = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g; 
     // 또는
        const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g); 
        // 숫자/문자/특수문자 포함 형태의 8~15자리 이내의 암호 정규표현식 객체 생성
        
        const bool = regExp_pwd.test($(e.target).val());

        if(!bool) {
            // 암호가 정규표현식에 위배된 경우 
            $(e.target).parent().parent().find("span.error").show();
			$(e.target).val("").focus();
        }
        else {
            // 암호가 정규표현식에 맞는 경우
            right_regExp = true; 
            $(e.target).parent().parent().find("span.error").hide();
            
        }

    });// 비밀번호 포커스를 잃었을 경우 이벤트
    
    
    // 비밀번호 확인
    $("input#pwdcheck").blur( (e) =>{
        
        if($("input#pwd").val() != $(e.target).val()){
           // 암호와 암호확인값이 다른 경우
           $(e.target).parent().parent().find("span.error").show();

        }

        else{
            // 암호와 암호확인값이 같은 경우
            $(e.target).parent().parent().find("span.error").hide();
        }


    }); // 비밀번호확인 포커스를 잃었을 경우 이벤트
    	
	
});// end of $(document).ready(function()



// Function Declaration
// "가입하기" 버튼 클릭시 호출되는 함수

function goEdit() {

    // *** 수정정보가 모두 입력이 되었는지 검사하기 시작 *** //
    let pw_changeinfo = true;
    

    const pwd = $("input#pwd").val().trim();
    if(pwd == ""){
        alert("비밀번호가 입력되지 않았습니다.");
        pw_changeinfo = false;
    }
    
    const pwdcheck = $("input#pwdcheck").val().trim();
    if(pwdcheck == ""){
        alert("비밀번호확인이 입력되지 않았습니다.");
        pw_changeinfo = false;
    }    

	if(pwd != pwdcheck){
		alert("비밀번호와 비밀번호확인이 동일하지 않습니다.");
		pw_changeinfo = false;
	}
	
    if(!pw_changeinfo){
        return; // goEdit() 함수를 종료한다.
    }
    
    if(!right_regExp){
		alert("정규표현식에 맞지 않는 비밀번호입니다.")
		return; // goEdit() 함수를 종료한다.
	}
	
    // *** 새로운 비밀번호 검사하기 끝 *** //

	/////////////////////////////////////////////////	
	let isNewPwd = true;
	
	// const userid = $("input#userid").val();
	// alert("확인용 : "+ userid);
	
	$.ajax({
		url:"pwdDuplicateCheck_edit.flex",
		data:{"password":$("input#pwd").val(),
			  "userid": $("input#userid").val() },
		type:"post", // type 생략시 type:"get" 디폴트
		async:false, // async:false 동기방식, 디폴트는 true(비동기방식)
		dataType:"json",
		
		success:function(json){
			
			if(json.isExists){
				// 입력한 암호가 이미 사용중이라면
				$("span#duplicate_pwd").html("현재 사용중인 비밀번호로 변경하는 것은 불가합니다.");
				isNewPwd = false;
			}
			
			
		},
		
		error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
		
	});
	/////////////////////////////////////////////////////////////////	
	
	if(isNewPwd){ // 변경한 암호가 새로운 암호일 경우
	    const frm = document.editPwdFrm;
	    frm.action = "memberPwdChange.flex";
	    frm.method = "post";
	    frm.submit();           	
	}
	
};// function goEdit()


function goReset(){
    
    $("span.error").hide();

    $("input#pwd").empty();
    $("input#pwdcheck").empty();
    
    $("span#duplicate_pwd").html("");
    
    $("input#pwd").focus();
}// end of function goReset() 
