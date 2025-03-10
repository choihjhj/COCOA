<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>검색</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="${path}/resources/js/search.js"></script>
<link rel="stylesheet" href="${path}/resources/css/search.css">


</head>

<body>

	<header>
		<div class="search-container">
			<div class="search-box">
				<form class="search" id="searchform" action="/search/ajax" method="get">
					<input id="input" type="text" name="keyword" class="search-input" autocomplete="off" placeholder="작품, 작가를 입력하세요." maxlength="30">
					<button class="search-button">검색</button>
				</form>
			</div>
		</div>
	</header>


	<main>
		<section id="webtoon-list">
        <!-- 검색 결과 여기에 출력 -->
    </section>
	</main>

</body>

</html>