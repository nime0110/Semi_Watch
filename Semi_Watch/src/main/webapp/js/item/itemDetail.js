/*  아래 코드로 사진 변경 가능 */
  $(document).ready(function() {
   const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 

  	 $('ul#choice li button img').on('click',function(){
	    var i = $(this).attr('src');
		console.log(i);
	    $('.image-box img').attr('src',i);
	    return false;
	  })
	  
	  
	  //첫번째 content로 애니메이트
	  //$('html,body').stop().animate({scrollTop: 0},2000);
	  //메뉴 클릭하면 해당 위치 찾아가기
	  $('.categori ul li a').on('click',function(){
		//-첫째로 몇번째인지 알아야됨
		let n = $(this).parent().index();
		//해당 위치 찾아가기
		let target =  $('.categori').eq(n).offset().top;
		$('html,body').stop().animate({scrollTop: target},2000);
		return false;
	  })
	  $('.minus-item').click(function() {
        let quantityInput = $('#product__quantity');
        let currentValue = parseInt(quantityInput.val());
        
        if (currentValue > 1) {
          quantityInput.val(currentValue - 1);
        }
        if(currentValue == 1) {
        	$(this).prop("disabled"); 
        }
      });

      $('.plus-item').click(function() {
        var quantityInput = $('#product__quantity');
        var currentValue = parseInt(quantityInput.val());
        quantityInput.val(currentValue + 1);
      });
      
      
      // 선택한 색상 값 가져오기 -----------------------------
      let selectedColor;
	  $('#color_select').on('change', function() {
		selectedColor = $(this).val(); //선택한 색상의 값
	  });

      
      
      
      /* 장바구니로 이동하기 ---> ajax로 보내줄 예정 */
      $('#addCart').click(function() {
		  
        const colorSelect = $('#color_select');
        if (colorSelect.length > 0 && !selectedColor) {
			alert("색상을 선택하세요!");
			return;
		}
		if(colorSelect.length == 0) {
			selectedColor = 'none'; //기본 컬러가 없을 경우 none 으로 설정.
		}
		  
		let cartItems = []; //장바구니 아이템 담을 배열
		// 제품번호 / 색상 / 수 
		let productNo = $("input#productno").val(); //제품번호
		
		let quantity = $("#product__quantity").val(); //수 
		
		console.log("==selectedColor : " + selectedColor); //색상
		console.log("==quantity : " + quantity);
		
		 $.ajax({
            type: "POST",
            url: "addToCartDetailJSON.flex", 
            data: {"productNo":productNo,
            	   "selectedColor":selectedColor,
            	   "quantity":quantity},
            dataType: "json",
            success: function(json) {
                console.log("AJAX 요청 성공:", json);
             
                if (json.loginRequired) { //로그인안했을경우
                    alert(json.message);
                    location.href = contextPath + "/login/login.flex"; // 로그인 페이지로 이동
                } else {
                    let answer = confirm(json.message);
                    // 장바구니 페이지로 이동 (config)
                    if(answer == true) {
						location.href = contextPath + "/item/itemCart.flex"; //장바구니
					}
                    
                    /// ---------------------------------
                }
             },
            error: function(request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });

      });
      
        
      
});

