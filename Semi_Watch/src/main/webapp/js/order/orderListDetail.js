$(document).ready(function() {
	
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)); // 컨텍스트 패스 
    //////////////////////////////////////// 리뷰 작성 js start /////////////////////////////////////////////
    /* ------------------------------리뷰 작성하기시작 -------------------- */
	let ratingSelect = 5;
	

    $("#rateYo").rateYo({
        rating: ratingSelect, // 초기 별점
        fullStar: true, // 별점을 정수로만 설정할 경우
        starWidth: "30px", // 별의 크기 조정
        ratedFill: "#ff9f00",
        normalFill: "#ddd"
    }).on("rateyo.set", function (e, data) {
        // 사용자가 별점을 선택했을 때 실행되는 이벤트
        //$("#ratingValue").text(data.rating); // 선택한 별점을 표시
        ratingSelect = data.rating;
        console.log("The rating is :" + data.rating); // 선택한 별점을 콘솔에 출력
    });


	const reviewPopup = $('#reviewPopup');
	
	$('#revieWrite').click(function(e) {
	    reviewPopup.show();
	    e.preventDefault();
	    $("#rateYo").rateYo("option", "rating", ratingSelect);
	    $('#productno').val($(this).data('productno')); // 버튼의 데이터 속성에서 제품 번호를 설정
	});
	
	// 리뷰 팝업창에서 x버튼 눌렀을떄 (공통)
	$('.close').click(function() { 
	    $('#reviewPopup').hide();
	    $('#reviewUpdatePopup').hide();
	    $('#reviewText').val(" "); //리뷰텍스트초기화 
	    ratingSelect = 5; //별점초기화
	});
	
	// 리뷰작성하기버튼 눌렀을떄 ajax통신 
	$('#submitReviewBtn').click(function() {
	    const reviewText = $('#reviewText').val();
	    let productNo = $('#productno').val(); // 히든 필드에서 제품 번호 가져오기

	    if (reviewText.length < 20) {
	        alert('리뷰는 최소 20글자 이상 작성해야 합니다.');
	        return;
	    }
	
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
	            if (json.loginRequired) {
	                alert(json.message);
	                location.href = contextPath + "/login/login.flex";
	            } else {
	                alert(json.message);
	                location.reload();//페이지새로고침 
	            }
	        },
	        error: function(request, status, error) {
	            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
	        }
	    });
	    $('#reviewPopup').hide();
	});

    /* ------------------------------리뷰 작성하기끝 -------------------- */
    
    
    /* ------------------------------리뷰 수정하기시작 -------------------- */
    
    const updateRating =  $("#rateYoUpdate").data('rating');
    const reviewUpdatePopup = $("#reviewUpdatePopup");
    
     $("#rateYoUpdate").rateYo({
        rating: updateRating, // 초기 별점
        fullStar: true, // 별점을 정수로만 설정할 경우
        starWidth: "30px", // 별의 크기 조정
        ratedFill: "#ff9f00",
        normalFill: "#ddd"
    }).on("rateyo.set", function (e, data) {
        // 사용자가 별점을 선택했을 때 실행되는 이벤트
        //$("#ratingValue").text(data.rating); // 선택한 별점을 표시
        ratingSelect = data.rating;
        console.log("수정하기별점 :" + data.rating); // 선택한 별점을 콘솔에 출력
    });


	
	$('#reviewChange').click(function(e) {
	    reviewUpdatePopup.show();
	    e.preventDefault();
	    $("#rateYoUpdate").rateYo("option", "rating", updateRating);
	    $('#productno').val($(this).data('productno')); // 버튼의 데이터 속성에서 제품 번호를 설정
	});
    
    
    // 리뷰수정하기버튼 눌렀을떄 ajax통신 
	$('#submitReviewUpdateBtn').click(function() {
	    let reviewText = $('#reviewUpdateText').val();
	    console.log(reviewText);
	    let productNo = $('#productno').val(); // 히든 필드에서 제품 번호 가져오기
	    if (reviewText.length < 20) {
	        alert('리뷰는 최소 20글자 이상 작성해야 합니다.');
	        return;
	    }
	
	    $.ajax({
	        type: "POST",
	        url: "reviewUpdateJSON.flex",
	        data: {
	            'reviewText': reviewText,
	            'rating': ratingSelect,
	            'productNo': productNo
	        },
	        dataType: "json",
	        success: function(json) {
	            if (json.loginRequired) {
	                alert(json.message);
	                location.href = contextPath + "/login/login.flex";
	            } else {
	                alert(json.message);
	                location.reload();//페이지새로고침 
	            }
	        },
	        error: function(request, status, error) {
	            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
	        }
	    });
	    reviewUpdatePopup.hide();
	});
    

    
    /* ------------------------------리뷰 수정하기끝 -------------------- */
    
    /* ------------------------------리뷰 삭제하기시작 -------------------- */
    
 
    // 리뷰수정하기버튼 눌렀을떄 ajax통신 
	$('#reviewDelete').click(function() {

	    let productNo = $('#productno').val(); // 히든 필드에서 제품 번호 가져오기
	    $.ajax({
	        type: "POST",
	        url: "reviewDeleteJSON.flex",
	        data: {
	            'productNo': productNo
	        },
	        dataType: "json",
	        success: function(json) {
	            if (json.loginRequired) {
	                alert(json.message);
	                location.href = contextPath + "/login/login.flex";
	            } else {
	                alert(json.message);
	                location.reload();//페이지새로고침 
	            }
	        },
	        error: function(request, status, error) {
	            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
	        }
	    });
	    reviewUpdatePopup.hide();
	});    
    
    /* ------------------------------리뷰 삭제하기끝 -------------------- */


});


	// Getter
	var normalFill = $("#rateYo").rateYo("option", "fullStar"); //returns true
	 
	// Setter
	$("#rateYo").rateYo("option", "fullStar", true); //returns a jQuery Element
	
	
    
