$(() => {
	// --페이지 로드시 url의 값 얻기 start--
	const urlParams = new URLSearchParams(window.location.search);
	const epIdFromUrl = parseInt(urlParams.get('epId'));
	const toonIdFromUrl = parseInt(urlParams.get('toonId'));
	// --페이지 로드시 url의 값 얻기 finish--


	// 로컬 스토리지에 저장될 데이터 개수 제한
	const MAX_DATA_COUNT = 20;

	// 로컬 스토리지에서 데이터 가져오기
	function getStoredData() {
		const storedData = localStorage.getItem('storedData');
		return storedData ? JSON.parse(storedData) : [];
	}

	// 툰 아이디와 에피소드 번호 저장 또는 업데이트
	function storeToonIdAndEpNumber(toonId, epNumber, epId) {
		// 저장된 데이터 가져오기
		const storedData = getStoredData();

		// 중복 여부 확인 (toonId만 고려)
		const existingDataIndex = storedData.findIndex(item => item.toonId === toonId);

		if (existingDataIndex !== -1) {
			// 중복되는 경우 해당 데이터 제거
			storedData.splice(existingDataIndex, 1);
		}

		// 현재 데이터를 배열에 추가 (배열의 맨 뒤로 가게 됨)
		storedData.push({ toonId, epNumber, epId });

		// 데이터 개수가 MAX_DATA_COUNT를 초과하는 경우 가장 오래된 데이터를 제거
		if (storedData.length > MAX_DATA_COUNT) {
			storedData.shift(); // 배열의 첫 번째 요소 제거 (가장 오래된 데이터)
		}

		// 배열을 다시 문자열로 변환하여 저장
		localStorage.setItem('storedData', JSON.stringify(storedData));
	}

	storeToonIdAndEpNumber(toonIdFromUrl, epNumberz, epIdFromUrl);





	// -- 해당 회차의 이미지의 파일을 출력 start --
	function loadImageWhile(index) {
		const imageUrl = `/resources/image/episode/${epIdFromUrl}_${index}.png`;
		const imgElement = new Image(); // 이미지 엘리먼트 생성
		imgElement.onload = function () {
			// 이미지가 로드되면, 이미지를 DOM에 추가하고 다음 이미지 로드
			$('.webtoon-image-container').append($(this));
			loadImageWhile(index + 1);
		};
		imgElement.onerror = function () {
			// 이미지 로딩에 실패하면, 기본 동작 중지 (에러 메시지 표시 안 함)
		};
		imgElement.src = imageUrl;
		$(imgElement).addClass('webtoon-image');
	}
	loadImageWhile(1);
	// -- 해당 회차의 이미지의 파일을 출력 finish --


	// //--최신댓글 가져오기 start--
	$('#latest').click((e) => {
		$('#best').removeClass('active');
		$('#latest').addClass('active');
		$.ajax({
			url: "/lastestComment?epId=" + epIdFromUrl,
			method: 'get',
			success: (responseObj) => {
				if (responseObj != null) {
					$(".comments").html(responseObj);
				}
			},
			error: function (xhr) {
				alert('Error: ' + xhr.status);
			}
		});
	})
	// //--최신댓글 가져오기 end--


	// //--베스트댓글 가져오기 start--
	$('#best').click((e) => {
		$('#latest').removeClass('active');
		$('#best').addClass('active');
		$.ajax({
			url: "/bestComment?epId=" + epIdFromUrl,
			method: 'get',
			success: (responseObj) => {
				if (responseObj != null) {
					$(".comments").html(responseObj);
				}
			},
			error: function (xhr) {
				alert('Error: ' + xhr.status);
			}
		});
	})
	// //--베스트댓글 가져오기 finish--




	//--목록버튼 클릭시 start
	$('.category-button').click((e) => {
		e.preventDefault(); // 기본 동작(링크 이동)을 막습니다.
		location.href = `/toondetail?toonId=${toonIdFromUrl}`
	})
	//--목록버튼 클릭 finish

	// 이전 버튼 클릭
	$('.prev-button').click((e) => {
		e.preventDefault();
		const currentIndex = EpIds.indexOf(epIdFromUrl);
		if (currentIndex !== -1 && currentIndex > 0) {
			const prevEpId = EpIds[currentIndex - 1];
			location.href = `/episode?toonId=${toonIdFromUrl}&epId=${prevEpId}`;
		}
	});

	// 다음 버튼 클릭
	$('.next-button').click((e) => {
		e.preventDefault();
		const currentIndex = EpIds.indexOf(epIdFromUrl);
		if (currentIndex !== -1 && currentIndex < EpIds.length - 1) {
			const nextEpId = EpIds[currentIndex + 1];
			location.href = `/episode?toonId=${toonIdFromUrl}&epId=${nextEpId}`;
		}
	});


	const userId = $('#userId').val();

	//--댓글 좋아요 클릭 start--
	$(document).on('click', '.likeButton', function (e) {


		if (userId == '') {
			alert('로그인 후 좋아요가 가능합니다.');
		} else {

			const commentId = $(this).data('commentid');
			//const isLiked = $(this).hasClass('liked'); 


			if (commentId !== undefined) {
				$.ajax({
					url: `/like/${commentId}`,
					method: 'post', // Change to POST method
					dataType: 'json', // Expect JSON response
					success: (response) => {

						const countElement = $(this).siblings('.comment-likes');
						let count = parseInt(countElement.text());
						if (response === true) { //좋아요 성공                                   
							count++;
							// $(this).removeClass('liked');
						} else { //좋아요취소 성공

							count--;
							// $(this).addClass('liked');
						}
						countElement.text(count);
					},
					error: (xhr, status, error) => {
						console.error('실패:', status, error);
					}
				});
			}

		}



	});
	//--댓글 좋아요 클릭 finish--

	const scrollToTopBtn = $('#up');
	scrollToTopBtn.click(() => {
		$('html, body').animate({ scrollTop: 0 }, 500);  // 500ms(0.5초) 동안 스크롤
	});


	//--댓글 작성 start--
	function redirectToLogin() {
		let currentURL = window.location.href;
		location.href = "/login?origin=comment&redirect=" + encodeURIComponent(currentURL);
	}


	$('#writebutton').click((e) => {
		console.log("클릭")
		e.preventDefault();

		if ($("#write").attr("readonly")) {
			var confimLogin = confirm("로그인 하시겠습니까?")
			if (confimLogin) {
				redirectToLogin();
			} else {
				return;
			}
		}

		const writedata = $('#write').val().trim();
		if (userId != '') {
			if (writedata == "") {
				alert('댓글을 작성해주세요')
				return
			} else {


				$.ajax({

					url: "/newcomment",
					method: 'post',
					data: {
						"writedata": writedata,
						"epid": epIdFromUrl

					},
					success: (responseObj) => {
						if (responseObj == 1) {

							$('#latest').trigger('click');
							$('#write').val('')
						}

					},
					error: function (xhr) {
						alert('Error: ' + xhr.status);
					}

				})
			}
		}
	})

	//--댓글 작성 finish--


	//--댓글 삭제 start--
	$(document).on('click', '.removeButton', function (e) {
		//var commentId = $(this).siblings(".likeButton").data("commentid");
		var commentId = $(this).closest(".comment-item").find(".likeButton").data("commentid");
		e.preventDefault();

		$.ajax({
			url: "/removecomment",
			method: 'post',
			data: {
				"commentId": commentId
			},
			success: (responseObj) => {
				if (responseObj == 1) {
					alert("댓글이 삭제되었습니다");
					$('#latest').trigger('click');

				}
			},
			error: function (xhr) {
				alert('Error: ' + xhr.status);
			}
		})
	});
	//--댓글 삭제 finish--

	const modbtn = $(".modifyButton")
	//-- 댓글 수정 start --
	$(document).on('click', '.modifyButton', function (e) {

		const commentId = $(this).closest(".comment-item").find(".likeButton").data("commentid");
		const remove = $(this).siblings(".removeButton");
		const commentBody = $(this).closest('.comment-item').find('.commentbody');
		const currentText = commentBody.text();


		e.preventDefault();

		commentBody.replaceWith('<input class="comment-input" maxlength="50" value="' + currentText + '">');

		$(this).text('저장').removeClass('modifyButton').addClass('saveButton');
		$(remove).text('취소').removeClass('removeButton').addClass('cancelButton');


		$('.saveButton').click((e) => {
			const modifydata = $(this).closest('.comment-item').find('.comment-input').val().trim();

			$.ajax({
				url: "/modifycomment",
				method: "post",
				data: {
					"commentId": commentId,
					"modifydata": modifydata
				},
				success: (responseObj) => {
					if (responseObj = 1) {
						alert("댓글이 수정되었습니다");
						$('#latest').trigger('click');
					}
				},
				error: function (xhr) {
					alert('Error: ' + xhr.status);
				}
			});

		});


	   $('.cancelButton').click((e) => {
	    const activeSortButton = $('.sort-button.active');
	    if (activeSortButton.length) {
	        activeSortButton.trigger('click');
	    }
	});
  });
	//-- 댓글 수정 finish -- 


});