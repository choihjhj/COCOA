$(() => {
	const recentList = $('#recentList');
	const purchaseList = $('#purchaseList');
	const recentMain = $('#recent');
	const purchaseMain = $('#purchase')
	const p = $('#noresult1')

	// 화면을 처음 로드할 때 최근 목록을 보여주도록 설정
	function getRecentList() {
		$("#purchaseContainer").hide();
		recentList.addClass('active');

		// 로컬 스토리지에서 저장된 toonIds 배열 가져오기
		function getStoredData() {
			const storedData = localStorage.getItem('storedData');
			return storedData ? JSON.parse(storedData) : [];
		}
		const storedData = getStoredData();

		if (storedData.length === 0) {
			p.show()

			recentMain.find('section').hide();
		} else {
			recentMain.find('section').show();
			p.hide()
			// 원본 article 요소 선택
			const originalArticle = recentMain.find('section').children(':first-child');
			// 각 toonId에 대해 이미지 엘리먼트 생성 및 추가
			storedData.forEach(data => {
				const toonId = data.toonId;
				const epNumber = data.epNumber;
				const epId = data.epId;

				// 새로운 article 요소 생성 복사
				const newArticle = originalArticle.clone();

				// 이미지 엘리먼트에 src 속성 설정
				newArticle.find('.bg').attr('src', `/resources/image/bg/${toonId}_bg.jpg`);
				newArticle.find('.person').attr('src', `/resources/image/person/${toonId}_person.png`);
				newArticle.find('.title').attr('src', `/resources/image/title/${toonId}_title.png`);

				// p 태그에 epNumber 값을 설정
				newArticle.find('.epNumber').text(`${epNumber}화`);

				// section에 새로운 article 추가 (원본 article 요소 뒤에 추가됨)
				originalArticle.after(newArticle);

				newArticle.click(() => {
					location.href=`/episode?toonId=${toonId}&epId=${epId}`
				});
			});

			// 첫 번째 요소를 화면에서 숨김
			recentMain.find('section').find('> article:first-child').hide();
		}
	}
	getRecentList();

	recentList.click(() => {
		$("#recentContainer").show();
		$("#purchaseContainer").hide();

		recentList.addClass('active');
		purchaseList.removeClass('active');
	})

	purchaseList.click(() => {
		$("#recentContainer").hide();
		$("#purchaseContainer").show();

		recentList.removeClass('active');
		purchaseList.addClass('active');
	})

// purchaseContainer 내에 있는 article 요소를 클릭할 때 data-toonId 값을 가져오기
$('#purchaseContainer article').click(function() {
	const toonId = $(this).data('toonid'); // 'toonid'를 소문자로 사용
	const epId = $(this).data('epid'); // 'toonid'를 소문자로 사용
	// alert(toonId); // 또는 toonId를 사용하여 원하는 작업 수행
	// alert(epId)
	location.href=`/episode?toonId=${toonId}&epId=${epId}`
  });
  
	

});
