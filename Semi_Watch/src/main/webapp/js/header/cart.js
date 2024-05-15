$(document).ready(function() {
	console.log("ㅈㅂㅈㅂㅈㅂㅈ");
  const cart = (function() {
    // 장바구니에 새로운 상품 항목을 만드는 함수
    function createItemToCart(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice) {
      return {
        item_id: productId,
        quantity: quantity,
        thumb_URL: thumbImageURL,
        name: productName,
        discount_price: discountPrice,
        total_price: totalPrice
      };
    }

    // 새로운 상품을 장바구니에 추가하는 함수
    function addNewItem(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice) {
      let item = JSON.parse(sessionStorage.getItem(productId));
      let flagAlreadyExisted = false;

      if (item !== null) {
        flagAlreadyExisted = true;
        quantity = parseInt(quantity) + parseInt(item.quantity);
      }

      let newItem = createItemToCart(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice);
      sessionStorage.setItem(newItem.item_id, JSON.stringify(newItem));
      newItem.existsInDOM = flagAlreadyExisted;

      return newItem;
    }

    // sessionStorage에서 모든 상품 항목을 로드하는 함수
    function loadAllItems() {
      const items = [];
      for (let i = 0; i < sessionStorage.length; i++) {
        let key = sessionStorage.key(i);
        if (key.startsWith('item-cart-')) {
          let item = sessionStorage.getItem(key);
          if (item !== null) {
            items.push(JSON.parse(item));
          }
        }
      }
      return items;
    }

    // 장바구니에 있는 상품의 총 수량을 계산하는 함수
    function getCartSize() {
      const items = loadAllItems();
      return items.reduce((total, item) => total + parseInt(item.quantity), 0);
    }

    // 장바구니에서 특정 상품을 삭제하는 함수
    function deleteItem(productId) {
      sessionStorage.removeItem(productId);
    }

    // 모듈의 공개 인터페이스 정의
    return {
      addNewItem,
      getCartSize,
      loadAllItems,
      deleteItem
    };
})
});