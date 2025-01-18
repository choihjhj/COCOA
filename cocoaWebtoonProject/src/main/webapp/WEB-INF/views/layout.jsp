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
<title>코코아웹툰</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link rel="stylesheet" href="/resources/css/layout.css">
<link rel="stylesheet" href="/resources/css/header.css">
<script src="/resources/js/layout.js"></script>
</head>

<body>

	<%@include file="./header.jsp"%>


	<nav>
		<a href=""<c:if test="${dayOfWeek eq 1}">class="active"</c:if>>월</a>
		<a href=""<c:if test="${dayOfWeek eq 2}">class="active"</c:if>>화</a>
		<a href=""<c:if test="${dayOfWeek eq 3}">class="active"</c:if>>수</a>
		<a href=""<c:if test="${dayOfWeek eq 4}">class="active"</c:if>>목</a>
		<a href=""<c:if test="${dayOfWeek eq 5}">class="active"</c:if>>금</a>
		<a href=""<c:if test="${dayOfWeek eq 6}">class="active"</c:if>>토</a>
		<a href=""<c:if test="${dayOfWeek eq 7}">class="active"</c:if>>일</a>
	</nav>
	


	<div class="box">
		<video title="" class="object-contain w-full h-full pointer-events-auto " style="background: none;" autoplay muted>
			<source type="video/webm" src="/resources/video/16.webm">
		</video>
		<img class="banner" src="/resources/image/banner/16_banner.jpg" alt="Banner">
		<img class="webtoon-title" src="">
	</div>
	

 	<main>
		<section>
			<c:forEach items="${webtoons}" var="webtoon">
					<article class="webtoonlist">
						<img class="bg" src="${path}/resources/image/bg/${webtoon.toonId}_bg.jpg" alt="${webtoon.toonId}"> 
						<img class="person" src="${path}/resources/image/person/${webtoon.toonId}_person.png">
						<img class="title" src="${path}/resources/image/title/${webtoon.toonId}_title.png">
					</article>
			</c:forEach>
		</section>
	</main>
 

	<footer>
		<p>COCOA 공동대표: 최혜지, 박은성, 허준행</p>
	</footer>
</body>

</html>