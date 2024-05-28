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
	if (window.opener && !window.opener.closed) {
        const totalCostpage = $(window.opener.document).find("input#totalCost").val();
        
		alert(totalCostpage);
	
	
	//	여기 링크를 꼭 참고하세용 http://www.iamport.kr/getstarted
   var IMP = window.IMP;     // 생략가능
   IMP.init('imp30055488');  // 중요!!  아임포트에 가입시 부여받은 "가맹점 식별코드". 
	
   // 결제요청하기
   IMP.request_pay({
       pg : 'html5_inicis', // 결제방식 PG사 구분
       pay_method : 'card',	// 결제 수단
       merchant_uid : 'merchant_' + new Date().getTime(), // 가맹점에서 생성/관리하는 고유 주문번호
       name : '테스트상품명',	 // 상품명 또는 코인충전 또는 order 테이블에 들어갈 주문명 혹은 주문 번호. (선택항목)원활한 결제정보 확인을 위해 입력 권장(PG사 마다 차이가 있지만) 16자 이내로 작성하기를 권장
       amount : totalCostpage,	  // *** '${coinmoney}'  결제 금액 number 타입. 필수항목. 
       buyer_email : 'test@test.com',  // 구매자 email
       buyer_name : '홍길동',	  // 구매자 이름 
       buyer_tel : '01012345678',    // 구매자 전화번호 (필수항목)
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
			<%-- window.opener.goCoinUpdate('<%= ctxPath%>','${requestScope.userid}','${requestScope.productPrice}'); --%>
			// 넘겨준 userid 와 충전할 금액을 함수에 넣어서 호출한다.
		//  $(opener.location).attr("href", "javascript:goCoinUpdate('ctxPath 값','${idx}','${coinmoney}');");
			
		    self.close();
			
        } else {
            location.href="javascript:history.back()";	
            alert("결제에 실패하였습니다.");
            // self.close();
       }
	
   }); // end of IMP.request_pay()----------------------------
}

}); // end of $(document).ready()-----------------------------

</script>
</head>	

<body>
</body>
</html>
