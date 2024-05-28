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

//전체선택박스 선택하면 나머지 전체 체크, 눌러서해제되면 전체 해제
function allCheckBox(){
	
	const bool = $("input:checkbox[id='allCheckOrNone']").is(":checked");
	// .prop("checked") or  .is(":checked") 를 쓰면 체크되었으면 true 아님 false 가 나온다.
	
	/*
    $("input:checkbox[id='allCheckOrNone']").is(":checked"); 은
      선택자 $("input:checkbox[id='allCheckOrNone']") 이 체크되어지면 true를 나타내고,
      선택자 $("input:checkbox[id='allCheckOrNone']") 이 체크가 해제되어지면 false를 나타내어주는 것이다.
 	*/
	
 	$("input:checkbox(name='pnum')").prop("checked", bool);
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
		                        <input type="number" min="1" max="5" value="1" class="quantity-input led" data-index="0" size="1">
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
            <button class="btn" id="go_Pay">주문하기</button>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>  




