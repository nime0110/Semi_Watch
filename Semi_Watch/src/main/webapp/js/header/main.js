$(document).ready(function() {
  const mainJs = (function() {
    const $navBtn = $('.top-header__left .nav-btn');
    const $mainNav = $('.top-header__left .main-nav');
    const $mainNavCloseBtn = $('.main-nav__close-btn');
    const $mainNavContentContainer = $('.main-nav__content-container');
    const $btnCart = $('.top-header__btn-cart');
    const $btnUser = $('.top-header .user-container');
    const $cartSection = $('.cart-section');
    const $itemsCounter = $('.top-header__btn-cart .items-quantity');
    const $cartForm = $('.cart-form');
    const $inputProductQuantity = $('#product__quantity');
    const $cartProducts = $('.cart-section__products');
    const $emptyMsg = $('.empty-msg');
    const $cartBody = $('.cart-section__body');
    const $originalProductSlider = $('.product__slider');
    const $lightbox = $('.lightbox');

    var modalLightbox = null;
    var productIndex = 0;
    $inputProductQuantity.val(0);
    manageItemsCounter(cart.getCartSize());
    updateCartItems();
    adjustAriaAttributesOnBtnMenu();
    initProductSliders();

    $navBtn.click(toggleMenu);
    $mainNavCloseBtn.click(toggleMenu);
    $btnCart.click(toggleCart);
    $btnUser.click(toggleBtnCheckout);
    $cartForm.click(manageFormClicks);
    $cartSection.click(manageCartClicks);

    function initProductSliders() {
      updateLightboxContent();
      $('.product__slider').on('click keydown', manageProductClicks);
      autoSelectFirstThumbItem();
    }

    function updateLightboxContent() {
      const $clonedElement = $originalProductSlider.clone(true);
      $clonedElement.addClass('--lightbox-active');
      $lightbox.append($clonedElement);
    }

    function adjustAriaAttributesOnBtnMenu() {
      if (window.matchMedia('(min-width: 992px)').matches) {
        $navBtn.removeAttr('aria-controls aria-expanded');
      }
    }

    function autoSelectFirstThumbItem() {
      $('.product__thumbs').each(function() {
        let hasThumbSelected = false;
        $(this).find('.thumb-item__btn').each(function() {
          if ($(this).hasClass('--selected')) {
            hasThumbSelected = true;
          }
        });
        if (!hasThumbSelected) {
          $(this).children().first().find('.thumb-item__btn').addClass('--selected');
        }
      });
    }

    function toggleDocumentOverflow() {
      $('html, body').toggleClass('--overflow-hidden');
    }

    function toggleMenu() {
      toggleDocumentOverflow();
      $mainNav.toggleClass('active');
      $mainNavContentContainer.toggleClass('active');
      $navBtn.attr('aria-expanded', $mainNav.hasClass('active'));
    }

    function toggleCart() {
      if ($cartSection.hasClass('active')) {
        $cartSection.hide();
        $btnCart.attr('aria-expanded', false);
      } else {
        $cartSection.show();
        $btnCart.attr('aria-expanded', true);
      }
      setTimeout(function() {
        $cartSection.toggleClass('active');
      }, 100);
      $btnCart.toggleClass('--active');
    }

    function toggleCartProducts() {
      if (cart.getCartSize() <= 0 && $emptyMsg.hasClass('--deactivate')) {
        $emptyMsg.removeClass('--deactivate');
        $cartProducts.removeClass('--active');
        $cartBody.removeClass('--with-items');
      } else {
        $emptyMsg.addClass('--deactivate');
        $cartProducts.addClass('--active');
        $cartBody.addClass('--with-items');
      }
    }

    function toggleBtnCheckout() {
      $('.cart-section__btn-checkout').toggleClass('--active');
    }

    function manageFormClicks(event) {
      const $target = $(event.target);
      if (!$target.is('.cart-form, #product__quantity, .cart-form__input-container')) {
        if ($target.is('.icon-cart, .cart-form__add-btn')) {
          const notValidEntries = ['+', '-', 'e'];
          let quantity = $inputProductQuantity.val();
          let isValid = !notValidEntries.some(entry => quantity.includes(entry));

          if (isValid) {
            if (quantity === "0") {
              alert('Please determine the product quantity.');
              $inputProductQuantity.val(0);
            } else {
              const productId = $originalProductSlider.find('.image-box__src').data('product-id');
              const thumbImageURL = `images/image-product-${productId.split('-')[2]}-thumbnail.jpg`;
              const productName = $('.product__name').text().trim();
              let discountPrice = parseInt($('.discount-price__value').text().replace(/,/g, ''));
              let totalPrice = parseInt($('.full-price').text().replace(/,/g, ''));

              const newItem = cart.addNewItem(productId, quantity, thumbImageURL, productName, discountPrice, totalPrice);
              manageItemsCounter(cart.getCartSize());
              updateCartItems([newItem]);
            }
          } else {
            alert('Invalid quantity of the product!');
            $inputProductQuantity.val(0);
          }
        } else {
          let newValue = parseInt($inputProductQuantity.val()) + ($target.is('.plus-item, .icon-plus') ? 1 : -1);
          $inputProductQuantity.val(Math.max(0, newValue));
        }
      }
    }


  function manageProductClicks(event) {
    let $target = $(event.target);
    if ($target.is('p')) {
      $target = $target.parent();
    }
    const actionCondition = (event.type === "keydown" && event.key === "Enter") || event.type === "click";

    if (actionCondition) {
      event.preventDefault();
      if ($target.is('.image-box__src[tabindex="0"]') && window.matchMedia('(min-width: 992px)').matches) {
        zoomProductImage(event);
        $('body').css('overflow', 'hidden');
      } else if ($target.is('[data-thumb-index], .thumb-item__btn')) {
        const $localProductSlider = $target.closest('.product__slider');
        const $product = $localProductSlider.find('.image-box__src');
        productIndex = parseInt($target.data('thumb-index') || $target.find('[data-thumb-index]').data('thumb-index'));
        slideProductImage('', $product.data('product-id'), $product);
      } else if ($target.is('.icon-previous, .icon-next, .btn-previousImage, .btn-nextImage')) {
        const $product = $target.closest('.image-box').find('.image-box__src');
        let operation = $target.hasClass('btn-nextImage') ? '+' : '-';
        slideProductImage(operation, $product.data('product-id'), $product);
      } else if ($target.is('.icon-close, .product__slider___btn-close-lightbox')) {
        setTimeout(function() {
          $lightbox.hide();
          if (modalLightbox) modalLightbox.removeEvents();
          $('body').css('overflow', '');
        });
      }
    }
  }

  function zoomProductImage() {
    $lightbox.addClass('--active').show();
    setTimeout(function() {
      modalLightbox = new Modal($lightbox.get(0));
    }, 200);
  }

  function slideProductImage(operator, productId, $image) {
    let productImagesLength = products.get(productId).length - 1;
    if (operator === '+') {
      productIndex = productIndex < productImagesLength ? productIndex + 1 : 0;
    } else if (operator === '-') {
      productIndex = productIndex > 0 ? productIndex - 1 : productImagesLength;
    }
    const $localProductSlider = $image.closest('.product__slider');
    $localProductSlider.find('.thumb-item__btn.--selected').removeClass('--selected');
    let $elementToSelect = $localProductSlider.find(`[data-thumb-index='${productIndex}']`);
    $elementToSelect.closest('button').addClass('--selected');

    let fullImgURL = products.get(productId)[productIndex].full_img_url;
    $image.addClass('--changed');
    setTimeout(function() {
      $image.removeClass('--changed').attr('src', fullImgURL);
    }, 200);
  }

  function manageItemsCounter(quantity) {
    $itemsCounter.find('.value').text(quantity);
    if (!quantity) {
      $itemsCounter.removeClass('active');
    } else {
      $itemsCounter.addClass('active');
    }
  }

  function manageCartClicks(event) {
    const $target = $(event.target).closest('.btn-del-product, img:not(.product__thumb)');
    if ($target.hasClass('btn-del-product')) {
      const itemId = $target.closest('[data-item-id]').data('item-id');
      cart.deleteItem(itemId);
      $target.closest('.list-item').remove();
      manageItemsCounter(cart.getCartSize());
      toggleCartProducts();
    }
  }

  function updateCartItems(items) {
    items = items || cart.loadAllItems();
    if (items.length > 0) {
      toggleCartProducts();
      items.forEach(function(item) {
        if (item.existsInDOM) {
          const $elementInDOM = $(`[data-item-id='${item.item_id}']`);
          $elementInDOM.find('.price-calculation__value .quantity').text(item.quantity);
          $elementInDOM.find('.final-price__span').text((parseInt(item.discount_price) * parseInt(item.quantity)).toLocaleString());
        } else {
          $cartProducts.append(createDOMCartElement(item));
        }
      });
    }
  }

  function createDOMCartElement(item) {
    const totalPrice = item.discount_price * item.quantity;
    const formattedPrice = totalPrice.toLocaleString("ko-KR");
    return $('<li>').html(`
      <a href="#" class="list-item" data-item-id="${item.item_id}">
        <img src="${item.thumb_URL}" alt="" class="product__thumb">
        <div class="list-item__abstract">
          <h4 class="title">${item.name}</h4>
          <div class="price-calculation">
            <p class="price-calculation__value">
              <span class="value__span">${item.discount_price.toLocaleString("ko-KR")}</span>
              <span class="sr-only">dollars</span>
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
    `);
  }

  return {
    toggleDocumentOverflow
  }
})();
});