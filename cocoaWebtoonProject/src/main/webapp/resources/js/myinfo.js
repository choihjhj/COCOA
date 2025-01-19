$(() => {
    //-- 로그아웃 start --
    $("#logout").click((e) => {
        e.preventDefault();
        const confirmed = confirm('정말로 로그아웃 하시겠습니까?');
        if (confirmed) {
            $("#logoutForm").submit()
        }
    });
    //-- 로그아웃 end --	

    //-- 회원탈퇴 start --
    $("#remove").click((e) => {
        e.preventDefault()
        const confirmed = confirm('정말로 회원탈퇴 하시겠습니까?');
        if (confirmed) {
            $.ajax({
                url: "/remove", // 서버의 회원탈퇴 엔드포인트
                type: "DELETE",
                success: (response) => {
                    alert("회원탈퇴가 완료되었습니다.");
                    location.href = "/"; // 회원탈퇴 후 메인 페이지로 이동
                },
                error: (xhr, status, error) => {
                    alert("회원탈퇴에 실패했습니다. 다시 시도해주세요.");
                    console.error("Error:", error);
                },
            });
        }
    })
    //-- 회원탈퇴 end --

});