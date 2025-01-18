$(() => {
    const epId =$('#form').data('episode-id')
    const toonId = $('.webtoon-info').data('toon-id')

    // -- 충전하기 버튼 누를시 페이지 이동 start --
    $('.charge_btn').click((e) => {
        var confirmPurchase = confirm("충전 하시겠습니까?");
        if (confirmPurchase) {
        location.href = `/charge?epId=${epId}`;
        }
    });
    // -- 충전하기 버튼 누를시 페이지 이동 end --

    
    //--취소버튼 누를시 뒤로start---
    $('.cancel_btn').click((e) => {
        location.href = `/toondetail?toonId=${toonId}`;
    });
    //--취소버튼 end---



    // --구매 버튼 클릭시 start-- 
      // 가격/잔액 추출
    function extractNumberFromElement(selector) {
        const element = $(selector);
        const text = element.text();
        return parseFloat(text.match(/\d+(\.\d+)?/)[0]);
    }
    const price = extractNumberFromElement(".section.price");
    const cocoaBalance = extractNumberFromElement(".section.balance");

    $('.purchase_btn').click((e) => {
        e.preventDefault(); // 폼 전송을 막음
        if (cocoaBalance < price) {
            alert('코코아 잔액이 부족합니다.');
        } else {
            var confirmPurchase = confirm("구매 하시겠습니까?");
            if (confirmPurchase) {
            $('#purchase').submit();
            }
        }
    });
    // 구매 버튼 클릭시 end--

});
