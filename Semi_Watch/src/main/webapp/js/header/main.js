$(document).ready(function() {

  
    const navBtn = $('.top-header__left .nav-btn'); // 네비게이션 버튼
    const mainNav = $('.top-header__left .main-nav'); // 메인 네비게이션
    const mainNavCloseBtn = $('.main-nav__close-btn'); // 네비게이션 닫기 버튼
    const mainNavContentContainer = $('.main-nav__content-container'); // 네비게이션 콘텐츠 컨테이너
    const btnCart = $('.top-header__btn-cart'); // 장바구니 버튼
    const cartSection = $('.cart-section'); // 장바구니 섹션
    const inputProductQuantity = $('#product__quantity'); // 제품 수량 입력
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 

    console.log("Context Path: ", contextPath); // 확인용

    
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

      if (localStorage.getItem('str_arr_jjim') != null) {
        // --------------------- html 정렬 ---------------------
        // cart-section__body 클래스 div에 --with-items 클래스 추가
        $('.cart-section__body').toggleClass('--with-items');
        
        // cart-section__products 클래스 ul에 --active 클래스 추가
        $('.cart-section__products').toggleClass('--active');

        //3. empty-msg클래스 p --deactivate 클래스 추가
        $('.empty-msg').toggleClass('--deactivate');
        
        // ----------------------------------------------------
      }

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

    //빈 배열 선언
    let arr_jjim = [];

    // 찜하기 버튼 클릭시 
    $('#wish_list').click(function() { // 첫번째 찜

      //특정한 키값이 getItem 했을때 
      if (localStorage.getItem('str_arr_jjim') == null || arr_jjim.length == 0) { // 첫번째 찜
        // 키	를 선택자로 가져온다.
        let productName = $('#productName').text();
        // 위시리스트 배열 만들기
        arr_jjim.push(productName);

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
        console.log("else 문의 jjim_arr:", arr_jjim);
        //다른 상품도 가져와야 함
        let productName = $('#productName').text();
            
        // 중복 확인 후 추가
        if (!arr_jjim.includes(productName)) {
          arr_jjim.push(productName);  // ["새우깡","양파링"]
          console.log("else 문의 push 후 arr_jjim:", arr_jjim);
      
          // 업데이트된 배열을 로컬스토리지에 다시 저장
          let str_arr_jjim = JSON.stringify(arr_jjim);
          localStorage.setItem('str_arr_jjim', str_arr_jjim);
        } else {
          console.log("이미 존재하는 상품입니다:", productName);
        }

        
  
      }

      //console.log("~~~ 확인용 : ", arr_jjim);  // ["새우깡","양파링"]

      // 위시리스트에서 보여줌 
      //displayWishListDetails(productImage, productName);

      
      $.ajax({
        type: "get",
        url: "wishListAdd.flex", //컨트롤러 만들고 
        data: {"pdnames":arr_jjim.join(",")},  // "새우깡,양파링" -->이거 컨트롤러에서 split 해서 in() 으로 sql 조회, ? 위치홀더금지
        dataType: "json",
        success: function (json) {
          alert("성공!!!!!!!!!!");
          let html ='';
          //foreach로 json. ~~ 해서 가져오면 되..           
          // JSON 데이터가 배열이라고 가정하고 루프를 통해 각 항목을 처리
			console.log("foreach 밖 item.pdname :" + json.pdname);
          json.forEach(item => {     
			console.log("foreach 속 item.pdname" + item.pdname);
            // html += `
            //   <li id="wish_list_details">
            //     <img src="${pdimg}" alt="${pdname}">            
            //     <span>${item.pdname}</span>
            //     <span>${item.pdprice}</span>
            //   </li>`;
            //a태그에 해당 상품의 페이지로 가는거 있으면 좋을 듯함
            html += `
              <li id="${item.pdname}"> 
                  <a href="#" class="list-item">
                	<input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                    <img src="${contextPath}/images/product/product_thum/${item.pdimg}" alt="${item.pdname}" class="product__thumb">
                    <div class="list-item__abstract">
                      <h4>${item.pdname}</h4>
                      <div class="price-calculation">
                        <p class="price-calculation__value">
                          <span class="value__span">${item.pdprice.toLocaleString("ko-KR")}</span>
                        </p>
                      </div>
                    </div>
                    <button type="button" class="btn-del-product">
                      <span class="sr-only">Delete this product</span>
                      <img src="${contextPath}/images/header/icon-delete.svg" alt="itemdelete" role="presentation">
                    </button>
                    </a>
                </li>
                `
          });

          $("#cart-section > div.cart-section__body > ul").html(html);
          // 생성된 HTML을 로컬 스토리지에 저장
          localStorage.setItem('wishlistHTML', html);
  
        },
        error:function(request,status,error){
              alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
       
      });

    }); // 찜하기 버튼 끝

    // 페이지가 로드될 때 로컬 스토리지에서 위시리스트 데이터를 불러와서 렌더링
    let storedWishlistHTML = localStorage.getItem('wishlistHTML');
    if (storedWishlistHTML) {
      $("#cart-section > div.cart-section__body > ul").html(storedWishlistHTML);
    }
    
    //위시리스트 섹션 내의 삭제 버튼 클릭
    // 위시리스트에서 특정 상품을 삭제하는 함수
    // function deleteItem(productId) {
    //   localStorage.removeItem(productId);
    // }


    // 위시리스트 섹션 내의 삭제 버튼 클릭
    $(document).on('click', '.btn-del-product', function() {
	  alert("삭제 버튼 클릭");
      const li = $(this).closest('li'); //해당 코드에서 가장 위에있는 li를 찾음
      const productName = li.attr('id'); //그 해당 li의 아이디를 찾음

      localStorage.removeItem(productName); //아이디 li 삭제 (로컬 스토리지에 html 저장된거)
	  // 로컬 스토리지에서 해당 아이템 삭제
	  let arr_jjim = JSON.parse(localStorage.getItem('str_arr_jjim')) || [];
	  arr_jjim = arr_jjim.filter(item => item !== productName);
	  localStorage.setItem('str_arr_jjim', JSON.stringify(arr_jjim));
	  
      // UI에서 해당 li 요소 삭제
      li.remove();

      // 업데이트된 HTML을 다시 로컬 스토리지에 저장
      const updatedHTML = $("#cart-section > div.cart-section__body > ul").html();
      localStorage.setItem('wishlistHTML', updatedHTML);

      // 빈 상태일 경우 클래스 업데이트
      if (arr_jjim.length === 0) {
        $('.cart-section__body').removeClass('--with-items');
        $('.cart-section__products').removeClass('--active');
        $('.empty-msg').removeClass('--deactivate');
      }
    });

  });
