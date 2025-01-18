$(() => {

	var webtoons = [];

	const days = {
		"일": 7,
		"월": 1,
		"화": 2,
		"수": 3,
		"목": 4,
		"금": 5,
		"토": 6
	  };
	  
	// --요일 클릭 start--
	$('nav a').click(function(e) {
		e.preventDefault();
		var dayNumber = $(this).text();
		var dayOfWeek = days[dayNumber];

		location.href = '/layout?dayOfWeek='+dayOfWeek;
	});
	// --요일 클릭 finish--
	  


	//-- 웹툰클릭시이동 start --
	$('section').on('click', 'article.webtoonlist', (e) => {
		const toonId = $(e.currentTarget).find('img').attr('alt');
		location.href = '/toondetail?toonId='+toonId;
	});
	//-- 웹툰클릭시이동 finish --
	

	// --움직이는 메인이미지 가져오기 start--
	const anime = Math.floor(Math.random() * 32) + 1;

	$('.banner').attr("src", `/resources/image/banner/${anime}_banner.jpg`)
		.attr('alt', anime);
	$('.box video source').attr('src', `/resources/video/${anime}.webm`);
	$('.box video source').attr('data-src', `/resources/video/${anime}.webm`);
	$('.webtoon-title').attr('src', `/resources/image/title/${anime}_title.png`)
	$('.box video')[0].load();


	$('.box').on('click', (e) => {
		const toonId = $(e.currentTarget).find('.banner').attr('alt');

		location.href = '/toondetail?toonId='+toonId;
	});
	// --움직이는 메인이미지 가져오기 finish--












});
