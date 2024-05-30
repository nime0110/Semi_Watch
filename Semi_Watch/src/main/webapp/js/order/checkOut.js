 
let iscomplite = true;  // 배송지정보 변경 시 완료버튼 눌렀는지 확인용

$(document).ready(function(){
/*
    // 결제할 상품 없는데 들어올 경우
    const productCnt = $("div[name='pInfo']").length;
    	
    if(productCnt < 1){
       history:back(-1);
    }
*/	
	$("textarea").hide();   // 배송메시지 직접 입력 누르면 나오는거
    $("div#chageInfo").hide();  // 배송정보 수정구역
    $("div#shouldmsg").hide();

    
    // 처음에 주소값 보여지는거
    
    

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



    // 배송비
    const deliveryfee = $("input.deliveryfee").val();

    // 최종결제비용  총비용 구하기(상품별 총액)
    const totalPrice_ori = $("input#totalPrice").val(); // 초기값
    $("span#totalCostView").html(`${Number(totalPrice_ori-deliveryfee).toLocaleString('en')} 원`);
    $("span.totalPrice").html(`${Number(totalPrice_ori).toLocaleString('en')} 원`);


    // === 마일리지 관련 내용 ===//
    // 처음에 보유 마일리지 출력
    const userPoint = $("input:text[name='userpoint']").val();  // 초기값
    $("span#userpoint").text(Number(userPoint).toLocaleString('en'));    // 보유 값
    $("span#restpoint").text(Number(userPoint).toLocaleString('en'));    // 사용가능 값 초기


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
            $("input:text[name='useEndPointInput']").val("");
            $(this).val("");
            usePoint = "";
            $("span#restpoint").text(userPoint.toLocaleString('en'));
            $("input:text[name='userpoint']").val(userPoint);
            
        }

        // 입력한 포인트가 보유포인트 보다 넘어갈 경우
        if(Number(usePoint) > Number(userPoint)){
            $("input#usePoint").val(userPoint); //사용 포인트 최대값으로
            $("span#restpoint").text("0");

            usePoint = userPoint;

        }

        // 마일리지 사용비율 상품총금액에 80%만 가능하도록
        if(Number(usePoint) > Number(totalPrice_ori)*0.8 ){
            $("input#usePoint").val(Number(totalPrice_ori)*0.8); //사용 포인트 최대값으로
            // $("span#restpoint").text(`${Number(p_totalPrice)*0.8}`); 
            usePoint = Number(totalPrice_ori)*0.8;
            alert("마일리지는 상품금액에 80%만 사용가능합니다.");
        }



        // 보유마일리지 감소
        const restPoint = Number(userPoint)-Number(usePoint);

        if(restPoint < 0){
            $("input:text[name='userpoint']").val("0");
        }
        else if(isNaN(restPoint)){  // 타입이 숫자가 아니면
            $(this).val("");
            usePoint = "";
            $("span#restpoint").text(userPoint.toLocaleString('en'));
            $("input:text[name='userpoint']").val(userPoint);

        }
        else{
            $("input:text[name='userpoint']").val(restPoint);
            $("span#restpoint").text(restPoint.toLocaleString('en'));
            
        }

        // 마지막 사용포인트 값을 결제 영역에 넘겨준다.
        if(usePoint == "" || usePoint == "0"){
            $("span#useEndPoint").empty();
            $("input:text[name='useEndPointInput']").val("");

            $("span#totalCostView").html(`${Number(totalPrice_ori).toLocaleString('en')} 원`);
            $("input#totalPrice").val(totalPrice_ori);
        }
        else{
            $("span#useEndPoint").html(`- ${usePoint.toLocaleString('en')}`);
            $("input:text[name='useEndPointInput']").val(usePoint);

            $("span#totalCostView").html(`${(totalPrice_ori-usePoint).toLocaleString('en')} 원`);
            $("input#totalPrice").val(totalPrice_ori-usePoint);
        }
        

    });

    // 마일리지 모두사용 버튼 클릭 시
    $("button#allUsePoint").click(function(){
        if(Number(userPoint) > Number(totalPrice_ori)*0.8 ){
            $("input#usePoint").val(Number(totalPrice_ori)*0.8); //사용 포인트 최대값으로
            // $("span#restpoint").text(`${Number(p_totalPrice)*0.8}`); 
            const restPoint = Number(userPoint)-(Number(totalPrice_ori)*0.8);
            alert("마일리지는 상품금액에 80%만 사용가능합니다.");
            $("span#restpoint").text(restPoint.toLocaleString('en'));
            $("input:text[name='userpoint']").val(restPoint);
            $("span#useEndPoint").html(`- ${(userPoint-restPoint).toLocaleString('en')}`);

            $("input:text[name='useEndPointInput']").val(userPoint-restPoint);

            $("span#totalCostView").html(`${(totalPrice_ori-(totalPrice_ori*0.8)).toLocaleString('en')} 원`);
            $("input#totalPrice").val(totalPrice_ori-(totalPrice_ori*0.8));
        }
        else{
            $("input#usePoint").val(userPoint);
            $("input:text[name='userpoint']").val("0")  // 보유한 값을 0으로 만든다.
            $("span#restpoint").text("0");
            $("span#useEndPoint").html(`- ${userPoint.toLocaleString('en')}`);

            $("input:text[name='useEndPointInput']").val(userPoint);

            $("span#totalCostView").html(`${(totalPrice_ori-userPoint).toLocaleString('en')} 원`);
            $("input#totalPrice").val(totalPrice_ori-userPoint);
        }

        

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

    if($("select#requiremsg").val()=="배송시 요청사항을 선택해 주세요."){
        alert("배송메시지를 선택하세요.");
        return;
    }



    // 가정 1 장바구니 또는 구매할 품목이 없는 경우
    // 상품리스트 페이지로 이동 또는 메인페이지로 이동

    // 포트원(구 아임포트) 결제 팝업창 띄우기
    const url = `${ctxPath}/order/checkOutEnd.flex?userid=${userid}`;

    // 너비 1000, 높이 600 인 팝업창을 화면 가운데 위치시키기
    const width = 1000;
    const height = 600;

    const left = Math.ceil((window.screen.width - width)/2);   // (내모니터 화면 넓이 - 650)/2 , 예로 내모니터 화면 넓이가 1400 이면 650을 뺀 나머지 750 의 반 375 이다.
    // Math.ceil() 은 정수로 만드는 것이다.

    const top = Math.ceil((window.screen.height - height)/2);   // (내모니터 화면 높이 - 650)/2 , 예로 내모니터 화면 높이가 900 이면 570을 뺀 나머지 330 의 반 165 이다.
    // Math.ceil() 은 정수로 만드는 것이다.

    window.open(url, "결제창", `left=${left}, top=${top}, width=${width}, height=${height}`);

}// end of function goCheckOutPayment(ctxPath, userid)------



// 이거 뜯어고치기
// 결제창 성공하면 호출되는 함수
function checkOutUpdate(ctxPath, userid, paySuccess){

    console.log(paySuccess);
    // true

    // 결제가 성공했을 경우
    if(paySuccess){
        
        // 업데이트시 필요한내용 정리
    /*
        1. 주문자정보 => 로그인한 유저의 정보로 대체
        2. 배송정보 (수취인성명, 연락처, 우편코드, 주소명, 배송메세지)
        3. 제품정보 (제품번호, 제품상세번호, 옵션명, 주문수량)
        4. 결제정보 (결제금액, 포인트(적립포인트+(기존보유포인트-사용포인트)))
    */

        // 2. 배송정보
        const name = $("input:hidden[name='name']").val().trim();
        const email = $("input:hidden[name='email']").val().trim();
        const mobile = $("input:hidden[name='mobile']").val().trim();
        const postcode = $("input:hidden[name='postcode']").val().trim();
        const address = $("input:hidden[name='address']").val().trim();
        let deliverymsg = $("select#requiremsg").val();

        if(deliverymsg == "직접입력"){
            deliverymsg = $("textarea#comment").val().trim();
        }

        // 제품개수 알아오기
        const productCnt = $("div[name='pInfo']").length;

        // console.log("체크아웃 제품 개수 : "+productCnt);
        // 체크아웃 제품 개수 : 3

        // 3. 제품정보
        const pnumArr = new Array();        // 또는 const pnumArr = []; 가능    // pnum 배열
        const pdetailArr = new Array();     // 주문상세번호 배열
        const poptionArr = new Array();     // 주문옵션 배열
        const oqtyArr = new Array();        // 주문수량 배열
        const cartnoArr = new Array();      // 장바구니번호 배열
        const ptotalPriceArr = new Array(); // 제품별 제품가격총액 배열
        

        // 보여지는 상품만큼 반복
        for(let i=0; i<productCnt; i++){

            console.log("제품번호 : " , $("input.pnum").eq(i).val() );
            console.log("제품상세번호 : " , $("input.pdetail").eq(i).val() );
            console.log("제품옵션 : " , $("input.poption").eq(i).val() );
            console.log("주문량 : " ,  $("input.oqty").eq(i).val() );
            console.log("삭제해야할 장바구니 번호 : " , $("input.cartno").eq(i).val() ); 
            console.log("주문한 제품의 개수에 따른 가격합계 : " , $("input.ptotalPrice").eq(i).val() );
            console.log("======================================"); 
            
            pnumArr.push($("input.pnum").eq(i).val());
            pdetailArr.push($("input.pdetail").eq(i).val());
            poptionArr.push($("input.poption").eq(i).val());
            oqtyArr.push($("input.oqty").eq(i).val());
            cartnoArr.push($("input.cartno").eq(i).val());
            ptotalPriceArr.push($("input.ptotalPrice").eq(i).val());
                 
        }// end of for---------------------


        // 확인용
        for(let i=0; i<productCnt; i++) {
            console.log("확인용 제품번호: " + pnumArr[i] + ", 제품상세번호: " + pdetailArr[i] + ", 제품옵션: " + poptionArr[i] + ", 주문수량 : " + oqtyArr[i] + ", 장바구니번호: " + cartnoArr[i] + ", 제품별 가격총액: " + ptotalPriceArr[i]);
    
        }// end of for(let i=0; i<checkCnt; i++) {}-------------
        
        const str_pnum = pnumArr.join(); // 배열을 하나의 문자열변경
        const str_pdetail = pdetailArr.join();
        const str_poption = poptionArr.join();
        const str_oqty = oqtyArr.join();
        const str_cartno = cartnoArr.join();
        const str_ptotalPrice = ptotalPriceArr.join();

        // 4. 결제정보 (결제금액, 포인트(적립포인트+(기존보유포인트-사용포인트)))
        const paymentTotalPrice = $("input#totalPrice").val();
        const savePoint = Number($("input:hidden[name='pointSaveInput']").val());
        let useEndPointInput = $("input:text[name='useEndPointInput']").val();  // 사용포인트
        const userpoint = Number($("input:text[name='userpoint']").val()); // 사용하고 남은 포인트

        if(useEndPointInput == ""){
            useEndPointInput = 0;
        }
        else{
            useEndPointInput = Number(useEndPointInput);
        }

        console.log("확인용 savePoint : "+ savePoint);
        console.log("확인용 useEndPointInput : "+ useEndPointInput);
        console.log("확인용 userpoint : "+ userpoint );

    

        console.log("확인용 타입 : "+ typeof(useEndPointInput));

        const updatePoint = savePoint+userpoint-useEndPointInput;

        console.log("확인용 타입2 : "+typeof(updatePoint));

        console.log("확인용 업데이트포인트 : "+ Number(updatePoint));


        $.ajax({
            url:"checkOutUpdate.flex",
            type:"post",
            data:{"name" : name,
                  "mobile" : mobile,
                  "postcode":postcode,
                  "address":address,
                  "deliverymsg":deliverymsg,
                  "str_pnum_join":str_pnum, // 요거 쓸데가 없음
                  "str_pdetail_join":str_pdetail,
                  "str_poption_join":str_poption,
                  "str_oqty_join":str_oqty,
                  "str_cartno_join":str_cartno,
                  "str_ptotalPrice_join":str_ptotalPrice,
                  "paymentTotalPrice":paymentTotalPrice,
                  "updatePoint":updatePoint},
            dataType:"json",
            success:function(json){
                if(json.isSuccess == 1){	// {isSuccess:1} 또는 {isSuccess:0}
                    // 주문이 성공했으므로 주문목록을 보여준다.
                    //location.href="<%= ctxPath%>/shop/orderList.up";
                    alert("주문이 성공");
                }
                else{
                    // 주문이 실패할 경우
                    //location.href="<%= ctxPath%>/shop/orderError.up";
                    alert("주문이 실패");
                }
            },
            error: function(request, status, error){
                    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
        });// end of  $.ajax({

    }
    else{
        alert("결제 실패함");
        location.href="javascript:history.go(0)";
        return;
    }

    
    

    
    
    
    
    /*   

    
    const str_pnum = pnumArr.join(","); // 배열을 하나의 문자열로 만들자. default가 join(",")임. 그냥 join()해도 콤마로 연결됨
    const str_oqty = oqtyArr.join();
    const str_cartno = cartnoArr.join();
    const str_totalPrice = totalPriceArr.join();
    const str_totalPoint = totalPointArr.join();
    
    let n_sum_totalPrice = 0;
    for(let i=0; i<totalPriceArr.length; i++){
        n_sum_totalPrice += Number(totalPriceArr[i]);
    }// end of for---------------------------------
    
    let n_sum_totalPoint = 0;
    for(let i=0; i<totalPointArr.length; i++){
        n_sum_totalPoint += Number(totalPointArr[i]);
    }// end of for---------------
    /*	   
    console.log("확인용 str_pnum : ", str_pnum);                 // 확인용 str_pnum :  5,4,61
    console.log("확인용 str_oqty : ", str_oqty);                 // 확인용 str_oqty :  1,2,3
    console.log("확인용 str_cartno : ", str_cartno);             // 확인용 str_cartno :  11,8,7
    console.log("확인용 str_totalPrice : ", str_totalPrice);     // 확인용 str_totalPrice :  33000,26000,57000
    console.log("확인용 str_totalPoint : ", str_totalPoint);     // 확인용 str_totalPoint :  20,20,300
    console.log("확인용 n_sum_totalPrice : ", n_sum_totalPrice); // 확인용 n_sum_totalPrice :  116000
    console.log("확인용 n_sum_totalPoint : ", n_sum_totalPoint); // 확인용 n_sum_totalPoint :  340
    
        
    확인용 str_pnum :  62,3,35
    확인용 str_oqty :  3,5,2
    확인용 str_cartno :  6,4,2
    확인용 str_totalPrice :  30000,50000,2000000
    확인용 str_totalPoint :  30,25,120
    확인용 n_sum_totalPrice :  2080000
    확인용 n_sum_totalPoint :  175
    -- 원래 여기 있
    
    // const current_coin = ${sessionScope.loginuser.coin};
    
    if(current_coin < n_sum_totalPrice){
        $("p#order_error_msg").html("코인잔액이 부족하므로 주문이 불가합니다.<br>주문총액 : "+ n_sum_totalPrice.toLocaleString('en') +"원 / 코인잔액 : "+ current_coin.toLocaleString('en') +"원").css({'display':''}); 
        // 숫자.toLocaleString('en') 이 자바스크립트에서 숫자 3자리마다 콤마 찍어주기 이다.   
        return; // 종료
    }
    else{
        $("p#order_error_msg").css({'display':'none'}); // 코인이 충분할 경우 에러메시지를 보이지 않는다.
        
        if( confirm("총주문액 "+ n_sum_totalPrice.toLocaleString('en') + "원을 주문하시겠습니까?") ) {
            
            alert("내일 합니다.");
           	/*
            $.ajax{(
                url:"<%=ctxPath%>/shop/orderAdd.up",
                type:"post",
                data:{"n_sum_totalPrice" : n_sum_totalPrice,
                      "n_sum_totalPoint" : n_sum_totalPoint,
                      "str_pnum_join":str_pnum,
                      "str_oqty_join":str_oqty,
                      "str_totalPrice_join":str_totalPrice,
                      "str_cartno_join":str_cartno}, // 비워야 할 장바구니 번호
                dataType:"json",
                success:function(json){
                    
                },
                error: function(request, status, error){
                       alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                }
            )}
            ---원래 여기 있
            
        }
    }
    */

}// end of function checkOutUpdate(ctxPath, userid, paySuccess)-----