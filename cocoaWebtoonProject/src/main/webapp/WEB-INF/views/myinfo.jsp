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
<title>내 정보</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="/resources/js/myinfo.js"></script>
<link rel="stylesheet" href="/resources/css/myinfo.css">
<link rel="stylesheet" href="/resources/css/header.css">

</head>

<body>
	<%@include file="./header.jsp" %>
	<div class="container">
		<div class="balance">아이디 : ${sessionScope.ToonUserDTO.userId}</div>
		<div class="balance">${sessionScope.ToonUserDTO.cocoa} 코코아</div>

		
		<div class="btn-group">
			<button class="btn" id="charge">
				<a href="/charge?origin=myinfo">코코아충전</a>
			</button>
			
			<button class="btn" id="cocoahistory">
				<a href="/cocoahistory">코코아 사용내역</a>
			</button>
			
			<form id="logoutForm" method="get" action="/logout">
			<button class="btn" id="logout">
				<a href="/logout">로그아웃</a>
			</button>
			</form>
			
			<form id="removeForm" method="get" action="/remove">
			<button class="btn" id="remove">
				<a href="/remove">회원탈퇴</a>
			</button>
			</form>
		</div>
	</div>


	

</body>

</html>