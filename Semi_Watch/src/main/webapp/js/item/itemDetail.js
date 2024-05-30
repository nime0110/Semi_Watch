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

      
      
      
      /* 장바구니로 이동하기 ----------------------------------- */
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

      }); // 장바구니로 이동하는 함수 종료
      
           
      /* 구매하기로 이동하기 ----------------------------------- */
      $('#buy').click(function() {
		  
        const colorSelect = $('#color_select');
        if (colorSelect.length > 0 && !selectedColor) {
			alert("색상을 선택하세요!");
			return;
		}
		if(colorSelect.length == 0) {
			selectedColor = 'none'; //기본 컬러가 없을 경우 none 으로 설정.
		}
		  
		let productNo = $("input#productno").val(); //제품번호
		
		// 제품번호 구매수량 색상 가격 수량 포인트
		let quantity = $("#product__quantity").val(); //구매수량
		
		
		$("input[name='str_cart_qty']").val(quantity); //구매수량을 input에 넣음
		$("input[name='selectedColor']").val(selectedColor); //컬러를 input에 넣음
		

		$.ajax({
            type: "POST",
            url: "goCheckOutDetailJSON.flex", 
            data: {"productNo":productNo,
            	   "selectedColor":selectedColor,
            	   "quantity":quantity},
            dataType: "json",
            success: function(json) {
				console.log("ajax 요청 성공~~", json);			

                if (json.loginRequired) { //로그인안했을경우
                    alert(json.message);
                    location.href = contextPath + "/login/login.flex"; // 로그인 페이지로 이동
                } else {
 					//console.log("json.str_pdPriceArr:", json.str_pdPriceArr);
                    
                    $("input#productpoint").val(json.productpoint);
                    $("input#str_pd_detailno").val(json.str_pd_detailno);
                    $("input#str_pdPriceArr").val(json.str_pdPriceArr);
                    $("input#str_pdPointArr").val(json.str_pdPointArr);
                    /// ---------------------------------
				
					const frm = document.buyFrm;
					frm.action =  contextPath + "/order/checkOut.flex";
					frm.method = "post";
					frm.submit();
                }
             },
            error: function(request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });

      }); // 장바구니로 이동하는 함수 종료
      
      
      
      
      
    //////////////////////////////////////// 리뷰 작성 js start /////////////////////////////////////////////
    /* ------------------------------리뷰창-------------------- */
	let ratingSelect = 5;
	
	function initializeRateYo() {
	    $("#rateYo").rateYo({
	        rating: ratingSelect, // 초기 별점
	        fullStar: true, // 별점을 정수로만 설정할 경우
	        starWidth: "30px", // 별의 크기 조정
	        ratedFill: "#ff9f00",
	        normalFill: "#ddd"
	    }).on("rateyo.set", function (e, data) {
	        // 사용자가 별점을 선택했을 때 실행되는 이벤트
	        $("#ratingValue").text(data.rating); // 선택한 별점을 표시
	        ratingSelect = data.rating;
	        console.log("The rating is :" + data.rating); // 선택한 별점을 콘솔에 출력
	    });
	}
	initializeRateYo(); // 초기화
	
	const writeReview = $('#writeReview a');
	const reviewPopup = $('#reviewPopup');
	const closeBtn = $('.close');
	const submitReviewBtn = $('#submitReviewBtn');
 	reviewPopup.hide();
	
	writeReview.click(function(e) {
	    e.preventDefault();
	    ratingSelect = 5; // 별점을 5점으로 초기화
	    $("#rateYo").rateYo("option", "rating", ratingSelect); // 별점 설정
	    reviewPopup.show();
	});
	
	closeBtn.click(function() {
	    reviewPopup.hide();
	});
	
	$(window).click(function(event) {
	    if ($(event.target).is(reviewPopup)) {
	        reviewPopup.hide();
	    }
	});
	
	submitReviewBtn.click(function() {
	    const reviewText = $('#reviewText').val().replace(/\s/g, ''); // 공백을 제거한 리뷰 값
	    let productNo = $("input#productno").val(); //제품번호
	    if (reviewText.length < 20) {
	        alert('리뷰는 최소 20글자 이상 작성해야 합니다.');
	        return;
	    }
	
	    // 리뷰를 제출하는 로직을 여기에 추가
	    // ajax로 보내야 함 -> 별점 + 리뷰쓰기 내용 + 로그인한 유저 아이디로 ajax로 보내서 컨트롤러에서 받은 값으로 db에 인서트
	    console.log('리뷰 내용:', $('#reviewText').val());
	    console.log('별점:', ratingSelect);
		console.log('해당제품번호:',  productNo);
	    reviewPopup.hide();
	
	    $.ajax({
	        type: "POST",
	        url: "reviewJSON.flex",
	        data: {
	            'reviewText': reviewText,
	            'rating': ratingSelect,
	            'productNo': productNo
	        },
	        dataType: "json",
	        success: function(json) {
	            console.log("AJAX 요청 성공:", json);
             
                if (json.loginRequired) { //로그인안했을경우
                    alert(json.message);
                    location.href = contextPath + "/login/login.flex"; // 로그인 페이지로 이동
                } else {
					alert(json.message);
                }
	        },
	        error: function(request, status, error) {
	            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
	        }
	    });
	});

});
	// Getter
	var normalFill = $("#rateYo").rateYo("option", "fullStar"); //returns true
	 
	// Setter
	$("#rateYo").rateYo("option", "fullStar", true); //returns a jQuery Element
	
	
	

