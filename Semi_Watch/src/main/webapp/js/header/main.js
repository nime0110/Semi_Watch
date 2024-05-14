const mainJs = (
  function(){
    'strict'
    const navBtn = document.querySelector('.top-header__left .nav-btn')
    const mainNav = document.querySelector('.top-header__left .main-nav')
    const mainNavCloseBtn = document.querySelector('.main-nav__close-btn')
    const mainNavContentContainer = document.querySelector('.main-nav__content-container')
    const btnCart = document.querySelector('.top-header__btn-cart')
    const btnUser = document.querySelector('.top-header .user-container')
    const cartSection = document.querySelector('.cart-section')
    const itemsCounter = document.querySelector('.top-header__btn-cart .items-quantity')
    const cartForm = document.querySelector('.cart-form')
    const inputProductQuantity = document.getElementById('product__quantity') 
    const cartProducts = document.querySelector('.cart-section__products')
    const emptyMsg = document.querySelector('.empty-msg')
    const cartBody = document.querySelector('.cart-section__body')
    const originalProductSlider = document.querySelector('.product__slider')
    const lightbox = document.querySelector('.lightbox')

    var modalLightbox = null
    var productIndex = 0
    inputProductQuantity.value = 0
    manageItemsCounter(cart.getCartSize())
    updateCartItems()
    adjustAriaAttributesOnBtnMenu()
    initProductSliders()
 






    navBtn.addEventListener('click', toggleMenu)
    mainNavCloseBtn.addEventListener('click',toggleMenu)
    btnCart.addEventListener('click', toggleCart)
    btnUser.addEventListener('click', toggleBtnCheckout)
    cartForm.addEventListener('click', manageFormClicks)
    cartSection.addEventListener('click', manageCartClicks)
    
       // 제품 슬라이더 초기화
    function initProductSliders(){
      updateLightboxContent()
      document.querySelectorAll('.product__slider').forEach( element => {
        element.addEventListener('click', manageProductClicks)
        element.addEventListener('keydown', manageProductClicks)
      })
      autoSelectFirstThumbItem()
    }
     // 라이트박스 컨텐츠 업데이트
    function updateLightboxContent(){
      const clonedElement = originalProductSlider.cloneNode(true)
      clonedElement.classList.add('--lightbox-active')
      lightbox.appendChild(clonedElement)
    }
    // 버튼 메뉴의 ARIA 속성 조정
    function adjustAriaAttributesOnBtnMenu(){
      if(window.matchMedia(`(min-width: 992px)`).matches){
        navBtn.removeAttribute('aria-controls')
        navBtn.removeAttribute('aria-expanded')
      }
    }
        // 첫 번째 썸네일 항목을 자동 선택
    function autoSelectFirstThumbItem(){
      document.querySelectorAll('.product__thumbs').forEach( listThumbs => {
        let hasThumbSelected = false
        Array.from(listThumbs).forEach( element => {
          if(element.querySelector('.thumb-item__btn').classList.contains('--selected')){
            hasThumbSelected = true
          }
        })
        if(hasThumbSelected === false){
          listThumbs.children[0].querySelector('.thumb-item__btn').classList.add('--selected')
        }
      })
    }
     // 문서 오버플로우 토글
    function toggleDocumentOverflow(){
      document.documentElement.classList.toggle('--overflow-hidden')
      document.body.classList.toggle('--overflow-hidden')
    }
      // 메뉴 토글
    function toggleMenu(){
      toggleDocumentOverflow()
      mainNav.classList.toggle('active')
      mainNavContentContainer.classList.toggle('active')
      if(mainNav.classList.contains('active')){
        navBtn.setAttribute('aria-expanded', true)
      }
      else{
        navBtn.setAttribute('aria-expanded', false)
      }
    }
       // 장바구니 버튼 클릭했을때
    function toggleCart(){

      if(cartSection.classList.contains('active')){
        cartSection.style.display = "none"
        btnCart.setAttribute('aria-expanded', false)
      }
      else{
        cartSection.style.display = "block"
        btnCart.setAttribute('aria-expanded', true)
      }
      setTimeout(() => cartSection.classList.toggle('active'), 100)
      btnCart.classList.toggle('--active')
    }

      // 장바구니 상품 토글
    function toggleCartProducts(){
      if(cart.getCartSize() <= 0 && emptyMsg.classList.contains('--deactivate')){
        emptyMsg.classList.remove('--deactivate')
        cartProducts.classList.remove('--active')
        cartBody.classList.remove('--with-items')
      }
      else{
        emptyMsg.classList.add('--deactivate')
        cartProducts.classList.add('--active')
        cartBody.classList.add('--with-items')
      }
    }
       // 결제 버튼 토글
    function toggleBtnCheckout(){
      const btnCheckout = document.querySelector('.cart-section__btn-checkout')
      btnCheckout.classList.toggle('--active')
    }
    // 카트 관련 폼 클릭을 관리
    function manageFormClicks(event){
      // 클릭된 요소를 가져오기
      let element = event.target;
      if(!element.matches(`.cart-form, #product__quantity, .cart-form__input-container`)){
        if(element.matches(`.icon-cart, .cart-form__add-btn`)){
          const notValidEntries = ['+', '-', 'e']
          //수량에 유효하지 못한 문자가 있는지
          let validFlag = true //수량 유효성 검사용
          let quantity = inputProductQuantity.value //제품 수량 입력값
          notValidEntries.forEach(element => { //수량에 유효하지 못한 글자가 있는지 확인
            if(quantity.indexOf(element) !== -1){
              validFlag = false
            }
          })
          if(validFlag){
            if(quantity === "0"){
              // 수량이 "0"인 경우 경고를 표시하고 입력값을 0으로 설정
              alert('Please determine the product quantity.')
              inputProductQuantity.value = 0
            }
            else{
              // 제품 ID, 수량, 썸네일 이미지 URL, 제품 이름, 할인된 가격, 총 가격을 추출하여 새로운 카트 아이템을 추가
              const productId = originalProductSlider.querySelector('.image-box__src').getAttribute('data-product-id')
              const thumbImageURL = `images/image-product-${productId.split('-')[2]}-thumbnail.jpg`
              const productName = document.querySelector('.product__name').textContent.trim()
              let discountPrice = document.querySelector('.discount-price__value').textContent.trim()
              discountPrice = discountPrice.trim();
              discountPrice = parseInt(discountPrice.replace(/,/g, ''));//230000 이렇게 쉼표제거하고 출력
              // console.log("discountPrice" + discountPrice);

              let totalPrice = document.querySelector('.full-price').textContent.trim()
              totalPrice = totalPrice.trim()
              totalPrice = parseInt(totalPrice.replace(/,/g, ''));//230000 이렇게 쉼표제거하고 출력
              // console.log("totalPrice" + totalPrice);
              const newItem = cart.addNewItem(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice)
                // 카트 아이템 수를 업데이트
              manageItemsCounter(cart.getCartSize())
               // 카트 아이템을 업데이트.
              updateCartItems([newItem])
            }
          }
          else{
            alert('Invalid quantity of the product!')
            inputProductQuantity.value = 0
          }
        }
        else{
          let newValue = 0
          if(element.matches(`.plus-item, .icon-plus`)){
            newValue = parseInt(inputProductQuantity.value) + 1
          }
          else if(inputProductQuantity.value != "0"){
            newValue = parseInt(inputProductQuantity.value) - 1
          }
          inputProductQuantity.value = newValue
        }
        
      }
    }
    // 클릭하면 lightbox 뜸
    function manageProductClicks(event){

      // alert("확인");
      let element = event.target
      let key = event.key
      const actionCondition = (event.type === "keydown" && key === "Enter") || event.type === "click"
      if(element.matches(`p`)){
        element = element.parentElement
        
      }
      if(element.matches(`.image-box__src[tabindex='0']`) && window.matchMedia(`(min-width: 992px)`).matches && actionCondition){
        event.preventDefault()
        zoomProductImage(event)
        document.body.style.overflow = "hidden"; //높이 잘라주기
      }
      else if(actionCondition){
        event.preventDefault()
        if(element.matches(`[data-thumb-index], .thumb-item__btn`)){
          const localProductSlider = element.closest(`.product__slider`)
          const product = localProductSlider.querySelector('.image-box__src')
          if(element.classList.contains('thumb-item__btn')){
            element = element.querySelector('[data-thumb-index]')
          }
          productIndex = parseInt(element.getAttribute('data-thumb-index'))
          slideProductImage('', product.getAttribute('data-product-id'), product)
        }
        else if(element.matches(`.icon-previous, .icon-next, .btn-previousImage, .btn-nextImage`)){
          if(element.classList.contains(`icon`)){
            element = element.closest('button')
          }
          const product = element.closest('.image-box').querySelector('.image-box__src')
          let operation = element.classList.contains('btn-nextImage') ? '+' : '-'
          slideProductImage(operation, product.getAttribute('data-product-id'), product)
        }
        else if(element.matches(`.icon-close, .product__slider___btn-close-lightbox`)){
          setTimeout(() => {
            lightbox.style.display = ''
            modalLightbox.removeEvents();
            document.body.style.overflow = ""; 
          })
        }        
      }
      
    }

    function zoomProductImage(){
      lightbox.classList.add('--active')
      setTimeout(() => {
        lightbox.style.display = 'flex'
        modalLightbox = new Modal(lightbox)
      }, 200)
    }
    function slideProductImage(operator, productId, image){
      let productImagesLength = products.get(productId).length - 1
      if(operator === '+'){
        productIndex = productIndex < productImagesLength ? productIndex + 1 : 0  
      }
      else if(operator === '-'){
        productIndex = productIndex > 0 ? productIndex - 1 : productImagesLength
      }
      const localProductSlider = image.closest('.product__slider')
      localProductSlider.querySelector('.thumb-item__btn.--selected').classList.remove('--selected')
      let elementToSelect = localProductSlider.querySelector(`[data-thumb-index='${productIndex}']`)
      elementToSelect.closest('button').classList.add('--selected')
      
      let {full_img_url: fullImgURL} = products.get(productId)[productIndex]
      image.classList.add('--changed')
      setTimeout(() => {
        image.classList.remove('--changed')
        image.src = fullImgURL
      }, 200)
    }
    function manageItemsCounter(quantity){
      itemsCounter.querySelector('.value').textContent = quantity;
      if(!itemsCounter.classList.contains('active') && quantity > 0){
        itemsCounter.classList.add('active')
      }
      else if(quantity <= 0){
        itemsCounter.classList.remove('active')
      }
    }
    function manageCartClicks(event){
      let element = event.target 
      if(element.matches("img:not(.product__thumb)")){
        element = element.parentElement
      }
      if(element.classList.contains('btn-del-product')){
        const itemToDelete = element.closest('[data-item-id]')
        cart.deleteItem(itemToDelete.getAttribute('data-item-id'))
        itemToDelete.parentElement.remove()
        manageItemsCounter(cart.getCartSize())
        toggleCartProducts()
      }
    }
    function updateCartItems(item){
      const items = item || cart.loadAllItems()
      if(items.length > 0){
        toggleCartProducts()
        items.forEach(element => {
          //상품이 이미 카트애 들어와 있으면 수 / 최종가격 업데이트 
          if(element.existsInDOM){
            const elementInDOM = document.querySelector(`[data-item-id=${element.item_id}]`)
            elementInDOM.querySelector('.price-calculation__value .quantity').textContent = element.quantity
            elementInDOM.querySelector('.final-price__span').textContent = (parseInt(element.discount_price) * parseInt(element.quantity)).toLocaleString();
            console.log(elementInDOM);
          }
          else{
            cartProducts.appendChild(createDOMCartElement(element))
          }
        })
      }
      
    }
    
   



    function createDOMCartElement(item){
      const li = document.createElement('li');
      // 총 가격 계산
      const totalPrice =item.discount_price * item.quantity;
      const formattedPrice = totalPrice.toLocaleString("ko-KR");
      const liContent = `
      <a href="#" class="list-item" data-item-id="${item.item_id}">
        <img src="${item.thumb_URL}" alt="" class="product__thumb">
        <div class="list-item__abstract">
          <h4 class="title">${item.name}</h4>
          <div class="price-calculation">
            <p class="price-calculation__value">
              <span class="value__span">
                ${item.discount_price.toLocaleString("ko-KR")}
                <span class="sr-only">dollars</span>
              </span>
              <span class="sr-only">by</span>
              <span aria-hidden="true">x</span>
              <span class="quantity">${item.quantity}</span>
            </p>
          </div>
          <p class="price-calculation__final-price">
          
            <span class="final-price__span">${formattedPrice}</span>
            <span class="sr-only">dollars on total</span> 
          </p>
        </div>
        <button type="button" class="btn-del-product">
          <span class="sr-only">Delete this product</span>
          <img src="images/icon-delete.svg" alt="" role="presentation">
        </button>
      </a>
    `;
  
  
      li.innerHTML = liContent;
      return li;
    }
    


    return {
      toggleDocumentOverflow
    }
  }
)()