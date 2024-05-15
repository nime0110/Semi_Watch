<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>

<style>





/** Shop: Item **/
.shop-item__info {
  padding: 30px;
  margin-bottom: 40px;
  background-color: white;
  border: 1px solid rgba(0, 0, 0, 0.05);
}
.shop-item-cart__title {
  margin-bottom: 20px;
  line-height: 1.3;
}
.shop-item-cart__price {
  font-size: 28px;
  font-weight: 400;
  color: #F7C41F;
}
.shop-item__intro {
  color: #777777;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  padding-bottom: 10px;
  margin-bottom: 20px;
}
.shop-item__add button[type="submit"] {
  padding: 15px 20px;
}
.shop-item__img {
  margin-bottom: 40px;
}
.shop-item-img__main {
  width: -webkit-calc(100% - 110px);
  width: calc(100% - 110px);
  height: auto;
  float: left;
}
.shop-item-img__aside {
  width: 100px;
  height: auto;
  float: left;
}
.shop-item-img__aside > img {
  cursor: pointer;
  margin-bottom: 10px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  opacity: .5;
}
.shop-item-img__aside > img:hover,
.shop-item-img__aside > img.active {
  border-color: rgba(0, 0, 0, 0.05);
  opacity: 1;
}
@media (max-width: 767px) {
  .shop-item-img__main {
    width: -webkit-calc(100% - 60px);
    width: calc(100% - 60px);
  }
  .shop-item-img__aside {
    width: 50px;
  }
}
/** Shop: Filter **/
.shop__filter {
  margin-bottom: 40px;
}
/* Shop filter: Pricing */
.shop-filter__price {
  padding: 15px;
}
.shop-filter__price [class*='col-'] {
  padding: 2px;
}
/* Shop filter: Colors */
.shop-filter__color {
  display: inline-block;
  margin: 0 2px 2px 0;
}
.shop-filter__color input[type="text"] {
  display: none;
}
.shop-filter__color label {
  width: 30px;
  height: 30px;
  background: transparent;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 3px;
  cursor: pointer;
}
/** Shop: Sorting **/
.shop__sorting {
  list-style: none;
  padding-left: 0;
  margin-bottom: 40px;
  margin-top: -20px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  text-align: right;
}
.shop__sorting > li {
  display: inline-block;
}
.shop__sorting > li > a {
  display: block;
  padding: 20px 10px;
  margin-bottom: -1px;
  border-bottom: 2px solid transparent;
  color: black;
  font-weight: bolder;
  font-size: 11pt;
  -webkit-transition: all .05s linear;
       -o-transition: all .05s linear;
          transition: all .05s linear;
}
.shop__sorting > li > a:hover,
.shop__sorting > li > a:focus {
  color: #333333;
  text-decoration: none;
}
.shop__sorting > li.active > a {
  color: #ed3e49;

}
@media (max-width: 767px) {
  .shop__sorting {
    text-align: left;
    border-bottom: 0;
  }
  .shop__sorting > li {
    display: block;
  }
  .shop__sorting > li > a {
    padding: 10px 15px;
    margin-bottom: 10px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }
  .shop__sorting > li.active > a {
    font-weight: 600;
  }
}
.checkout__submit {
  padding: 15px 40px;
}
/** Shop: Order Confirmation */
.shop-conf__heading {
  margin-bottom: 40px;
}
.shop-conf__heading + p {
  margin-bottom: 100px;
}


/**
 * Forms
 */
.form-control,
.form-control:focus {
  -webkit-box-shadow: none;
          box-shadow: none;
  outline: none;
}
/* Has error */
.has-error .form-control {
  border-color: #d9534f;
  -webkit-box-shadow: none !important;
          box-shadow: none !important;
}
.has-error .form-control:focus {
  border-color: #b52b27;
}
.has-error .help-block {
  color: #d9534f;
}
/* Checkboxes */
.checkbox input[type="checkbox"] {
  display: none;
}
.checkbox label {
  padding-left: 0;
}
.checkbox label:before {
  content: "";
  display: inline-block;
  vertical-align: middle;
  margin-right: 15px;
  width: 20px;
  height: 20px;
  line-height: 20px;
  background-color: #eee;
  text-align: center;
  font-family: "FontAwesome";
}
.checkbox input[type="checkbox"]:checked + label::before {
  content: "\f00c";
}
/* Radios */
.radio input[type="radio"] {
  display: none;
}
.radio label {
  padding-left: 0;
}
.radio label:before {
  content: "";
  display: inline-block;
  vertical-align: middle;
  margin-right: 15px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 10px solid #eee;
  background-color: #333333;
}
.radio input[type="radio"]:checked + label:before {
  border-width: 7px;
}


.button {
  border-radius: 10px;
  border: none;
  
  font-size: 15px;
  
  width: 40%;
  height: 50px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 25px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}


/** Shop: Thumbnails **/
.shop__thumb {
  border: 1px solid rgba(0, 0, 0, 0.05);
  padding: 20px;
  margin-bottom: 20px;
  background-color: white;
  text-align: center;
  -webkit-transition: border-color 0.1s, -webkit-box-shadow 0.1s;
       -o-transition: border-color 0.1s, box-shadow 0.1s;
          transition: border-color 0.1s, box-shadow 0.1s;
}
.shop__thumb:hover {
  border-color: rgba(0, 0, 0, 0.07);
  -webkit-box-shadow: 0 5px 30px rgba(0, 0, 0, 0.07);
          box-shadow: 0 5px 30px rgba(0, 0, 0, 0.07);
}
.shop__thumb > a {
  color: #333333;
  text-decoration: none;
}

.shop-thumb__img {
  position: relative;
  margin-bottom: 20px;
  overflow: hidden;
}
.shop-thumb__title {
  color: #00008B;
  font-size: 16pt;
  font-weight: 600;
  text-decoration: none;

}
.shop-thumb__price {
  color: black;
  font-size: 11pt;
  font-weight: bolder;
  text-align: right;
  margin: 3% 10% 3% 0;
  
  
}

#floatingDiv {
	border-radius: 3%;
	background-color: white;
    position: fixed;

    width: 15%;

  }
  
#top-button{
	border-radius: 10%;
	background-color: white;
    position: fixed;

    width: 4.5%;


  }  

ul.pagination li {
	
	font-size: 13pt;
	font-weight: bold;

}  


  

</style>
<script>
window.addEventListener('scroll', function() {
    var navbar = document.querySelector('nav#header');

    
    if(window.scrollY > 0) {
        navbar.style.backgroundColor = '#3b5d50';
       
    } else {
        navbar.style.backgroundColor = 'black';
        
    }
    
});

<%--
document.addEventListener('DOMContentLoaded', function() {
    var sortingItems = document.querySelectorAll('.shop__sorting li');

    // 각 정렬 항목에 클릭 이벤트 리스너 추가
    sortingItems.forEach(function(item) {
    	
        item.addEventListener('click', function(event) {
            // 클릭된 요소에만 'active' 클래스 추가
            sortingItems.forEach(function(item) {
                item.classList.remove('active');
            });
            this.classList.add('active');
        });
    });
});
--%>
</script>
<%-- Font Awesome 6 Icons --%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<jsp:include page="../header1.jsp" />

<div class="container">
  <div class="row">
    <div class="col-sm-4 col-md-3">
      
      
      <form class="pt-3">
        <div class="well">
          <div class="row">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="text" class="form-control" placeholder="Search products...">
                <button class="btn btn-success" type="button">
                    <i class="fa fa-search"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </form>

      <!-- Filter -->
      <form class="shop__filter">
        
        

        <!-- Checkboxes -->
        
        
		
		
		<div id="floatingDiv">
        <h3 class="headline">
          <span>Brand</span>
        </h3>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_1" checked="">
          <label for="shop-filter-checkbox_1">Adidas</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_2">
          <label for="shop-filter-checkbox_2">Calvin Klein</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_3">
          <label for="shop-filter-checkbox_3">Columbia</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_4">
          <label for="shop-filter-checkbox_4">Tommy Hilfiger</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" class="custom-chk" value="" id="shop-filter-checkbox_5">
          <label for="shop-filter-checkbox_5">Not specified</label>
        </div>

        <!-- Radios -->
        <h3 class="pt-5 headline">
          <span>Material</span>
        </h3>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_1" value="" checked="">
          <label for="shop-filter-radio_1">100% Cotton</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_2" value="">
          <label for="shop-filter-radio_2">Bamboo</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_3" value="">
          <label for="shop-filter-radio_3">Leather</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_4" value="">
          <label for="shop-filter-radio_4">Polyester</label>
        </div>
        <div class="checkbox">
          <input type="checkbox" name="shop-filter__radio" id="shop-filter-radio_5" value="">
          <label for="shop-filter-radio_5">Not specified</label>
        </div>
        
        
        	<div class="float-right" id="top-button"><a style="text-decoration: none; color:black;" href="#">Top&nbsp;▲</a></div>
		</div>
		

		<%-- 
        <div class="dropdown">
			<!-- 버튼태그에서 아래로 보여주는걸 의미함 -->
		  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    Dropdown button
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		    <a class="dropdown-item" href="#">Adidas</a>
		    <a class="dropdown-item active" href="#">Calvin Klein</a>
		    <a class="dropdown-item" href="#">Columbia</a>
		    <a class="dropdown-item" href="#">Tommy Hilfiger</a>
		    <a class="dropdown-item" href="#">Not specified</a>
		  </div>
		</div>
		--%>
      </form>
      
      	
    </div>
 
    <div class="col-sm-8 col-md-9">
      <!-- Filters -->
      <ul class="shop__sorting pt-3">
        <li class="active"><a href="#">신상품순</a></li>
        <li><a href="#">인기상품순</a></li>
        <li><a href="#">높은가격순</a></li>
        <li><a href="#">낮은가격순</a></li>
      </ul>


					
      <div class="row">
      
      	<div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                  <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
        
        <div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
     
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div class="pt-0">
                   <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
          
        <div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                  <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
      
      	<div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
       
       <div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                  <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
       
       <div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
      				<button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
      
      
      
		<div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                 <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
                
        <div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="<%= ctxPath %>/images/product/watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                  <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>
        
         <div class="col-sm-6 col-md-4">
          <div class="shop__thumb">     
              <div class="position-relative overflow-hidden">
            	<div class="shop-thumb__img">
                   <a href=""><img class="img-fluid" src="images\watchTest.png" alt=""></a>
                 </div>  
                   <div class="bg-primary rounded text-white position-absolute start-0 top-0 py-1 px-3">SALE</div>
               </div>
              <a href="#">
              <span class="shop-thumb__title">
                티쏘 젠틀맨 실리시움 </span>
              </a>
              <div class="shop-thumb__price">
                1,140,000원
              </div>
              <div>
                 <button type="button" class="button btn-Light"><span>Buy</span></button>
                   <button type="button" class="button btn-dark"><span>Cart</span></button>
               </div>
          </div> 
        </div>

      </div> <!-- / .row -->

      <!-- Pagination -->
      
      <div class="row justify-content-center">
	    <div class="col-12 text-center">
	        <ul class="pagination">
	            <li><a href="#">«</a></li>
	            <li><a class="page-link" href="#">1</a></li>
	            <li><a class="page-link" href="#">2</a></li>
	            <li><a class="page-link" href="#">3</a></li>
	            <li><a href="#">»</a></li>
	        </ul>
	    </div>
	  </div> <!-- / .row -->
      
    </div> <!-- / .col-sm-8 -->
  </div> <!-- / .row -->
</div>



<jsp:include page="../footer.jsp" />