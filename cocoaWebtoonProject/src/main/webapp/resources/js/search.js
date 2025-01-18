$(() => {

    // --페이지 로드시 url의 값을 검색창에 표시 start--
    const urlParams = new URLSearchParams(window.location.search);
    const keywordFromUrl = urlParams.get('keyword');
    if (keywordFromUrl) {
        $("#input").val(keywordFromUrl);
    }
    // --페이지 로드시 url의 값을 검색창에 표시 finish--


    // -- 검색 버튼 클릭 start--
    $("#searchform").submit((e) => {
        e.preventDefault()

        // 입력된 텍스트
        let inputText = $("#input").val();


        if (inputText.trim() === '') {
            alert("키워드를 입력하세요");
            return false; // 검색어가 비어 있을 때 폼 제출 방지
        }

        // Ajax 요청
        $.ajax({
            url: "/search/ajax",
            method: "GET",
            data: { keyword: inputText },
            dataType: "json",
            success: (data) => {
                renderWebtoons(data);
            },
            error: (xhr, status, error) => {
                console.error("Error during Ajax request:", error);
            }
        });

    });
    // -- 검색 버튼 클릭 finish--

    // 웹툰 목록 렌더링
    const renderWebtoons = (webtoons) => {
        const $list = $("#webtoon-list");
        $list.empty(); // 기존 결과 제거

        if (webtoons.length === 0) {
            $list.append("<p>검색 결과가 없습니다.</p>");
            return;
        }

        webtoons.forEach(webtoon => {
            const html = `
                <article class="webtoonlist" data-toon-id="${webtoon.toonId}">
                    <img class="bg" src="/resources/image/bg/${webtoon.toonId}_bg.jpg" alt="${webtoon.toonId}">
                    <img class="person" src="/resources/image/person/${webtoon.toonId}_person.png">
                    <img class="title" src="/resources/image/title/${webtoon.toonId}_title.png">
                </article>
            `;
            $list.append(html);
        });
    };

    //--검색후 웹툰클릭시 이동 start--
    $('section').on('click', 'article.webtoonlist', (e) => {
        let toonId = $(e.currentTarget).find('img').attr('alt');
        location.href = `/toondetail?toonId=${toonId}`;
    });
    //--검색후 웹툰클릭시 이동 finish--

});