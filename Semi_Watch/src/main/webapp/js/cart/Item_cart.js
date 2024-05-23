
$(document).ready(function(){
	
	// 주문 수량 바뀌면 주문 금액에 값 바꿔서 넣어주기
	
	$(".led").bind("change", function(e){
		const su = $(e.target).val();
		//const danga = $("input:hidden[class='danga']").val();
		const price = (Number(su)*Number(danga));
		// console.log(price);
		const idx = $(".led").index($(e.target));
		const $price = $("span[class='item-price']").eq(idx);
		
		let tota = Number($("span#sum").text());
	
		const eachList = document.querySelectorAll("spant[class = 'item-price']");
		
		});
	
});