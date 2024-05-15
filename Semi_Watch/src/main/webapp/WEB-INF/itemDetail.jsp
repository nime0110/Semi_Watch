<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header1.jsp" />
  <main>
    <article class="product">
      <section class="product__slider default-container" aria-label="Product preview">
        <button type="button" class="product__slider___btn-close-lightbox">
          <span class="sr-only" >Close lightbox</span>
          <span class="icon icon-close" aria-hidden="true"></span>
        </button>  
        <div class="image-box" aria-label="Product preview" role="region">
          <button type="button" class="btn-previousImage">
            <span class="sr-only">Previous Image</span>
            <span class="icon icon-previous" aria-hidden="true"></span>
          </button>
          <button type="button" class="btn-nextImage">
            <span class="sr-only">Next Image</span>
            <span class="icon icon-next" aria-hidden="true"></span>
          </button>
          <img src="images/image-product-1.jpg" alt="Brown and white sneaker" class="image-box__src" data-product-id="item-cart-1" tabindex="0" aria-controls="lightbox" aria-expanded="false">
        </div>
        
        <ul class="product__thumbs default-container" aria-label="Product thumbnails">
          <li class="thumb-item">
            <button type="button" class="thumb-item__btn" aria-label="change to image 1">
              <img src="images/image-product-1-thumbnail.jpg" alt="" data-thumb-index="0" role="presentation">
            </button>
          </li>
          <li class="thumb-item">
            <button type="button" class="thumb-item__btn" aria-label="change to image 2">
              <img src="images/image-product-2-thumbnail.jpg" alt="" data-thumb-index="1" role="presentation">
            </button>
          </li>
          <li class="thumb-item">
            <button type="button" class="thumb-item__btn" aria-label="change to image 3">
              <img src="images/image-product-3-thumbnail.jpg" alt="" data-thumb-index="2" role="presentation">
            </button>
          </li>
          <li class="thumb-item">
            <button type="button" class="thumb-item__btn" aria-label="change to image 4">
              <img src="images/image-product-4-thumbnail.jpg" alt="" data-thumb-index="3" role="presentation">
            </button>
          </li>
        </ul>
      </section>
      <section class="product__content default-container" aria-label="Product content">
        <header>
          <h2 class="company-name" tabindex="0">CASIO</h2>
          <p class="product__name" tabindex="0"></p>
          <h3 class="product__title" tabindex="0">GA-2100-2A2DR</h3>
        </header>
        <p class="product__description" tabindex="0">
          터프니스를 추구하며 진화를 계속하는 G-SHOCK에서, 세부적인 파트까지 모두 원톤으로 완성한 One tone 시리즈입니다. 베이스 모델은 옥타곤 베젤의 GA-2100을 채용하고, 인기 있는 컬러링을 케이스, 베젤, 밴드, 다이얼의 각 파트에 이르기까지 원톤으로 통일하여 전체적으로 깊이감이 있는 모델로 완성했습니다. 스트릿 패션에도 최적화된 캐주얼하고 스타일링의 포인트로 활용하기 좋은 컬러 모델입니다.
        </p>
        <div class="product__price">
          <div class="discount-price">
            <span>￦</span>
            <p class="discount-price__value" tabindex="0">
              260,000 
              <span class="sr-only"></span>
            </p>
            <p class="discount-price__discount" tabindex="0">50%</p>
          </div>
          <div class="full-price">
            <p tabindex="0">
              135,000 
              <span class="sr-only"></span>
            </p>
          </div>
        </div>
        
        <form action="#" class="cart-form">
          <div class="cart-form__input-container" aria-label="Define the product quantity">
            <button type="button" class="btn-changeValue minus-item">
              <span class="sr-only">Minus one item</span>
              <span class="icon icon-minus" aria-hidden="true"></span>
            </button>
            <label for="product__quantity" class="sr-only">Set the quantity manually</label>
            <input type="number" min="0" value="0" id="product__quantity">
            <button type="button" class="btn-changeValue plus-item">
              <span class="sr-only">Plus one item</span>
              <span class="icon icon-plus" aria-hidden="true"></span>
            </button>
          </div>
          <button type="button" class="cart-form__add-btn">
            <span class="icon icon-cart" aria-hidden="true"></span>
            Add to cart
          </button>
        </form>
      </section>
    </article>
  </main>
  
  <div class="lightbox" id="lightbox" role="dialog"></div>
  <div id="infobox">
    <div id="info_leftbox">
      <div id="infobox1">
        <h2>온라인 스토어에서 구매한 모든 제품은 실물 보증서가 동봉되지 않습니다.</h2>
        <ol>
          <li>온라인 스토어에서는 구매 시 입력한 수취인(혹은 선물 받은 이) 정보를 기준으로 전산 보증서로 자동 등록됩니다.<br>
            - 별도의 지류 보증서는 발급이 불가한 점 참고 부탁드립니다.</li>
          <li> 온라인 스토어 구매 시 출고 전 밴드 조절 서비스가 불가하며, 가까운 공식 매장 방문하시면 조절해 드리고 있습니다. <br>
            (사전 연락 후 방문 권장)</li>
          <li> 한글 설명서는 포함되어 있지 않으며, 제품 상세페이지 내 [MANUAL]을 클릭하시면 확인이 가능합니다. </li>
        </ol>
      </div>
      <div id="infobox2">
        <h2>[착용 시 주의사항] </h2>
        <ol>
          <li>화기에 가까이 두면 제품에 변형이 생길 수 있습니다.</li>
          <li>장기간 미사용 시에는 땀, 수분 등을 부드러운 천으로 닦아내고, 고온 다습한 장소를 피하여 보관하세요. </li>
          <li>체질에 따라서 알레르기가 일어날 수 있습니다.</li>
        </ol>
        </div>
        <div id="infobox3">
          <h2>[배송 안내]</h2>
          <ol>
            <li> 배송 방법 : 우체국 택배 배송비 : 5만원 이상 구매 시 무료배송<br>
              - 단순 변심으로 인한 반품 또는 교환 시 왕복 배송비는 고객님 부담입니다.<br>
              - 제주 및 도서 산간 지역에 따라 배송비가 추가 부과될 수 있습니다. </li>
            <li>배송 기간 : 평일 14:30 이전 주문 건은 당일 출고되며, 평균 배송일은 출고일로부터 평일 기준 1~3일 소요됩니다.<br>
              - 특정 이벤트 혹은 기타 상황에 따라 배송이 지연될 수 있습니다.</li>
          </ol>
        </div>
      </div>
    <div id="info_rightbox">
        <div id="infobox4">
          <h2>[A/S 접수 안내]</h2>
          <p>
          <span>• A/S 관련 문의 : 02-3143-0718 (1번)</span>
          A/S 방문 접수 시 보증서를 지참해 주시고, 보증서가 없는 경우 유상 수리만 가능합니다.
          </p>
          </p>
        </div>
        <div id="infobox5">
          <h2>[교환 및 반품 안내]</h2>
          <ol>
            <li>제품은 배송 완료일로부터 착용하지 않은 상태에서 7일 이내에 교환, 반품이 가능합니다.</li>
            <li>그외 기본 택배 사용시 물품에 택배비(2,500)을 넣으신후 선불로 보내주시면<br>
            됩니다.</li>
            <li>제주도나 도서산간 지역은 배송비가 추가될수 있습니다.</li>
            <li>반품택배 접수는 CJ택배 이용시 인터넷으로 접수하시면 더욱 편리합니다.<br>
            (일반택배 착불로 접수)</li>
            <li>CJ택배이외 타 택배 이용시는 꼭 선불로 보내주시고 최초배송비 2,500원 동봉해 보내<br>
            주시면 됩니다.</li>
            <li>반품 및 교환시 내용을 메모에 작성후 상자안에 넣어주시면 보다 신속하고 정확하게<br>
            처리를 받으실수 있습니다.</li>
          </ol>
        </div>
      </div>
  </div>
