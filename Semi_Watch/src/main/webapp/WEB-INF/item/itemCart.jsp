<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../header1.jsp" />

<script type="text/javascript" src="<%= ctxPath%>/js/cart/Item_cart.js"></script>


<style>

.card {
    margin: auto;
    max-width: 100%;
    width: 90%;
    box-shadow: 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    border-radius: 1rem;
    border: 1px solid #ccc; /* 일정한 border 설정 */
}

@media (max-width: 767px) {
    .card {
        margin: 3vh auto;
    }
}

.cart {
    background-color: #fff;
    padding: 4vh 5vh;
    border-bottom-left-radius: 1rem;
    border-top-left-radius: 1rem;
}

@media (max-width: 767px) {
    .cart {
        padding: 4vh;
        border-bottom-left-radius: unset;
        border-top-right-radius: 1rem;
    }
}

.summary {
    background-color: #ddd;
    border-top-right-radius: 1rem;
    border-bottom-right-radius: 1rem;
    padding: 4vh;
    color: rgb(65, 65, 65);
}

@media (max-width: 767px) {
    .summary {
        border-top-right-radius: unset;
        border-bottom-left-radius: 1rem;
    }
}

.summary .col-2 {
    padding: 0;
}

.summary .col-10 {
    padding: 0;
}

.row {
    margin: 0;
}

.title b {
    font-size: 1.5rem;
}

.main {
    margin: 0;
    padding: 2vh 0;
    width: 100%;
}

.col-2, .col {
    padding: 0 1vh;
    vertical-align: middle;
}

a {
    padding: 0 1vh;
}

.close {
    margin-left: auto;
    font-size: 0.7rem;
}

.back-to-shop {
    margin-top: 4.5rem;
}

h5 {
    margin-top: 4vh;
}

hr {
    margin-top: 1.25rem;
}

form {
    padding: 2vh 0;
}

select {
    border: 1px solid rgba(0, 0, 0, 0.137);
    padding: 1.5vh 1vh;
    margin-bottom: 4vh;
    outline: none;
    width: 100%;
    background-color: rgb(247, 247, 247);
}

input {
    border: 1px solid rgba(0, 0, 0, 0.137);
    padding: 1vh;
    margin-bottom: 4vh;
    outline: none;
    width: 100%;
    background-color: rgb(247, 247, 247);
}

input:focus::-webkit-input-placeholder {
    color: transparent;
}

.btn {
    background-color: #000;
    border-color: #000;
    color: white;
    width: 100%;
    font-size: 0.7rem;
    margin-top: 4vh;
    padding: 1vh;
    border-radius: 0;
}

.btn:focus {
    box-shadow: none;
    outline: none;
    color: white;
}

.btn:hover {
    color: white;
}

a {
    color: black;
}

a:hover {
    color: black;
    text-decoration: none;
}

#code {
    background-image: linear-gradient(to left, rgba(255, 255, 255, 0.253), rgba(255, 255, 255, 0.185)), url("https://img.icons8.com/small/16/000000/long-arrow-right.png");
    background-repeat: no-repeat;
    background-position-x: 95%;
    background-position-y: center;
}

#led {
    width: 50px;
}

.col {
    position: relative;
}

.fixed-button {
    position: absolute;
    right: 40%;
    top: 0;
}

.quantity-input {
    width: 30%;
}
</style>

<script type="text/javascript">

$(document).ready(function(){
	
	
	
	
	// 제품번호의 모든 체크박스가 체크가 되었다가 그 중 하나만 이라도 체크를 해제하면 전체선택 체크박스에도 체크를 해제하도록 한다. 
	  $("input:checkbox[name='pdno']").click(function(){
		 
		  let bFlag = false; // 전체선택버튼을 체크하기 위한 표식
		  
		  $("input:checkbox[name='pdno']").each(function (index, elmt){
			  
		  	  const is_checked = $(elmt).prop("checked");
		  	  
		  	  if(!is_checked){
		  		  
		  	     $("input:checkbox[id='allCheckOrNone']").prop("checked", false);
		  	     
		  	     bFlag = true;
		  	     return false;
		  	  	
		  	  }
		  	  
		  });
		  
		  if(!bFlag){
			  
			  $("input:checkbox[id='allCheckOrNone']").prop("checked", true);
		  
		  }
		  
		  
	  }); // end of $("input:checkbox[name='pnum']").click(function(){   }
	
	
	
	
	
	
	
	
	
	
	
});
	  
	  
//=== 장바구니에서 제품 주문하기 === // 	
function goOrder(){
	
//// === 체크박스의 체크된 개수(checked 속성이용) === ////
	const checkCnt = $("input:checkbox(name='pdno'):checked").length;
	// 체크박스들 중에서 체크드 된 갯수 알아오기
	
	if(checkCnt < 1){
		
		alert('주문하실 제품을 선택하세요!!');
		return;
	}
	else{
		//// === 체크박스의 체크된 value값(checked 속성이용) === ////
		//// === 체크가 된 것만 읽어와서 배열에 넣어준다. === ////
	
		const allCnt = $("input:checkbox(name='pdno)").length;
		// 모든 체크박스갯수 세어오기
		
		const pdnoArr = new Array();        // 또는 const pnumArr = [];
	    const oqtyArr = new Array();        // 또는 const oqtyArr = [];
	    const pqtyArr = new Array();        // 또는 const pqtyArr = [];
	    const cartnoArr = new Array();
	    const pd_detailnoArr = new Array();		// 또는 const cartnoArr = [];
	    const totalPriceArr = new Array();  // 또는 const totalPriceArr = [];
	    const totalPointArr = new Array();  // 또는 const totalPointArr = [];
		
	   	for(let i=0; i< allCnt; i++){
	   		
	   		if( $("input:checkbox[name='pdno']").eq(i).prop("checked") ){
	   			
	   			
	   			console.log("제품번호 : " , $("input:checkbox[name='pdno']").eq(i).val() ); 
	            console.log("주문량 : " ,  $("input.oqty").eq(i).val() );
	            console.log("잔고량 : " ,  $("input.pqty").eq(i).val() );
	            console.log("삭제해야할 장바구니 번호 : " , $("input.cartno").eq(i).val() ); 
	            console.log("참조할 상품상세기본키 : " , $("input.pd_detailno").eq(i).val() ); 
	            console.log("주문한 제품의 개수에 따른 가격합계 : " , $("input.totalPrice").eq(i).val() );
	            console.log("주문한 제품의 개수에 따른 포인트합계 : " , $("input.totalPoint").eq(i).val() );
	            console.log("======================================");
	   			
	   			
	   			pdnoArr.push($("input:checkbox[name='pdno']").eq(i).val());
	   			oqtyArr.push($("input.oqty").eq(i).val());
	   			pqtyArr.push($("input.pqty").eq(i).val());
	   			cartnoArr.push($("input.cartno").eq(i).val());
	   			pd_detailnoArr.push($("input.pd_detailno").eq(i).val());
	   			totalPriceArr.push($("input.totalPrice").eq(i).val());
	   			totalPointArr.push($("input.totalPoint").eq(i).val());
	   			
	   		} // end of if 체크된 상품정보 읽어오기 for문이라 가능
	   		
	   	} // end of for

	   	
	   	for(let i=0; i<checkCnt; i++) {
	   		
	   		if( Number(pqtyArr[i]) < Number(oqtyArr[i]) ){
	   		// 주문할 제품중 아무거나 하나가 잔고량이 주문량 보다 적을 경우
	            
	            alert("제품번호 "+ pdnoArr[i] +" 의 주문개수가 잔고개수 보다 더 커서 진행할 수 없습니다.");
	            location.href="javascript:history.go(0)";
	            return; // goOrder 함수 종료	
	   			
	   		}
	   		
	   	} // end of for
	   	
	   	// 배열을 하나의 문자열로 바꿔준다
	   	const str_pdno = pdnoArr.join(",");
	   	const str_oqty = oqtyArr.join(",");
	    const str_cartno = cartnoArr.join(",");
	    const str_pd_detailno = pd_detailnoArr.join(",");
	    const str_totalPrice = totalPriceArr.join(",");
	    
	    
	    
	    const str_totalPoint = totalPointArr.join(",");
	   	
	    let n_sum_totalPoint = 0;
	    
	    for(let i=0; i<totalPointArr.length; i++){
	    	
	    	n_sum_totalPoint += Number(totalPointArr[i]);
	    	
	    } // end of for totalpoint
	    
	    
	    console.log("확인용 str_pdno : ", str_pdno);                 // 확인용 str_pnum :  4,36,3
	    console.log("확인용 str_oqty : ", str_oqty);                 // 확인용 str_oqty :  2,2,5
	    console.log("확인용 str_cartno : ", str_cartno);             // 확인용 str_cartno :  6,4,3
	    console.log("확인용 str_pd_detailno : ", str_pd_detailno);     // 확인용 str_totalPrice :  26000,2000000,500
	    console.log("확인용 str_totalPrice : ", str_totalPrice);     // 제품별구매총액 salprice * 수량  확인용 str_totalPrice :  26000,2000000,50000
	    
	   
	    // 얘만
	    console.log("확인용 n_sum_totalPoint : ", n_sum_totalPoint); // 확인용 n_sum_totalPoint :  165  // 수량별 상품적립포인트합 (point * 장바구니수량)
		
	    
	    const frm = document.hidden;
	    
	    frm.action = "<%= ctxPath%>/order/checkOut.flex";
	    frm.method = "post";
	    
	    frm.submit();

  
	} // end of else
	
} // end of function goOrder(){ 



//전체선택박스 선택하면 나머지 전체 체크, 눌러서해제되면 전체 해제
function allCheckBox(){
	
	const bool = $("input:checkbox[id='allCheckOrNone']").is(":checked");
	// .prop("checked") or  .is(":checked") 를 쓰면 체크되었으면 true 아님 false 가 나온다.
	
	/*
    $("input:checkbox[id='allCheckOrNone']").is(":checked"); 은
      선택자 $("input:checkbox[id='allCheckOrNone']") 이 체크되어지면 true를 나타내고,
      선택자 $("input:checkbox[id='allCheckOrNone']") 이 체크가 해제되어지면 false를 나타내어주는 것이다.
 	*/
	
 	$("input:checkbox(name='pdno')").prop("checked", bool);
    // 전체선택 체크박스결과에따라 나머지 전부 일치시켜줌

} // end of function allCheckBox(){


</script>


<div class="card w-70 pt-3 pb-3 mt-5 mb-5" style="border-radius:10px;">
    <div class="row">
        <div class="col-md-8 cart">
            <div class="title">
                <div class="row">
                    <div class="col"><h4><b>장바구니</b></h4></div>
                    <c:set var="cartListSize" value="${fn:length(requestScope.cartList)}" />
                    <div class="col align-self-center text-right text-muted">총 ${cartListSize}건</div>
                </div>
                <div class="col-1" style="margin: 5% 0 0 0;">
		             <input type="checkbox" id="allCheckOrNone" class="item-checkbox" onclick="allCheckBox()">
		             <span style="font-size: 10pt;"><label for="allCheckOrNone">전체선택</label></span>
		        </div>
            </div>    
            <div class="row border-bottom">
                <div class="row  flex-column">
                	<%--  <c:set var="sumAmount" value="${sum_amount}" /> --%>
                	<c:if test="${not empty requestScope.cartList}">
                	<c:forEach var="cart" items="${requestScope.cartList}" varStatus="status">
                		<div class="mb-1 mt-1 pt-5 pb-5" style="display: flex; border-top: 1px solid #ccc; align-items: center;">
		                	<div class="col-1">
		                		<input type="checkbox" class="item-checkbox" name="pdno" id="pdno${status.count}" value="${cart.prod.pdno}" />&nbsp;<label class="label_pnum" for="pdno${status.count}">${cartv.prod.pdno}</label>
		                	</div>	
		                	
		                  	<div class="col-2">
		                  		<a href="<%= ctxPath%>/item/itemDetail.flex?pdno=${cart.prod.pdno}">
		                  	  	<img class="img-fluid" src="<%= ctxPath%>/images/product/${cart.prod.pdimg1}" />
		                  	  	</a>
		                  	</div>
		                    <div class="col-3">
		                        <span class="row text-muted">${cart.prod.brand}</span>
		                        <a href="<%= ctxPath%>/item/itemDetail.flex?pdno=${cart.prod.pdno}">
		                        <span class="">${cart.prod.pdname}</span>
		                        </a>
		                    </div>
		                    <div class="col-3 pt-5">
		                    
		                        <input type="number" min="1" max="50" value="1" class="quantity-input led oqty" size="1">
		                         <%-- 잔고량(남은재고량) --%>
		                         <p>남은재고</p><input type="text" class="pqty" value="${cart.pdvo.pd_qty}" />
                            	
                            
                            <%-- 장바구니 테이블에서 특정제품의 현재주문수량을 변경(sql update)하여 적용하려면 먼저 장바구니번호(시퀀스이며 기본키)를 알아야 한다 --%>
                            <p>장바구니기본키</p><input type="text" class="cartno" value="${cart.cartno}" />
                            <p>상품상세기본키</p><input type="text" class="pd_detailno" value="${cart.pdvo.pd_detailno}" />
		                    </div>
		                    <div class="col">
		                    	₩<span id ="item-price"><fmt:formatNumber value="${cart.prod.saleprice}" pattern="###,###" /></span>
		                        <button class="fixed-button">&#10005;</button>
		                        <div style="display : none;" id = "danga">${cart.prod.saleprice}</div>
		                    </div>
	                    </div>
                  	</c:forEach>
                  	</c:if>
                  	<c:if test="${empty requestScope.cartList}">
                  	
                  	<div>장바구니에 담긴 상품이 없습니다.</div>
                  	</c:if>
                </div>
            </div>
            <div class="back-to-shop"><a href="/Semi_Watch/item/itemList.flex">&leftarrow;</a>
            <a href="/Semi_Watch/item/itemList.flex" class="text-muted">쇼핑으로 돌아가기</a></div>
        </div>
        <div class="col-md-4 summary">
            <div><h5><b>결제 예정금액</b></h5></div>
            <hr>
            <div class="row">
                <div class="col" style="padding-left:0;">총 건</div>
                <div class ="col">₩<span class="col text-right" id="sumPrice">0</span></div>
            </div>
            <%-- <form>
                <p>배송비</p>
                <div id="shippingPay"><option class="text-muted">기본 배송비 - ₩3,000</option></div>
               <!--  <p>GIVE CODE</p>
                <input id="code" placeholder="Enter your code"> -->
            </form>--%>
            <div class="row" style="border-top: 1px solid rgba(0,0,0,.1); padding: 2vh 0;">
                <div class="col" id="deliveryPrice">결제 금액</div>
                <div class ="col">₩<span class="col text-right" id="sumPrice">0</span></div>
            </div>
            <button class="btn" id="goOrder" onclick="goOrder()")>주문하기</button>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>  



