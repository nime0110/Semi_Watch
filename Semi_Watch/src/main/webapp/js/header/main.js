$(document).ready(function() {

  
    const navBtn = $('.top-header__left .nav-btn'); // 네비게이션 버튼
    const mainNav = $('.top-header__left .main-nav'); // 메인 네비게이션
    const mainNavCloseBtn = $('.main-nav__close-btn'); // 네비게이션 닫기 버튼
    const mainNavContentContainer = $('.main-nav__content-container'); // 네비게이션 콘텐츠 컨테이너
    const btnCart = $('.top-header__btn-cart'); // 장바구니 버튼
    const cartSection = $('.cart-section'); // 장바구니 섹션
    const inputProductQuantity = $('#product__quantity'); // 제품 수량 입력



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


    // 위시리스트 버튼 클릭시 
    $('#wish_list').click(function() { // 첫번째 찜

      //빈 배열 선언
      let arr_jjim = [];
      //특정한 키값이 getItem 했을때 
      if (localStorage.getItem('name').trim() == "" || localStorage.getItem('name') == null ) { // 첫번째 찜

        // 키를 선택자로 가져온다.
        let productName = $('#productName').text();

        // local 스토리지에 담음
        localStorage.setItem('str_arr_jjim', str_arr_jjim);
        // 이미지/ 상품명을 가져옴
            
        // 위시리스트 배열 만들기
        arr_jjim.push("name", productName);
        /*
        let arr_jjim = {
          image: productImage,
          name: productName
        };
        */
        let str_arr_jjim = JSON.stringify(arr_jjim); //객체 -> json 으로 바꾸기

      } else { //두번째 이후의 찜
        // get을 먼저 해와서 
        let jjim_arr = JSON.parse(localStorage.getItem('str_arr_jjim')); //배열 - 원래 있던 값
        //다른 상품도 가져와야 함
        let productName = $('#productName').text();
        jjim_arr.push("name", productName);
      }




      // 위시리스트에서 보여줌 
      displayWishListDetails(productImage, productName);

      $.ajax({
        type: "method",
        url: "url", //컨트롤러 만들고 
        data: "data",
        dataType: "dataType",
        success: function (json) {

          let html ='';
          //foreach로 json. 
          html += `
          <div id="wish_list_details">
            <img src="${image}" alt="${name}">
            <span>${name}</span>
          </div> `;
       
          $("#cart-section > div.cart-section__body > ul").html(wishListDetailsHtml);
  
        }
      });

    });

    function displayWishListDetails(image, name) {
      //위시리스트 디테일 html 더해주는 코드~~ li로 작성해야 
      let wishListDetailsHtml = `
        <div id="wish_list_details">
          <img src="${image}" alt="${name}">
          <span>${name}</span>
        </div> `;
     
        $("#cart-section > div.cart-section__body > ul").html(wishListDetailsHtml);

    }

  });
