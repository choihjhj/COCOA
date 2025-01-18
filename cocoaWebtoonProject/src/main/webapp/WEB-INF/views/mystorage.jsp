<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>보관함</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/storage.css">
<link rel="stylesheet" href="/resources/css/header.css">
<script src="/resources/js/storage.js"></script>
</head>

<body>

	<%@include file="./header.jsp"%>

	<nav>
		<p id="recentList">최근 감상</p>
		<p id="purchaseList">구매 목록</p>
	</nav>

	<div id="recentContainer">
		<main id="recent">
			<section>
					<article class="webtoonlist">
						<img class="bg"> 
						<img class="person">
						<img class="title">
						<p class="epNumber"></p>
					</article>
			</section>
		</main>
		<p class="noresult" id="noresult1">기록이 존재하지 않습니다</p>
	</div>

	<div id="purchaseContainer">
		<main id="purchase">
			<c:choose>
				<c:when test="${loginresult == 1}">
					<section>
						<c:forEach var="purchase" items="${PurchaseVO}">
							<article class="webtoonlist" data-toonid="${purchase.toonId}" data-epid="${purchase.epId}">
								<img class="bg" src="/resources/image/bg/${purchase.toonId}_bg.jpg"> 
								<img class="person" src="/resources/image/person/${purchase.toonId}_person.png">
								<img class="title" src="/resources/image/title/${purchase.toonId}_title.png">
								<p class="epNumber">${purchase.epNumber}화</p>
							</article>
						</c:forEach>
					</section>
				</c:when>
				<c:otherwise>				
				</c:otherwise>
			</c:choose>
		</main>
		<!-- main 요소 밖에서 로그인 메시지를 표시 -->
		<c:if test="${loginresult != 1}">
			<p class="noresult" id="noresult2">구매 목록이 보여집니다.</p>
		</c:if>
	</div>
	
 



	<footer>
		<p>COCOA 공동대표: 최혜지, 박은성, 허준행</p>
	</footer>

</body>

</html>