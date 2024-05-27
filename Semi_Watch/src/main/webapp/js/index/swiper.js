// const mySwiper = new Swiper('.swiper-container', {
//     // 옵션 설정
//     direction: 'horizontal', // 슬라이드 방향
//     loop: true, // 루프 설정
//     scrollbar: {
//         el: '.swiper-scrollbar',
//     }
// });
// Initialize Swiper
document.addEventListener('DOMContentLoaded', (event) => {
  const swiper = new Swiper('.swiper__coverflow', {
    // Optional parameters
    direction: 'horizontal',
    loop: true,
    slidesPerView: 3,
    effect: 'coverflow',

    // If we need pagination
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },

    // Navigation arrows
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },

    // Scroll bar
    scrollbar: {
      el: '.swiper-scrollbar',
    },
  });

  const swiper__slide = new Swiper('.swiper__slide', {
    // Optional parameters
    direction: 'horizontal',
    loop: true,
    slidesPerView: 3,

    // If we need pagination
    pagination: {
      el: '.swiper-slide-pagination',
      clickable: true,
    },

    // Navigation arrows
    navigation: {
      nextEl: '.swiper-slide-button-next',
      prevEl: '.swiper-slide-button-prev',
    },

    // Scroll bar
    scrollbar: {
      el: '.swiper-scrollbar',
    },
  });
});

// <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" /><!-- Swiper.js CSS -->
//   <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script><!-- Swiper.js JS -->
//   <script src="js/swiper.js"></script>
