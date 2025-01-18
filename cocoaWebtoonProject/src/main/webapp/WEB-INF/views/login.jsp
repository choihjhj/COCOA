<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>로그인</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/login.css">
<link rel="stylesheet" href="/resources/css/header.css">
<script src="/resources/js/login.js"></script>
</head>

<body>
	<%@include file="./header.jsp" %>

	<form class="form signup" method="post" action="/signup">
		<div class="form-header">

			<div class="show-signup">Sign Up</div>
			<div class="show-signin">Sign In</div>
		</div>

		<div class="arrow"></div>
		<div class="form-elements">

			<div class="form-element">
				<input type="text" name="userId" placeholder="아이디를 입력하세요"
					maxlength="5" autocomplete="off" required>
			</div>

			<div class="form-element">
				<input type="password" name="pwd" id="pwd" placeholder="비밀번호"
					autocomplete="off" required>
			</div>

			<div class="form-element">
				<input type="password" name="pwd1" id="pwd1" placeholder="비밀번호 확인"
					required autocomplete="off" required>
			</div>

			<div class="form-element">
				<input type="text" name="userName" placeholder="이름"
					autocomplete="off" required>
			</div>

			<div class="form-element">
				<input type="text" name="phone" placeholder="핸드폰 번호"
					autocomplete="off" required>
			</div>

			<div class="form-element">
				<input type="date" data-placeholder="생년월일 선택" required
					name="birthday">
			</div>

			<input type="hidden" name="cocoa" value="0">

			<div class="form-element">
				<button id="submit-btn">Sign Up</button>
			</div>

		</div>


		<input type="hidden" name="origin" value="${origin}" id="origin" />
		<input type="hidden" name="toonId" value="${toonId}" id="toonId" />
		<input type="hidden" name="redirect" id="redirectURL">
	</form>


	<input type="hidden" value="${signupResult}" id="signupResult" />
	<input type="hidden" value="${loginResult}" id="loginResult" />


</body>

</html>