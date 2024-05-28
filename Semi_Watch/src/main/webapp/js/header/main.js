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
    inputProductQuantity.val(1); // 초기 제품 수량을 1으로 설정
    if(inputProductQuantity.val() == 1) {
		$("button.minus-item").prop("disabled");
	}
    
    // 페이지가 로드될 때 로컬 스토리지에서 위시리스트 데이터를 불러와서 렌더링
    wishListRendering();
    function wishListRendering() {
	    let storedWishlistHTML = localStorage.getItem('wishlistHTML');
	    if (storedWishlistHTML) {
	      $("#cart-section > div.cart-section__body > ul").html(storedWishlistHTML);
	       checkWishlist(); // 위시리스트 상태 다시 확인
	    }	
    }
    
    // 페이지가 다시 표시될 때 위시리스트 렌더링 => 뒤로가기/ 앞으로 가기시 위시리스트 상태 유지를 위한 함수
    window.addEventListener('pageshow', function(e) { //history.back() 에서도 실행 됨 반대는 pagehide
    	//pageshow - html로드시 반드시 실행
        wishListRendering(); //렌더링 ㄱㄱ
    });



    // 선택한 색상 값 가져오기
	  let selectedColor;
	  $('#color_select').on('change', function() {
		selectedColor = $(this).val(); //선택한 색상의 값
	  });



    // 찜하기 버튼 클릭시에 발생하는 중요 이벤트 start ----------------------------------------------
$('#wish_list').click(function() {
    let productno = $('#productno').val(); // 해당 제품의 제품번호 (input hidden)
        const colorSelect = $('#color_select');
        if (colorSelect.length > 0 && !selectedColor) {
			alert("색상을 선택하세요!");
			return;
		}
		if(colorSelect.length == 0) {
			selectedColor = 'none'; //기본 컬러가 없을 경우 none 으로 설정.
		}
		  

    if (localStorage.getItem('str_arr_jjim') == null) { // 첫 번째로 찜하는 경우
        // 위시리스트 배열 만들기
        arr_jjim.push({ jepumno: productno, color: selectedColor });
        let str_arr_jjim = JSON.stringify(arr_jjim); // 객체 -> JSON으로 바꾸기
        // local 스토리지에 담음
        localStorage.setItem('str_arr_jjim', str_arr_jjim);
    } else { // 첫 번째 이후로 찜하는 경우(이미 배열이 존재하는 경우)
        // get을 먼저 해와서
        arr_jjim = JSON.parse(localStorage.getItem('str_arr_jjim')); // 배열 - 원래 있던 값
        // 중복 확인 후 추가
        let exists = arr_jjim.some(item => item.jepumno === productno && item.color === selectedColor);

        if (!exists) { // 그 상품이 존재하지 않는다면
            arr_jjim.push({ jepumno: productno, color: selectedColor }); 
            // 업데이트된 배열을 로컬스토리지에 다시 저장
            let str_arr_jjim = JSON.stringify(arr_jjim);
            localStorage.setItem('str_arr_jjim', str_arr_jjim);
            alert("상품을 위시리스트에 추가하셨습니다.");
        } else {
            alert("이미 위시리스트에 존재하는 상품입니다.");
            return;
        }
    }

    // 선택된 색상 정보를 "95:black,99:white" 형식으로 생성
    let colorInfo = arr_jjim.map(item => `${item.jepumno}:${item.color || 'none'}`).join(",");
    console.log("colorInfo:", colorInfo);

    // AJAX 요청
    $.ajax({
        type: "get",
        url: "wishListAdd.flex", // 컨트롤러 만들고 
        data: {
            "pdnos": arr_jjim.map(item => item.jepumno).join(","),
            "selectedColors": selectedColor // 선택된 색상을 함께 전송
        }, // "새우깡,양파링" --> 이거 컨트롤러에서 split 해서 in()으로 sql 조회, ? 위치홀더금지
        dataType: "json",
        success: function (json) {
            console.log("AJAX 요청 성공");
            console.log("응답 데이터:", json);
            let html = ''; // html 초기화

            // JSON 데이터가 배열이라고 가정하고 루프를 통해 각 항목을 처리
            json.forEach(item => { 
                let itemColor = item.color || 'none'; // 색상 없을 경우 none으로 처리 
                console.log("foreach 속 item.pdname" + item.pdname);

                html += `
                <li id="${item.pdno}-${selectedColor}" class="cart-section__li">
                    <a href="${contextPath}/item/itemDetail.flex?pdno=${item.pdno}" class="list-item">
                        <input type="hidden" name="${item.pdno}">
                        <input class="form-check-input" style="left:0;" type="checkbox" value="${item.pdno}" id="flexCheckDefault">
                        <img src="${contextPath}/images/product/product_thum/${item.pdimg}" alt="${item.pdname}" class="product__thumb">
                        <div class="list-item__abstract"	>
                            <h4>${item.pdname}</h4>
                            <div class="price-calculation" style="display: flex; justify-content: space-between;">
                                <input type="hidden" class="input_color" value="${itemColor}">
                                <span>색상: ${selectedColor}</span>
                                <p class="price-calculation__value">
                                    <span class="value__span">${item.pdsaleprice.toLocaleString("ko-KR")}</span>
                                </p>
                            </div>
                        </div>
                    </a>
                    <button type="button" class="btn-del-product">
                        <span class="sr-only">Delete this product</span>
                        <img src="${contextPath}/images/header/icon-delete.svg" alt="itemdelete" role="presentation">
                    </button>
                </li>
                `
            });
            // 기존 HTML 가져오기 및 추가된 HTML 병합
            let existingHTML = $("#cart-section > div.cart-section__body > ul").html();
            html = existingHTML + html;
            // 위의 li 문 생성 후 ul 안에 li 코드를 넣어서 보여주는 코드
            $("#cart-section > div.cart-section__body > ul").html(html);
            console.log("html", html);
            localStorage.setItem('wishlistHTML', html); // 로컬스토리지에 html을 넣음
            checkWishlist(); // 위시리스트 상태 다시 확인
        },
        error:function(request,error){
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
});

    // 찜하기 버튼 클릭시에 발생하는 중요 이벤트 end ----------------------------------------------

   //위시리스트가 있는지 확인하고 정렬하는 함수 start -------------------------     
   function checkWishlist() { 
     let wishlistStr = localStorage.getItem('str_arr_jjim');
     let wishlist = JSON.parse(wishlistStr || '[]'); // JSON으로 파싱, 없는 경우 빈 배열로 초기화 : 위시리스트 위 동그라미에 숫자 표시 위함
    //위시리스트 내부 정렬 
      if (localStorage.getItem('str_arr_jjim').length > 2) {
		  
		$('body > header > div.top-header__right > button > span').addClass('active'); //위시리스트에 담긴 숫자 표시하는 css 클래스 
		$('body > header > div.top-header__right > button > span > span.value').html(wishlist.length);
		  
		  //console.log("localStorage.getItem('wishlistHTML')", localStorage.getItem('wishlistHTML'));
		  //alert("위시리스트가 안비어있음");
		  
        // --------------------- html 정렬 ---------------------
        // cart-section__body 클래스 div에 --with-items 클래스 추가(CSS)
        $('.cart-section__body').addClass('--with-items');
        
        // cart-section__products 클래스 ul에 --active 클래스 추가 (CSS)
        $('.cart-section__products').addClass('--active');

        //3. empty-msg클래스 p --deactivate 클래스 추가(CSS)
        $('.empty-msg').addClass('--deactivate');
        // ----------------------------------------------------
      } else {
		$('body > header > div.top-header__right > button > span').removeClass('active'); //비어있는 순간 동그라미 사라짐
		  //alert("위시리스트가 비어있음");
		          // --------------------- html 정렬 ---------------------
        // cart-section__body 클래스 div에 --with-items 클래스 지우기(CSS)
        $('.cart-section__body').removeClass('--with-items');
        
        // cart-section__products 클래스 ul에 --active 클래스 지우기(CSS)
        $('.cart-section__products').removeClass('--active');

        //3. empty-msg클래스 p --deactivate 클래스 지우기(CSS)
        $('.empty-msg').removeClass('--deactivate');
        // ----------------------------------------------------
		  
	  }

	}
	//위시리스트가 있는지 확인하고 정렬하는 함수 end -------------------------   

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

    


    // 위시리스트 섹션 내의 삭제 버튼 클릭 이벤트 
    $(document).on('click', '.btn-del-product', function() {
	  //alert("삭제 버튼 클릭");
      const li = $(this).closest('li'); //해당 코드에서 가장 위에있는 li를 찾음
   	  const idParts = li.attr('id').split('-'); // id를 '-'로 분리
      const productno = idParts[0]; // productno 부분만 추출
 	  const color = idParts[1]; // color 부분 추출
	    // 로컬 스토리지에서 HTML 삭제
	  let storedHTML = $("#cart-section > div.cart-section__body > ul").html();
	  storedHTML = storedHTML.replace(li.prop('outerHTML'), '');
	  localStorage.setItem('wishlistHTML', storedHTML);
	
	
	  // 로컬 스토리지에서 데이터 삭제
	  arr_jjim = JSON.parse(localStorage.getItem('str_arr_jjim'));
	  arr_jjim = arr_jjim.filter(item => item.jepumno !== productno  || item.color !== color);
	  localStorage.setItem('str_arr_jjim', JSON.stringify(arr_jjim));


      // UI에서 해당 li 요소 삭제
      li.remove();
      
      // 위시리스트 상태 다시 확인
	  checkWishlist(); 

      // 업데이트된 HTML을 다시 로컬 스토리지에 저장
      const wishlistHTML = $("#cart-section > div.cart-section__body > ul").html();
      localStorage.setItem('wishlistHTML', wishlistHTML);
		

    });
	// ---------------------------------------------
	

	
    // 위시리스트 내에서 장바구니로 이동하기 버튼 클릭 이벤트 ---------------------------
    window.addCart = function() {
        let cartItems = []; //장바구니아이템
        let selectedLis = []; //선택된 li
        $('#cart-section > div.cart-section__body > ul > li').each(function() { //li만큼 반복문
			  if ($(this).find('input[type="checkbox"]').is(':checked')) { //체크박스에 체크된 것만 선택자로 갖고 들어옴\
			   		let idParts = $(this).attr('id').split('-'); // id를 '-'로 분리
      				let productno = idParts[0]; // productno 부분만 추출
      				let color = idParts[1]; // color 부분 추출
		            let productName = $(this).find('h4').text();
		            let productPrice = $(this).find('.value__span').text();
		            let productColor = $(this).find('input.input_color').val();
		            
		            //let productColor = $(this).find('input[type="hidden"]').text().replace('색상: ', '');
		       
		            let productImage = $(this).find('img.product__thumb').attr('src');
		            cartItems.push({ //배열생성 -ajax 전달객체
		                productno: productno,
		                productName: productName,
		                productPrice: productPrice,
		                productColor: productColor,
		                productImage: productImage
		            });
		            selectedLis.push({ li: $(this), productno: productno, color: color }); // 선택된 li를 배열에 저장
            } 

        });
        if(cartItems.length === 0) {
			alert("장바구니로 보낼 상품을 선택하세요!!");
			return;
		}

        console.log("cartItems:", cartItems);
        
        $.ajax({
            type: "POST",
            url: contextPath + "/item/addToCartJSON.flex", 
            data: {"cartItems":JSON.stringify(cartItems)},
            dataType: "json",
            success: function(json) {
                console.log("AJAX 요청 성공:", json);
             
                if (json.loginRequired) { //로그인안을경우
                    alert(json.message);
                    location.href = contextPath + "/login/login.flex"; // 로그인 페이지로 이동
                } else {
                    alert(json.message);
                    // 장바구니 페이지로 이동 (필요한 경우 고려)
                    selectedLis.forEach(function(selected) {
	                  
	                    let li = selected.li;
	                    li.remove();
	                    let productno = selected.productno;
	                    let color = selected.color;
					    // 로컬 스토리지에서 데이터 삭제 --------------------------------
	                    arr_jjim = JSON.parse(localStorage.getItem('str_arr_jjim'));
	                    arr_jjim = arr_jjim.filter(item => item.jepumno !== productno || item.color !== color);
	                    localStorage.setItem('str_arr_jjim', JSON.stringify(arr_jjim));
				

                    });
                          // 업데이트된 HTML을 다시 로컬 스토리지에 저장
		                const wishlistHTML = $("#cart-section > div.cart-section__body > ul").html();
		                localStorage.setItem('wishlistHTML', wishlistHTML);
		                checkWishlist(); // 위시리스트 상태 다시 확인
                    /// ---------------------------------
                }
             },
            error: function(request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
        
        }
        
        
  });

  
  