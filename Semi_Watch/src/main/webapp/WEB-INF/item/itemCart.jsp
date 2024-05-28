<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../header1.jsp" />

<script type="text/javascript" src="<%= ctxPath%>/js/cart/Item_cart.js"></script>


<style>

.title{
    margin-bottom: 5vh;
}
.card{
    margin: auto;
    max-width: 100%;
    width: 90%;
    box-shadow: 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    border-radius: 1rem;
    border: transparent;
}

@media(max-width:767px){
    .card{
        margin: 3vh auto;
    }
}

.cart{
    background-color: #fff;
    padding: 4vh 5vh;
    border-bottom-left-radius: 1rem;
    border-top-left-radius: 1rem;
} 

@media(max-width:767px){
    .cart{
        padding: 4vh;
        border-bottom-left-radius: unset;
        border-top-right-radius: 1rem;
    }
}

.summary{
    background-color: #ddd;
    border-top-right-radius: 1rem;
    border-bottom-right-radius: 1rem;
    padding: 4vh;
    color: rgb(65, 65, 65);
} 

@media(max-width:767px){
    .summary{
    border-top-right-radius: unset;
    border-bottom-left-radius: 1rem;
    }
}

.summary .col-2{
    padding: 0;
}

.summary .col-10
{
    padding: 0;
}.row{
    margin: 0;
}
.title b{
    font-size: 1.5rem;
}
.main{
    margin: 0;
    padding: 2vh 0;
    width: 100%;
}
.col-2, .col{
   	padding: 0 1vh;
    vertical-align: middle;
}
a{
    padding: 0 1vh;
}
.close{
    margin-left: auto;
    font-size: 0.7rem;
}

.back-to-shop{
    margin-top: 4.5rem;
}
h5{
    margin-top: 4vh;
}
hr{
    margin-top: 1.25rem;
}
form{
    padding: 2vh 0;
}
select{
    border: 1px solid rgba(0, 0, 0, 0.137);
    padding: 1.5vh 1vh;
    margin-bottom: 4vh;
    outline: none;
    width: 100%;
    background-color: rgb(247, 247, 247);
}
input{
    border: 1px solid rgba(0, 0, 0, 0.137);
    padding: 1vh;
    margin-bottom: 4vh;
    outline: none;
    width: 100%;
    background-color: rgb(247, 247, 247);
}
input:focus::-webkit-input-placeholder
{
      color:transparent;
}
.btn{
    background-color: #000;
    border-color: #000;
    color: white;
    width: 100%;
    font-size: 0.7rem;
    margin-top: 4vh;
    padding: 1vh;
    border-radius: 0;
}
.btn:focus{
    box-shadow: none;
    outline: none;
    box-shadow: none;
    color: white;
    -webkit-box-shadow: none;
    -webkit-user-select: none;
    transition: none; 
}
.btn:hover{
    color: white;
}

a{
    color: black; 
}

a:hover{
    color: black;
    text-decoration: none;
}

#code{
    background-image: linear-gradient(to left, rgba(255, 255, 255, 0.253) , rgba(255, 255, 255, 0.185)), url("https://img.icons8.com/small/16/000000/long-arrow-right.png");
    background-repeat: no-repeat;
    background-position-x: 95%;
    background-position-y: center;
}
 #led {
    width: 50px; /* 이 값을 조정하여 칸의 크기를 조절할 수 있습니다. */
    
  }
  
  .col{
  	position: relative;
  }
	
  .fixed-button{
  	position: absolute;
  	right: 40%;
  	top: 0;
  	
  }
  
/*   body > div.card.w-70.pt-3.pb-3.mt-5.mb-5 > div:nth-child(1) > div > div.row.border-top.border-bottom > div{
  	border: solid 1px black;
  	
  } 
*/
  
/*   
	body > div.card.w-70.pt-3.pb-3.mt-5.mb-5 > div:nth-child(1) > div > div.row.border-top.border-bottom > div > div.col > span{
  	border: solid 1px red;
  	width: 50%;
  } 
*/
  
.quantity-input {
	width: 30%;
}  
  
</style>

<div class="card w-70 pt-3 pb-3 mt-5 mb-5" style="border-radius:10px;">
    <div class="row">
        <div class="col-md-8 cart">
            <div class="title">
                <div class="row">
                    <div class="col"><h4><b>장바구니</b></h4></div>
                    <div class="col align-self-center text-right text-muted">3 items</div>
                </div>
            </div>    
            <div class="row border-bottom">
                <div class="row align-items-center flex-column">
                	<%--  <c:set var="sumAmount" value="${sum_amount}" /> --%>
                	<c:forEach var="cvo" items="${requestScope.cartList}" varStatus="">
                		<div class="mb-1 mt-1 pt-5 pb-5" style="display: flex; border-top: 1px solid #ccc; align-items: center;">
		                	<div class="col-1">
		                		<input type="checkbox" class="item-checkbox" data-index="0">
		                	</div>	
		                	
		                  	<div class="col-2">
		                  	  	<img class="img-fluid" src="<%= ctxPath%>/images/product/product_thum/${cvo.prod.pdimg1}" />
		                  	</div>
		                    <div class="col-3">
		                        <span class="row text-muted">${cvo.prod.brand}</span>
		                        <span class="">${cvo.prod.pdname}</span>
		                    </div>
		                    <div class="col-3 pt-5">
		                        <input type="number" min="1" max="5" value="1" class="quantity-input led" data-index="0" size="1">
		                    </div>
		                    <div class="col">
		                    	₩<span id ="item-price"><fmt:formatNumber value="${cvo.prod.saleprice}" pattern="###,###" /></span>
		                        <button class="fixed-button">&#10005;</button>
		                        <div style="display : none;" id = "danga">${cvo.prod.saleprice}</div>
		                    </div>
	                    </div>
                  	</c:forEach>
                </div>
            </div>
            <div class="back-to-shop"><a href="/Semi_Watch/item/itemList.flex">&leftarrow;</a>
            <a href="/Semi_Watch/item/itemList.flex" class="text-muted">쇼핑으로 돌아가기</a></div>
        </div>
        <div class="col-md-4 summary">
            <div><h5><b>결제 예정금액</b></h5></div>
            <hr>
            <div class="row">
                <div class="col" style="padding-left:0;">ITEMS 3</div>
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




