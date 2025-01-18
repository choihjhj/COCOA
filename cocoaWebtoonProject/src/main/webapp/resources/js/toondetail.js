$(() => {
	//-- 회차 클릭시이동 start --
	$('div.container').on('click', 'a.episode', (e) => {
	e.preventDefault()	

	const pricestate = $(e.currentTarget).find('.pricestate').text().trim();
	const toonId = $('.parent').data('toon-id');
	const episodeId = $(e.currentTarget).data('episode-id');

	if(pricestate == "유료"){
		var confirmPurchase = confirm("이 에피소드를 구매하시겠습니까?");
		if (confirmPurchase) {
			location.href = `/purchase?toonId=${toonId}&epId=${episodeId}`;
		}
	} else {
		//무료 & 구매완료
		location.href = `/episode?toonId=${toonId}&epId=${episodeId}`;
	}
	})

})
