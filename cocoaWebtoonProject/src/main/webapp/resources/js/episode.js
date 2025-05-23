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
    $(document).on('click', '.likeButton, .dislikeButton', function (e) {
        e.preventDefault(); // 기본 동작 방지 (form 전송 같은)

        const commentId = $(this).data('commentid');  // 댓글 ID 가져오기
        const isLiked = $(this).data('isliked'); // 현재 좋아요 상태 가져오기 (0 또는 1)

        let url;
        let method;
        

        if (isLiked === 1) {
            // 좋아요 클릭 시: 취소 요청 (DELETE)
            url = `/removeLike/${commentId}`;
            method = 'DELETE';  // 좋아요 취소는 DELETE           
        } else {
            // 싫어요 클릭 시: 추가 요청 (POST)
            url = `/like/${commentId}`;
            method = 'POST';     // 좋아요 추가는 POST
        }

        // AJAX 요청
        $.ajax({
            url: url,
            method: method,
            dataType: 'json',  // JSON 응답을 기대
            success: (response) => {
                const countElement = $(this).siblings('.comment-likes');  // 좋아요 수가 있는 요소
                let count = parseInt(countElement.text());

                if (response === true) {  // 좋아요 추가
                    count++;
                    $(this).data('isliked', 1);  // 좋아요 상태를 1로 업데이트
                } else {  // 좋아요 취소
                    count--;
                    $(this).data('isliked', 0);  // 좋아요 상태를 0으로 업데이트
                }

                // 좋아요 수 업데이트
                countElement.text(count);
            },
            error: (xhr, status, error) => {
                console.error('실패:', status, error);  // 에러 처리
            }
        });


    });


    //--댓글 좋아요 클릭 finish--

    const scrollToTopBtn = $('#up');
    scrollToTopBtn.click(() => {
        $('html, body').animate({ scrollTop: 0 }, 500);  // 500ms(0.5초) 동안 스크롤
    });


    //--댓글 작성 start--
    function redirectToLogin() {
        let currentURL = window.location.href;
        location.href = "/login?origin=episode&redirect=" + encodeURIComponent(currentURL);
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
                        "commentBody": writedata,
                        "epId": epIdFromUrl

                    },
                    dataType: "text",
                    cache: false,
                    success: (responseObj) => {
                        if (responseObj === "success") {

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
        console.log('삭제' + commentId);
        e.preventDefault();

        $.ajax({
            url: "/removecomment?commentId=" + commentId,
            method: 'DELETE',
            data: {
                "commentId": commentId
            },
            dataType: "text",
            cache: false,
            success: (responseObj) => {
                if (responseObj === "success") {
                    alert("댓글이 삭제되었습니다");
                    $('#latest').trigger('click');
                } else {
                    alert("댓글 삭제 실패. 다시 시도해 주세요.");
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
            console.log('수정' + commentId);
            const modifydata = $(this).closest('.comment-item').find('.comment-input').val().trim();

            $.ajax({
                url: "/modifycomment",
                method: "PUT",
                contentType: "application/json", // JSON 데이터 전송
                data: JSON.stringify({
                    commentId: commentId,
                    commentBody: modifydata
                }),
                dataType: "text",
                cache: false,
                success: (responseObj) => {
                    if (responseObj === "success") {
                        alert("댓글이 수정되었습니다");
                        $('#latest').trigger('click');
                    } else {
                        alert("댓글 수정 실패. 다시 시도해 주세요.");
                    }
                },
                error: function (xhr, status, error) {
                    alert('Error: ' + xhr.status + " " + error);
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