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
            return false ; // 검색어가 비어 있을 때 폼 제출 방지
        }

        location.href="/search?keyword="+inputText;
    });
    // -- 검색 버튼 클릭 finish--


    //--검색후 웹툰클릭시 이동 start--
    $('section').on('click', 'article.webtoonlist', (e) => {
        let toonId = $(e.currentTarget).find('img').attr('alt');
        location.href = `/toondetail?toonId=${toonId}`;
    });
    //--검색후 웹툰클릭시 이동 finish--

   });