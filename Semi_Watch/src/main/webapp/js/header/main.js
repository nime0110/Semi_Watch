$(document).ready(function() {
    const navBtn = $('.top-header__left .nav-btn'); // 네비게이션 버튼
    const mainNav = $('.top-header__left .main-nav'); // 메인 네비게이션
    const mainNavCloseBtn = $('.main-nav__close-btn'); // 네비게이션 닫기 버튼
    const mainNavContentContainer = $('.main-nav__content-container'); // 네비게이션 콘텐츠 컨테이너
    const btnCart = $('.top-header__btn-cart'); // 장바구니 버튼
    const cartSection = $('.cart-section'); // 장바구니 섹션
    const inputProductQuantity = $('#product__quantity'); // 제품 수량 입력
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 
    
    //빈 배열 선언
    let arr_jjim = [];
    let colorMap = {};
    
    // 페이지가 로드될 때 로컬 스토리지에서 위시리스트 데이터를 불러와서 렌더링
    let storedWishlistHTML = localStorage.getItem('wishlistHTML');
    if (storedWishlistHTML) {
      $("#cart-section > div.cart-section__body > ul").html(storedWishlistHTML);
       checkWishlist(); // 위시리스트 상태 다시 확인
    }
    
    // 색상 맵 로컬 스토리지
    let storedColorMap = localStorage.getItem('colorMap');
    if (storedColorMap) {
        colorMap = JSON.parse(storedColorMap);
    }

    // 선택한 색상 값 가져오기
    $('#color_select').on('change', function() {
        let selectedColor = $(this).val();
        let productno = $('#productno').val();
        colorMap[productno] = selectedColor;
        localStorage.setItem('colorMap', JSON.stringify(colorMap));//로컬스토리지에 저장 
        console.log("selectedColor:", selectedColor);
        console.log("colorMap:", colorMap);
    });


    // 찜하기 버튼 클릭시 
    $('#wish_list').click(function() {
    let productno = $('#productno').val(); //해당 제품의 제품번호 (input hidden)
    if ($('#color_select').length > 0) {
	    if (!colorMap[productno]) {
	        alert("먼저 색상을 선택하세요!");
	        return;
	    }
    }
	//색상 박스가 없을 경우 아래 코드 실행
    //-------------------------------------------------------------------------


  //특정한 키값이 getItem 했을때 
  if (localStorage.getItem('str_arr_jjim') == null) { // 첫번째 찜
    // 키를 선택자로 가져온다.
   // 위시리스트 배열 만들기
    arr_jjim.push(productno);

    let str_arr_jjim = JSON.stringify(arr_jjim); //객체 -> json 으로 바꾸기
    // local 스토리지에 담음
    localStorage.setItem('str_arr_jjim', str_arr_jjim);
    // 이미지/ 상품명을 가져옴

    /*
    let str_arr_jjim = ["새우깡"];
    */
    
  } else { //첫번째 이후의 찜
    // get을 먼저 해와서 
    arr_jjim = JSON.parse(localStorage.getItem('str_arr_jjim')); //배열 - 원래 있던 값  ["새우깡"];
    //console.log("else 문의 jjim_arr:", arr_jjim);
    //다른 상품도 가져와야 함
    //console.log("productName: " + productName);
    // 중복 확인 후 추가
    
    let idx = -1;
    for(let i=0; i<arr_jjim.length; i++){
		if(productno == arr_jjim[i]) {
			idx = i;
			break;
		}
	} 
   
        //alert(idx);   // 0
        //alert(arr_jjim[idx]);  // "새우깡""

        if (idx == -1) { //그 상품이 존재하지 않는다면 
          //alert("넣으세요!");
          
          arr_jjim.push(productno);  // ["새우깡","양파링"]
          //alert(arr_jjim.join(","));
      
          // 업데이트된 배열을 로컬스토리지에 다시 저장
          let str_arr_jjim = JSON.stringify(arr_jjim);
          localStorage.setItem('str_arr_jjim', str_arr_jjim);
          
        } else {
        //console.log("이미 존재하는 상품입니다:", productName);
          alert("이미 위시리스트에 존재하는 상품입니다.");
          return;
        }

        
  
      }

      //console.log("~~~ 확인용 : ", arr_jjim);  // ["새우깡","양파링"]

      // 위시리스트에서 보여줌 
      //displayWishListDetails(productImage, productName);
	    // 선택된 색상 정보를 "95:black,99:white" 형식으로 생성
        let colorInfo = arr_jjim.map(pdno => `${pdno}:${colorMap[pdno] || '없음'}`).join(",");
        console.log("colorInfo:", colorInfo);
      
      $.ajax({
        type: "get",
        url: "wishListAdd.flex", //컨트롤러 만들고 
        data: {
			"pdnos":arr_jjim.join(","),
			"selectedColors": colorInfo// 선택된 색상을 함께 전송
			},  // "새우깡,양파링" -->이거 컨트롤러에서 split 해서 in() 으로 sql 조회, ? 위치홀더금지
        dataType: "json",
        success: function (json) {
		  console.log("AJAX 요청 성공");
          console.log("응답 데이터:", json);
          let html ='';
          //foreach로 json. ~~ 해서 가져오면 되..           
          // JSON 데이터가 배열이라고 가정하고 루프를 통해 각 항목을 처리
          json.forEach(item => {   
			let itemColor = item.color || '색상 없음';
                console.log("foreach 속 item.pdname" + item.pdname);
            // html += `
            //   <li id="wish_list_details">
            //     <img src="${pdimg}" alt="${pdname}">            
            //     <span>${item.pdname}</span>
            //     <span>	${item.pdprice}</span>
            //   </li>`;
            //a태그에 해당 상품의 페이지로 가는거 있으면 좋을 듯함
            html += `
              <li id="${item.pdno}" style="position:relative; margin-bottom: 50px;">
			        <a href="${contextPath}/item/itemDetail.flex?pdno=${item.pdno}" class="list-item">
			        <input type="hidden" name="${item.pdno}">
			        <input class="form-check-input" style="left:0;" type="checkbox" value="${item.pdno}" id="flexCheckDefault">
			        <img src="${contextPath}/images/product/product_thum/${item.pdimg}" alt="${item.pdname}" class="product__thumb">
			        <div class="list-item__abstract">
			            <h4>${item.pdname}</h4>
			            <div class="price-calculation" style="display: flex; width: 70%; justify-content: space-between;">
			                <span>${itemColor}</span>
			                <p class="price-calculation__value">
			                    <span class="value__span">${item.pdsaleprice.toLocaleString("ko-KR")}</span>
			                </p>
			            </div>
			        </div>
			    </a>
			    <button type="button" class="btn-del-product" style="position:absolute; top:10px; right:5px;">
			        <span class="sr-only">Delete this product</span>
			        <img src="${contextPath}/images/header/icon-delete.svg" alt="itemdelete" role="presentation">
			    </button>
			</li>
                `
          });

          $("#cart-section > div.cart-section__body > ul").html(html);
		    console.log("html", html);
	      localStorage.setItem('wishlistHTML', html);
	       checkWishlist(); // 위시리스트 상태 다시 확인
  
        },
        error:function(request,status,error){
              alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
       
      });

    }); // 찜하기 버튼 끝

        
   function checkWishlist() { //위시리스트가 있는지 확인하고 정렬예정
    //위시리스트 내부 정렬 
      if (localStorage.getItem('str_arr_jjim').length > 2) {
		  //console.log("localStorage.getItem('wishlistHTML')", localStorage.getItem('wishlistHTML'));
		  //alert("위시리스트가 안비어있음");
		  
        // --------------------- html 정렬 ---------------------
        // cart-section__body 클래스 div에 --with-items 클래스 추가
        $('.cart-section__body').addClass('--with-items');
        
        // cart-section__products 클래스 ul에 --active 클래스 추가
        $('.cart-section__products').addClass('--active');

        //3. empty-msg클래스 p --deactivate 클래스 추가
        $('.empty-msg').addClass('--deactivate');
        // ----------------------------------------------------
      } else {
		  //alert("위시리스트가 비어있음");
		          // --------------------- html 정렬 ---------------------
        // cart-section__body 클래스 div에 --with-items 클래스 지우기
        $('.cart-section__body').removeClass('--with-items');
        
        // cart-section__products 클래스 ul에 --active 클래스 지우기
        $('.cart-section__products').removeClass('--active');

        //3. empty-msg클래스 p --deactivate 클래스 지우기
        $('.empty-msg').removeClass('--deactivate');
        // ----------------------------------------------------
		  
	  }

	}


    inputProductQuantity.val(0); // 초기 제품 수량을 0으로 설정
    
	  // 네비게이션 버튼 클릭 이벤트 
    navBtn.click(toggleMenu);
    mainNavCloseBtn.click(toggleMenu);
    btnCart.click(toggleCart);

  // 문서 오버플로우 토글 함수
    function toggleDocumentOverflow() {
      $('html, body').toggleClass('--overflow-hidden');
    }

    // 메뉴 토글 함수
    function toggleMenu() {
      toggleDocumentOverflow(); // 문서 오버플로우 토글
      mainNav.toggleClass('active'); // 메인 네비게이션 활성화 토글
      mainNavContentContainer.toggleClass('active'); // 네비게이션 콘텐츠 컨테이너 활성화 토글
      navBtn.attr('aria-expanded', mainNav.hasClass('active')); // 네비게이션 버튼의 ARIA 확장 속성 설정
    }


      
    // 위시리스트 토글 함수
    function toggleCart() {

      if (cartSection.hasClass('active')) {
          cartSection.hide(); // 장바구니 섹션 숨기기
          btnCart.attr('aria-expanded', false); // 장바구니 버튼 ARIA 확장 속성 false 설정
      } else {
          cartSection.show(); // 장바구니 섹션 보이기
          btnCart.attr('aria-expanded', true); // 장바구니 버튼 ARIA 확장 속성 true 설정
      }
      setTimeout(function() {
          cartSection.toggleClass('active'); // 장바구니 섹션 활성화 토글
      }, 100);
      
      btnCart.toggleClass('--active'); // 장바구니 버튼 활성화 토글
    }

    


    // 위시리스트 섹션 내의 삭제 버튼 클릭
    $(document).on('click', '.btn-del-product', function() {
	  //alert("삭제 버튼 클릭");
      const li = $(this).closest('li'); //해당 코드에서 가장 위에있는 li를 찾음
      const productno = li.attr('id'); //그 해당 li의 아이디를 찾음

	    // 로컬 스토리지에서 HTML 삭제
	  let storedHTML = $("#cart-section > div.cart-section__body > ul").html();
	  storedHTML = storedHTML.replace(li.prop('outerHTML'), '');
	  localStorage.setItem('wishlistHTML', storedHTML);
	
	
	    // 로컬 스토리지에서 데이터 삭제
	  arr_jjim = JSON.parse(localStorage.getItem('str_arr_jjim'));
	  arr_jjim = arr_jjim.filter(item => item !== productno);
	  localStorage.setItem('str_arr_jjim', JSON.stringify(arr_jjim));

      delete colorMap[productno];
      localStorage.setItem('colorMap', JSON.stringify(colorMap));
      
      // UI에서 해당 li 요소 삭제
      li.remove();
      
      // 위시리스트 상태 다시 확인
	  checkWishlist(); 

      // 업데이트된 HTML을 다시 로컬 스토리지에 저장
      const wishlistHTML = $("#cart-section > div.cart-section__body > ul").html();
      localStorage.setItem('wishlistHTML', wishlistHTML);
		

    });


    // 위시리스트 내에서 장바구니로 이동하기 버튼 클릭시 -----------------
    window.addCart = function() {
        let cartItems = [];
        $('#cart-section > div.cart-section__body > ul > li').each(function() { //li만큼 반복문
			  if ($(this).find('input[type="checkbox"]').is(':checked')) { //체크박스에 체크된 것만 선택자로 갖고드어옴
		            let productno = $(this).attr('id');
		            let productName = $(this).find('h4').text();
		            let productPrice = $(this).find('.value__span').text();
		            let productColor = $(this).find('.price-calculation > span').text().replace('색상: ', '');
		       
		            let productImage = $(this).find('img.product__thumb').attr('src');
		            cartItems.push({ //배열생성 -ajax 전달객체
		                productno: productno,
		                productName: productName,
		                productPrice: productPrice,
		                productColor: productColor,
		                productImage: productImage
		            });
            }
        });

        console.log("cartItems:", cartItems);
        
        $.ajax({
            type: "POST",
            url: "addToCartJSON.flex", // 서버의 장바구니 추가 API 엔드포인트
            data: JSON.stringify(cartItems),
            dataType: "json",
            success: function(json) {
                console.log("AJAX 요청 성공:", json);
                alert("장바구니에 상품이 추가되었습니다.");
                // 장바구니 페이지로 이동 (필요한 경우)
                window.location.href = contextPath + "/cartPage.flex";
            },
            error: function(request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
        
        }
        
        
  });

  
  