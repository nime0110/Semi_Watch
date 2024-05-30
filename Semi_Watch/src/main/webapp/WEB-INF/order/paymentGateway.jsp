<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
	// MyMVC
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.2.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	
	<%-- 이 내용은 결제가 완료되었는지 확인하는 표식이다. --%>
	let isPaymentcheck = false; 
	
	if(window.opener && !window.opener.closed){
		
		
		<%-- 여기에는 제품 총가격이랑, 제품명? 복수개 일 경우 처음제품명+ '외' 몇건? 또는 그냥 외만 표시 --%>
		// <input type="hidden" id="totalPrice" value="${requestScope.totalPrice}"/>
        const paymentTotalcost = $(window.opener.document).find("input#totalPrice").val();
        const odrCount = $(window.opener.document).find("div[name='pInfo']").length;
        
        // alert("확인용 제품갯수 : "+odrCount);
		// 확인용 제품개수 : 2
		
		const firstpname = $(window.opener.document).find("span#pname1").text();
		// alert("확인용 첫번째 제품명 : "+firstpname);
		
		let odrPname = firstpname;
		//alert("확인용 첫번째 제품명 : "+odrPname);
		
		if(odrCount > 1){
			odrPname = firstpname+" 외 "+Number(odrCount-1);
			//alert("확인용 첫번째 제품명 : "+odrPname);
		}
		
		
	
		//	여기 링크를 꼭 참고하세용 http://www.iamport.kr/getstarted
	   	var IMP = window.IMP;     // 생략가능
	   	IMP.init('imp30055488');  // 중요!!  아임포트에 가입시 부여받은 "가맹점 식별코드".
		
	   	// 결제요청하기
	   	IMP.request_pay({
		    pg : 'html5_inicis', // 결제방식 PG사 구분
		    pay_method : 'card',	// 결제 수단
		    merchant_uid : 'merchant_' + new Date().getTime(), // 가맹점에서 생성/관리하는 고유 주문번호
		    name : odrPname,	 // 상품명 또는 코인충전 또는 order 테이블에 들어갈 주문명 혹은 주문 번호. (선택항목)원활한 결제정보 확인을 위해 입력 권장(PG사 마다 차이가 있지만) 16자 이내로 작성하기를 권장
		    amount : 100,	  // *** '${coinmoney}'  결제 금액 number 타입. 필수항목. 
		    buyer_email : '${sessionScope.loginuser.email}',  // 구매자 email
		    buyer_name : '${sessionScope.loginuser.username}',	  // 구매자 이름 
		    buyer_tel : '${sessionScope.loginuser.mobile}',    // 구매자 전화번호 (필수항목)
		    buyer_addr : '',  
		    buyer_postcode : '',
		    m_redirect_url : ''  // 휴대폰 사용시 결제 완료 후 action : 컨트롤러로 보내서 자체 db에 입력시킬것!
	   	}, function(rsp) {
    	/*
		   if ( rsp.success ) {
			   var msg = '결제가 완료되었습니다.';
			   msg += '고유ID : ' + rsp.imp_uid;
			   msg += '상점 거래ID : ' + rsp.merchant_uid;
			   msg += '결제 금액 : ' + rsp.paid_amount;
			   msg += '카드 승인번호 : ' + rsp.apply_num;
		   } else {
			   var msg = '결제에 실패하였습니다.';
			   msg += '에러내용 : ' + rsp.error_msg;
		   }
		   alert(msg);
		*/
	
			if ( rsp.success ) { // PC 데스크탑용
			/* === 팝업창에서 부모창 함수 호출 방법 3가지 ===
			    1-1. 일반적인 방법
				opener.location.href = "javascript:부모창스크립트 함수명();";
				opener.location.href = "http://www.aaa.com";
				
				1-2. 일반적인 방법
				window.opener.부모창스크립트 함수명();
		
				2. jQuery를 이용한 방법
				$(opener.location).attr("href", "javascript:부모창스크립트 함수명();");
			*/
			//	아래 셋다 같은거다 . 문법만 다름
			//	opener.location.href = "javascript:goCoinUpdate('ctxPath 값','${idx}','${coinmoney}');";
			
				isPaymentcheck = true;
				// 부모창에 있는 함수를 불러온다.
				window.opener.checkOutUpdate('<%= ctxPath%>','${requestScope.userid}', isPaymentcheck);
				
				// 넘겨준 userid 와 충전할 금액을 함수에 넣어서 호출한다.
			//  $(opener.location).attr("href", "javascript:goCoinUpdate('ctxPath 값','${idx}','${coinmoney}');");
				
			    self.close();
				
       		} else {
	            	
	            alert("결제에 실패하였습니다.");
	            self.close();
	       	}
		
	   	}); // end of IMP.request_pay()----------------------------
	   
	   
	}// end of if (window.opener && !window.opener.closed)------------
	else{ // 부모창이 꺼지던가, 로그인이 안된 경우
		alert("비정상적인 이동경로 입니다.");
		self.close();
	}

	
}); // end of $(document).ready()-----------------------------

</script>
</head>	

<body>
</body>
</html>
