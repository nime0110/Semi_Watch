<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../header1.jsp" />

<%

	String ctxPath = request.getContextPath();
%>


<script type="text/javascript" src="<%= ctxPath%>/js/Item/Item_cart.js"></script>


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
            <div class="row border-top border-bottom">
                <div class="row main align-items-center">
                	<div class="col-1">
                		<input type="checkbox" class="item-checkbox" data-index="0">
                	</div>	
                  	  <div class="col-2">
                  	  	<img class="img-fluid" src="<%= ctxPath%>/images/product/product_thum/1_thum.png" />
                  	  </div>
                    <div class="col">
                        <div class="row text-muted">${requestScope.ProductList.brand}</div>
                        <div class="row">${requestScope.ProductList.pdname}</div>
                    </div>
                    <div class="col">
                        <input type="number" min="0" max="20" value="0" class="quantity-input" data-index="0" size="1" id="led">
                    </div>
                     <div class="col">₩<span class="item-price" id="price1">0.00</span>
                     <button class="fixed-button">&#10005;</button></div>
                </div>
            </div>
            <div class="row">
                <div class="row main align-items-center">
                <div class="col-1">
                		<input type="checkbox" class="item-checkbox" data-index="0">
                	</div>	
                    <div class="col-2"><img class="img-fluid" src="https://i.imgur.com/ba3tvGm.jpg"></div>
                    <div class="col">
                        <div class="row text-muted">${requestScope.brand}</div>
                        <div class="row">${requestScope.pdname}</div>
                    </div>
                    <div class="col">
                        <input type="number" min="0" max="20" value="0" class="quantity-input" data-index="1" size="1" id="led">
                    </div>
                    <div class="col">₩<span class="item-price" id="price2">0.00</span> 
                    <button class="fixed-button">&#10005;</button></div>
                </div>
            </div>
            <div class="row border-top border-bottom">
                <div class="row main align-items-center">
                <div class="col-1">
                		<input type="checkbox" class="item-checkbox" data-index="0">
                	</div>	
                 		 <div class="col-2"><img class="img-fluid" src="https://i.imgur.com/ba3tvGm.jpg"></div>
                    <div class="col">
                        <div class="row text-muted">Shirt</div>
                        <div class="row">Cotton T-shirt</div>
                    </div>
                    <div class="col">
                        <input type="number" min="0" max="20" value="0" class="quantity-input" data-index="2" size="1" id="led">
                    </div>
                    <div class="col">₩<span class="item-price" id="price3">0.00</span>
                    <button class="fixed-button">&#10005;</button></div>
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
                <div class="col text-right" id="sumprice">₩ 132.00</div>
            </div>
            <form>
                <p>배송비</p>
                <div id="shippingPay"><option class="text-muted">기본 배송비 - ₩3,000</option></div>
               <!--  <p>GIVE CODE</p>
                <input id="code" placeholder="Enter your code"> -->
            </form>
            <div class="row" style="border-top: 1px solid rgba(0,0,0,.1); padding: 2vh 0;">
                <div class="col" id="totalprice">결제 금액</div>
                <div class="col text-right">₩ 137.00</div>
            </div>
            <button class="btn" id="go_Pay">결제하기</button>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>  

<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    var quantityInputs = document.querySelectorAll('.quantity-input');
    var priceElements = document.querySelectorAll('.item-price');
    var totalItemsPriceElement = document.querySelector('.total-items-price');
    var totalPriceElement = document.querySelector('.total-price');
    var closeButton = document.querySelectorAll('.close');

    function updatePrice(event) {
        var input = event.target;
        var index = input.dataset.index;
        var unitPrice = 44.00;
        var quantity = parseInt(input.value);
        var totalPrice = unitPrice * quantity;
        updateItemPrice(index, totalPrice); // Update item price

        // Update total items price
        var totalItemsPrice = 0;
        priceElements.forEach(function(priceElement) {
            totalItemsPrice += parseFloat(priceElement.textContent.replace('', ''));
        });
        totalItemsPriceElement.textContent = totalItemsPrice.toFixed(2);

        // Calculate total price including shipping
        var shippingPrice = 5.00;
        var totalPriceWithShipping = totalItemsPrice + shippingPrice;
        totalPriceElement.textContent = totalPriceWithShipping.toFixed(2);
    }

    function updateItemPrice(itemIndex, totalPrice) {
    	
        if (itemIndex >= 0 && itemIndex < priceElements.length) {
            priceElements[itemIndex].textContent = totalPrice.toFixed(2);
        } else {
            console.error("Invalid item index!");
        }
        
    }

    quantityInputs.forEach(function(input) {
        input.addEventListener('change', updatePrice);
    });
    
    // 주문 수량이 바뀌면 주문금액에 값을 넣어주기
    const number_list = document.querySelectorAll("span.item-price");
    
    

 
});
</script>
