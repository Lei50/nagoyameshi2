$(function () {
  // slickを利用してカルーセルを実装する
  $('.slider').slick({
    arrows: false,
    dots: true,
    autoplay: true,
    fade: true,
    speed: 1500,
    pauseOnHover: false
  });
});