
$(document).ready(function(){
		
	let init_sum_real_price = 0; // 결제 예정금액의 초기치
		
	$("span#item-price").each(function(index, elmt){
			const real_price = Number($(elmt).text().split(",").join(""));
			init_sum_real_price += real_price;
	});	
	

	// 결제 예정금액에 금액을 초기치로 넣어주기	
	$("span#sumPrice").html(init_sum_real_price.toLocaleString('en'));
//	$("span#totalPrice").html(init_total_real_price.toLocaleString('en'));
		
	// 주문 수량 바뀌면 주문 금액에 값 바꿔서 넣어주기
	$(".led").bind("change", function(e){
		
		const idx = $(".led").index($(e.target));//해당 변화된 요소의 index 가져오기
		
		const $danga = $("div#danga").eq(idx);
		
		// const $sumAmount = $("span.order-amount").eq(idx);
				
		const n_danga = $danga.text().split(",").join("");
		
		console.log("n_danga : ", n_danga);
		console.log("n_danga 의 타입 ", typeof n_danga);
	
		const su = $(e.target).val(); //수량
		console.log("su : ", su);
	//	console.log("su 의 타입 : ", typeof su);
		
		
		const pointset = Number(Number(su) * Number(n_danga));
	//	Number(Number(su) * Number(n_danga))  이렇게 묶어서 숫자로 변환하려고 시도함. 
			
		$("span#item-price").eq(idx).text(pointset.toLocaleString('en')); // 기초 단가만 가격에 , 가 잡히고 다음부터는 , 없이 실행되었는데 다시 잡음.
	//  $("span#item-price").eq(idx).text(pointset.toLocaleString()); 이 부분의 타입이 String임.
	
	    //////////////////////////////////////////////////////////////////////////
	    let sum_real_price = 0;
	    
	    $("span#item-price").each(function(index, elmt){
			const real_price = Number($(elmt).text().split(",").join(""));
			sum_real_price += real_price;
		});
	    
	   /* 
	    let total_real_price = 0;
	    
	    $("span#totalPrice").each(function(index, elmt){
			const total_real_price = Number($(elmt).text().split(",").join(""));
			total_real_price += real_price;
		});
	   */
	    

	    // 결제 예정금액에 누적된 금액을 넣어주기
	    $("span#sumPrice").html(sum_real_price.toLocaleString('en'));
//	  	$("span#sumPrice").html(total_real_Price.toLocaleStirng('en'));
	    //////////////////////////////////////////////////////////////////////////
	});
		

});