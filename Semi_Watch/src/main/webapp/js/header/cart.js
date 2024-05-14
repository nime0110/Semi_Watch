const cart = (
  function(){
    // 장바구니에 새로운 상품 항목을 만드는 함수
    function createItemToCart(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice){
      return {
        item_id: productId, // 상품 ID
        quantity, // 상품 수량
        thumb_URL: thumbImageURL, // 상품 이미지 URL
        name: productName, // 상품 이름
        discount_price: discountPrice, // 할인 가격
        total_price: totalPrice // 총 가격
      }
    }

    // 새로운 상품을 장바구니에 추가하는 함수
    function addNewItem(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice){
      // sessionStorage에서 상품 정보를 가져옴
      let item = JSON.parse(sessionStorage.getItem(productId))
      let flagAlreadyExisted = false // 이미 존재하는 상품 여부를 나타내는 플래그

      // 이미 존재하는 상품이면 수량을 누적하여 업데이트
      if(item !== null){
        flagAlreadyExisted = true
        quantity = parseInt(quantity) + parseInt(item.quantity)
      }

      // 새로운 상품 항목 생성
      let newItem = createItemToCart(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice)
      
      // sessionStorage에 새로운 상품 정보 저장
      sessionStorage.setItem(newItem.item_id, JSON.stringify(newItem))
      
      // DOM에 이미 존재하는 상품인지 표시하는 프로퍼티 추가
      newItem.existsInDOM = flagAlreadyExisted
      
      // 새로운 상품 항목 반환
      return newItem
    }

    // sessionStorage에서 모든 상품 항목을 로드하는 함수
    function loadAllItems(){
      const items = []
      let sessionStorageLength = sessionStorage.length
      
      // sessionStorage에서 모든 항목을 반복하여 배열에 추가
      for(let i = 0; i <= sessionStorageLength; i++){
        let item = sessionStorage.getItem(`item-cart-${i}`)
        if(item !== null){
          item = JSON.parse(item)
          items.push(item)
        }
      }
      return items // 모든 상품 항목 반환
    }

    // 장바구니에 있는 상품의 총 수량을 계산하는 함수
    function getCartSize(){
      const items = loadAllItems() // 모든 상품 항목을 불러옴
      let total = 0
      
      // 각 상품의 수량을 누적하여 총 수량을 계산
      for(let {quantity} of items){
        total += parseInt(quantity)
      }
      return total // 총 수량 반환
    }

    // 장바구니에서 특정 상품을 삭제하는 함수
    function deleteItem(productId){
      sessionStorage.removeItem(productId) // sessionStorage에서 해당 상품 제거
    }

    // 모듈의 공개 인터페이스 정의
    return {
      addNewItem,
      getCartSize,
      loadAllItems,
      deleteItem
    }
  }
)()
