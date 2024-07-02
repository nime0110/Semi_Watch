/*  아래 코드로 사진 변경 가능 */
  $(document).ready(function() {
   	const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 
	let productNo = $("input#productno").val(); //제품번호
	loadReviewPage(1); //리뷰 페이지 로드
	  // 숫자만 입력되도록 제한
	  
        $('#product__quantity').on('input', function() {
            if (/[^0-9]/.test(this.value)) {
                this.value = 1;
            }
        }).on('blur', function() {
            if (this.value === '' || isNaN(this.value)) {
                this.value = 1;
            }
        });
	 
  	$('ul#choice li button img').on('click',function(){
	    var i = $(this).attr('src');
		console.log(i);
	    $('.image-box img').attr('src',i);
	    $('ul#choice li button').removeClass("color_clicked");
	    $(this).closest("button").addClass("color_clicked");
	    
	    let button = $(this).closest();
	    console.log(button);
	    return false;
	    /* css 효과 주기*/
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
                    
                    // ajax 요청 성공하고 나서 hidden에 들어감.. 
                    $("input#productpoint").val(json.productpoint);
                    $("input#str_pd_detailno").val(json.str_pd_detailno);
                    $("input#str_pdPriceArr").val(json.str_pdPriceArr);
                    $("input#str_pdPointArr").val(json.str_pdPointArr);
                    /// ---------------------------------
				
					if(!json.isPdQtyOk) {
						alert("구매하신 수량만큼의 재고가 부족하여 구매하실 수 없습니다.");
						return;
					}
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
      

	/* 리뷰 보여주기 ----------*/


	//  리뷰가 없을때의 평균 별점 --------------------------
	$("#rateYozero").rateYo({
		    rating: 0,
		    readOnly: true
		  });
	
	$(".rateYoOne").each(function() {
	var ratingOne = $(this).data("rating");
	$(this).rateYo({
	rating: ratingOne,
	readOnly: true
	});
	});

	//리뷰 글 하나하나의 별점
	// Getter
	var readOnly = $(".rateYoOne").rateYo("option", "readOnly"); //returns true
	
	// Setter
	//$(".rateYoOne").rateYo("option", "readOnly", false); //returns a jQuery Element


});
var currentPage = 1; // 현재 페이지를 추적
function loadReviewPage(pageNo) {
	  let productNo = $("input#productno").val(); //제품번호

	  $.ajax({
        url: 'getReviewsBypnumJSON.flex',
        type: 'GET',
        data: {
            "pdno": productNo, // 제품 번호
            "currentShowPageNo": pageNo,
            "sizePerPage": 4 // 한 페이지당 리뷰 수 -4개 보여줄 예정
        },
       success: function(response) {
            let reviews;
            let totalPage;
            let avgStarpoint;
            let reviewCount;

            try {
                reviews = JSON.parse(response);
                totalPage = reviews.pop().totalPage; // 마지막 객체에서 totalPage 추출

                // 첫 번째 리뷰 객체에서 avg_starpoint와 reviewcount 추출
                if (reviews.length > 0) {
                    avgStarpoint = reviews[0].avg_starpoint;
                    reviewCount = reviews[0].reviewcount;
                } else {
                    avgStarpoint = "0";
                    reviewCount = "0";
                }
            } catch (e) {
                console.error('JSON 파싱 에러!! :', e);
                return;
            }

            // 평균별점 / 리뷰수 넣기
            $("#rateView > h4 > span").text(avgStarpoint); // 평균별점
            $("#rateAndCount > h4 > span").text(reviewCount); // 리뷰수
            $(".reviewCountli").text(reviewCount); // 리뷰수
		
            // 평균 별점 초기화
            $("#avgRateYo").rateYo({
                rating: parseFloat(avgStarpoint),
                readOnly: true,
                starWidth: "20px"
            });
		    
            if (Array.isArray(reviews)) {
                displayReviews(reviews);
                displayPagination(totalPage, pageNo);
            } else {
                console.error('Reviews 에러:', reviews);
            }
        },
        error: function(error) {
            console.error('ajax 오류:', error);
        }
    });
}

function displayReviews(reviews) {
    var reviewsDiv = $('#reviewBoard table');
    reviewsDiv.empty(); // 기존 리뷰 제거

    if (reviews.length === 0) {
		
       
        reviewsDiv.append('<div id="notReviewDiv"><p id="notReviewP"> 아직 상품 리뷰가 작성되지 않은 상품입니다. <br>첫 상품 리뷰 작성자가 되어보세요!</p> </div>');
        return;
    }

    reviews.forEach(function(review) {
        var reviewHtml = '<tr>';
        reviewHtml += '<td><div id="ratingDiv">';
        reviewHtml += '<div class="rateYoOne" data-rating="' + review.starpoint + '"></div>';
        reviewHtml += '<p class="ratingCount">별점 <span class="ratingOneSpan">' + review.starpoint + '</span>점</p>';
        reviewHtml += '</div><p>' + review.review_content + '</p></td>';
        reviewHtml += '<td><p><span>작성일자</span>' + review.review_date + '</p>';
        reviewHtml += '<p><span>작성자</span>' + review.fk_usermask + '</p></td>';
        reviewHtml += '</tr>';
        reviewsDiv.append(reviewHtml);
    });
       // Initialize rateYo for each review
    $(".rateYoOne").each(function() {
        var rating = $(this).data("rating");
        $(this).rateYo({
            rating: rating,
            readOnly: true,
            starWidth: "20px"
        });
    });
}

function displayPagination(totalPage, currentPage) {
    var paginationDiv = $('#rpageNumber');
    paginationDiv.empty(); // 기존 페이지 버튼 제거

	if(totalPage != 0) {
	    paginationDiv.append('<li><a href="#" data-page="1">[맨처음]</a></li>');
	
	
	    for (var i = 1; i <= totalPage; i++) {
	        var listItem = $('<li></li>');
	        var link = $('<a style="font-size: large;"></a>').attr('href', '#').text(i).attr('data-page', i);
	
	        if (i === currentPage) {
	            listItem.addClass('active');
	        }
	
	        listItem.append(link);
	        paginationDiv.append(listItem);
	    }
	
	
	    paginationDiv.append('<li><a href="#" data-page="' + totalPage + '">[마지막]</a></li>');
	}
    // 페이지넘버클릭시에 
    $('#rpageNumber a').on('click', function(e) {
        e.preventDefault();
        var page = $(this).data('page');
        currentPage = page; // 전역 변수 업데이트
        loadReviewPage(page);
        
        // 모든 링크의 active 클래스를 제거하고 클릭된 링크에 추가->글씨진해지는 css
        $('#rpageNumber a').removeClass('active');
        $(this).addClass('active');
    });
}
