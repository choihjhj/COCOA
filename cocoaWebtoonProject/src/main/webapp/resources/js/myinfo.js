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
			$("#removeForm").submit()
		}
	})
	//-- 회원탈퇴 end --

});