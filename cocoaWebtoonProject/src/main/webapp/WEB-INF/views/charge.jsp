<%@ page language="java"
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
<title>충전</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/charge.css">
<link rel="stylesheet" href="/resources/css/header.css">
<script src="/resources/js/charge.js"></script>
</head>

<body>
	<%@include file="./header.jsp"%>
	
	<div class="charge-div">
	
		<form class="charge-form" id="charge-form" action="/charge" method="post" enctype="application/json">
			<fieldset>
				<legend>충전금액</legend>
				<input type="radio" name="amount" value="1" required> 1 <img src="/resources/image/cocoa.png" alt="코코아 열매" class="cocoa-icon"> - 100원<br>
				<input type="radio" name="amount" value="10"> 10 <img src="/resources/image/cocoa.png" alt="코코아 열매" class="cocoa-icon">- 1000원<br>
				<input type="radio" name="amount" value="30"> 30 <img src="/resources/image/cocoa.png" alt="코코아 열매" class="cocoa-icon">- 3000원<br>
				<input type="radio" name="amount" value="50"> 50 <img src="/resources/image/cocoa.png" alt="코코아 열매" class="cocoa-icon">- 5000원<br>
				<input type="radio" name="amount" value="100"> 100 <img src="/resources/image/cocoa.png" alt="코코아 열매" class="cocoa-icon"> -10000원<br>
			</fieldset>

			<input type="text" placeholder="신용카드번호 입력(숫자만 입력하세요)" name="creditNumber" required pattern="[0-9]+"  required>
			<input type="text" placeholder="신용카드 비밀번호 입력" name="creditPwd" required required pattern="[0-9]+">

			<input type="hidden" id="origin" name="epId" value="${epId}">
			<button id="charge-btn" type="submit">충전</button>
			
			
		</form>
	</div>
</body>
</html>