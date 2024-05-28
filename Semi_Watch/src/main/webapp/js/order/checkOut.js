 
let iscomplite = true;  // 배송지정보 변경 시 완료버튼 눌렀는지 확인용

$(document).ready(function(){
	
	$("textarea").hide();   // 배송메시지 직접 입력 누르면 나오는거
    $("div#chageInfo").hide();  // 배송정보 수정구역
    $("div#shouldmsg").hide();
    

    // 이름 유효성 검사
    $("input#ch_name").blur( (e) => {
      
        //   const regExp_hp2 = /^[1-9][0-9]{3}$/;  
        //  또는
        const regExp_name = new RegExp( /^[가-힣a-zA-Z]+$/);  
        // 연락처 정규표현식 객체 생성 
        
        const bool = regExp_name.test($(e.target).val());
        
        if(!bool) {
            // 이름이 정규표현식에 위배된 경우 

            $("div.namemt").html("올바른 이름을 입력하세요.").css("color","red").show();
            $(e.target).val("");
            // $("button#send_authentication_mobile").attr("disabled", true);
            
             
        }
        else {
            $("div.namemt").html("");  // 멘트 초기화
            
        }
        
    });// 아이디가 hp2 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.


    // 전화번호 유효성 검사
    $("input#ch_mobile").blur( (e) => {
      
        //   const regExp_hp2 = /^[1-9][0-9]{3}$/;  
        //  또는
        const regExp_mobile = new RegExp(/^[0][1][0][0-9]{8}$/);  
        // 연락처 정규표현식 객체 생성 
        
        const bool = regExp_mobile.test($(e.target).val());
        
        if(!bool) {
            // 연락처 국번이 정규표현식에 위배된 경우 

            $("div.mobilemt").html("올바른 전화번호를 입력하세요.").css("color","red").show();
            $(e.target).val("");
            // $("button#send_authentication_mobile").attr("disabled", true);
            
             
        }
        else {
            $("div.mobilemt").html("");  // 전화번호 멘트 초기화
            
        }
        
    });// 아이디가 hp2 인 것은 포커스를 잃어버렸을 경우(blur) 이벤트를 처리해주는 것이다.


    // 주소 찾기 버튼 클릭 시
    $("button#postSearch").click(function(){
        alert("주소찾기버튼누름");
		
		// 주소를 쓰기가능 으로 만들기
		$("input#ch_postcode").removeAttr("disabled");
        
        // 주소명 쓰기가능 으로 만들기
        $("input#ch_address").removeAttr("disabled");
        
		
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
                document.getElementById('ch_postcode').value = data.zonecode;
                
                document.getElementById("ch_address").value = addr+" "+extraAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("ch_detailAddress").focus();
            }
        }).open();
        
        // 주소를 읽기전용(disabled) 로 만들기
        $("input#ch_postcode").attr("disabled", true);
        
        // 참고항목을 읽기전용(disabled) 로 만들기
        $("input#ch_address").attr("disabled", true);
        
        
	});// end of $("img#zipcodeSearch").click()------------

    // 상세주소 미입력 시
    $("input#ch_detailAddress").blur( (e) => {
      if($(e.target).val().trim() == ""){
        $("div.detailAddrmt").show();
        $(e.target).val("");
      }
      else{
        $("div.detailAddrmt").hide();
      }
        
        
    });// 주소찾기 이벤트 종료


    // 배송메시지 직접선택 시 입력창 뜨게하기
    $("select#requiremsg").bind("change", function(e){

        if($(e.target).val() == "직접입력"){
            $("textarea#comment").show();
        }
        else{
            $("textarea#comment").hide();
        }

    });// end of $("select#requiremsg").bind("change", function(e)----



    // === 마일리지 관련 내용 ===//
    // 처음에 보유 마일리지 출력
    const userPoint = $("input:hidden[name='userpoint']").val();  // 초기값
    $("span#userpoint").text(userPoint);    // 보유 값
    $("span#restpoint").text(userPoint);    // 사용가능 값 초기


    // 마일리지 사용하기
    $("#usePoint").on('input', function(e) {
        let usePoint = $(e.target).val();
        const reinput = usePoint.replace(/[^\d]/g, '').replace(/[\s]/g, ''); // 숫자 이외의 모든 문자 제거
        //const userPoint = $("input:text[name='userpoint']").val();

        // 한글 입력 방지
        if (usePoint != reinput) {
            usePoint = $(e.target).val(reinput); // 한글을 제거한 값으로 입력 필드를 업데이트

        }
        
        // 첫번째 글자가 0 이면 블랭크하도록
        if(usePoint.slice(0,1) == "0"){
            $("span#useEndPoint").empty();
            $(this).val("");
            usePoint = "";
            $("span#restpoint").text(userPoint);
            $("input:hidden[name='userpoint']").val(userPoint);
            
        }

        // 입력한 포인트가 보유포인트 보다 넘어갈 경우
        if(Number(usePoint) > Number(userPoint)){
            $("input#usePoint").val(userPoint); //사용 포인트 최대값으로
            $("span#restpoint").text("0");
            usePoint = userPoint;

        }

        // 보유마일리지 감소
        const restPoint = Number(userPoint)-Number(usePoint);

        if(restPoint < 0){
            $("input:hidden[name='userpoint']").val("0");
        }
        else if(isNaN(restPoint)){  // 타입이 숫자가 아니면
            $(this).val("");
            usePoint = "";
            $("span#restpoint").text(userPoint);
            $("input:hidden[name='userpoint']").val(userPoint);

        }
        else{
            $("input:hidden[name='userpoint']").val(restPoint);
            $("span#restpoint").text(restPoint);
            
        }

        // 마지막 사용포인트 값을 결제 영역에 넘겨준다.
        if(usePoint == "" || usePoint == "0"){
            $("span#useEndPoint").empty();
        }
        else{
            $("span#useEndPoint").html(`- ${usePoint}`);
            
        }
        

    });

    // 마일리지 모두사용 버튼 클릭 시
    $("button#allUsePoint").click(function(){
        $("input#usePoint").val(userPoint);
        $("input:hidden[name='userpoint']").val("0")  // 보유한 값을 0으로 만든다.
        $("span#restpoint").text("0");
        $("span#useEndPoint").html(`- ${userPoint}`);

    });
    // === 마일리지 관련 내용  끝 ===//




	
});	// end of $(document).ready(function() -------

// Function Declaration

 // 배송정보 수정버튼 클릭시
function gochange(){
    $("div#userInfo").hide()
    $("div#chageInfo").show();
    
    iscomplite = false;

    $("div#shouldmsg").hide();
    
}

// 배송정보 취소 버튼 클릭시
function gochange_cancel(){

    $("div#userInfo").show()
    $("div#chageInfo").hide();

    $("div#shouldmsg").hide();
    
    iscomplite = true;

}

// 배송정보 수정완료 버튼 클릭시
function gochange_complite(){

    const ch_name = $("input#ch_name").val().trim();
    const ch_mobile = $("input#ch_mobile").val().trim();
    const ch_postcode = $("input#ch_postcode").val().trim();
    const ch_address = $("input#ch_address").val().trim();
    const ch_detailAddress = $("input#ch_detailAddress").val().trim();

    if(ch_name==""){
        $("div.namemt").show();
        alert("이름을 입력하세요.");
        return;
    }
    if(ch_mobile==""){
        $("div.mobilemt").show();
        alert("전화번호을 입력하세요.");
        return;
    }
    if(ch_postcode==""){
        alert("주소검색 버튼을 클릭하여 주소값을 입력하세요.");
        return;
    }
    if(ch_detailAddress==""){
        $("div.detailAddrmt").show();
        alert("상세주소을 입력하세요.");
        return;
    }

    if(ch_name != "" && ch_mobile != "" && ch_postcode != "" && ch_address != "" && ch_detailAddress != ""){
        // 값을 모두 입력하였으면
        // form 으로 넘겨줄 저장소에 넣는다.
        $("input:hidden[name='name']").val(ch_name);
        $("input:hidden[name='mobile']").val(ch_mobile);
        $("input:hidden[name='postcode']").val(ch_postcode);
        $("input:hidden[name='address']").val(ch_address+ch_detailAddress);

        // 보여지는 곳을 바꾸어 준다.
        $("div#name").text(ch_name);
        $("div#mobile").text(ch_mobile);
        $("div#postcode").text(ch_postcode);
        $("div#address").text(ch_address+"  "+ch_detailAddress);
        
        // 값을 초기화
        $("#chageInfo input").val('');
       

        // 마지막 셋팅
        iscomplite = true;
        $("div#shouldmsg").hide();
        $("div#userInfo").show()
        $("div#chageInfo").hide();
    }

    
    

}// end of function gochange_complite()-----------------


// Function Declaration
function goCheckOutPayment(ctxPath, userid){

    // 가정 1 장바구니 또는 구매할 품목이 없는 경우
    // 상품리스트 페이지로 이동 또는 메인페이지로 이동


    // 결제창 띄우기
    // alert(`확인용 부모창의 함수 호출함\n path : ${ctxPath} \n 결제금액: ${coinmoney} \n 유저아이디 : ${userid}`);

    // 포트원(구 아임포트) 결제 팝업창 띄우기
    const url = `${ctxPath}/order/checkOutEnd.flex`;

    // 너비 1000, 높이 600 인 팝업창을 화면 가운데 위치시키기
    const width = 1000;
    const height = 600;

    const left = Math.ceil((window.screen.width - width)/2);   // (내모니터 화면 넓이 - 650)/2 , 예로 내모니터 화면 넓이가 1400 이면 650을 뺀 나머지 750 의 반 375 이다.
    // Math.ceil() 은 정수로 만드는 것이다.

    const top = Math.ceil((window.screen.height - height)/2);   // (내모니터 화면 높이 - 650)/2 , 예로 내모니터 화면 높이가 900 이면 570을 뺀 나머지 330 의 반 165 이다.
    // Math.ceil() 은 정수로 만드는 것이다.

    window.open(url, "checkOutEnd", `left=${left}, top=${top}, width=${width}, height=${height}`);
/*
    $.ajax({
        url:"checkOutEnd.flex",
            data:{"totalCost":$("span#totalCost").text()},

            type:"post",
            
            async:false, // 비동기 방식  
            
            dataType:"json", 
            
            success:function(json){

                if(json.response) {
                    alert("성공");

                }
                else {
                    alert("실패");
                }
                
            },
            error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }

    });
*/
}