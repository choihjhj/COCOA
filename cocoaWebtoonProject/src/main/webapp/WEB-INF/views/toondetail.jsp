<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

		<c:set var="path" value="${pageContext.request.contextPath}" />

		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
			<META HTTP-EQUIV="Expires" CONTENT="-1">
			<title>${webtoon.toonName}</title>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
			<link rel="stylesheet" href="/resources/css/header.css">
			<link rel="stylesheet" href="/resources/css/toondetail.css">

			<script src="/resources/js/toondetail.js"></script>
		</head>

		<body>
			<%@include file="./header.jsp" %>

				<!-- 웹툰 이미지 배경 정보 -->
				<div class="parent" data-toon-id ="${sessionScope.WebToonDTO.toonId}">

					<video title="#" class="object-contain w-full h-full pointer-events-auto " style="background: none;"
						autoplay muted>
						<source type="video/webm" src="/resources/video/${sessionScope.WebToonDTO.toonId}.webm">
					</video>

					<img src="/resources/image/banner/${sessionScope.WebToonDTO.toonId}_banner.jpg" alt="Banner">

					<div class="banner">
						<div class="webtoon-info">
							<h1 id="webtoon-title">${sessionScope.WebToonDTO.toonName}</h1>
							<h2 id="webtoon-artist">${sessionScope.WebToonDTO.author}</h2>
							<p id="webtoon-desc">${sessionScope.WebToonDTO.summary}</p>
						</div>
					</div>
				</div>

				<div class="container">
					<c:forEach items="${episodes}" var="episode">
						<a class="episode" data-episode-id="${episode.epId}" href="/episode?epId=${episode.epId}">
								<div class="thumbnail">
									<img src="/resources/image/thumbnail/${episode.epId}.jpg" alt="에피소드">
								</div>
								<div class="episode-info">
									<h2 class="episode-epNumber">${episode.epNumber} 화</h2>
								</div>



								<p class="pricestate">
									<c:choose>
										<c:when test="${episode.epStatus == 0}">
											무료
										</c:when>
										<c:when test="${episode.epStatus == 1}">
											<c:choose>
												<c:when test="${not empty purchasedEpIds}">
													<c:set var="currentEpId" value="${episode.epId}" />
													<c:if test="${purchasedEpIds.contains(currentEpId)}">
														구매완료
													</c:if>
													<c:if test="${!purchasedEpIds.contains(currentEpId)}">
														유료
													</c:if>
												</c:when>
												<c:when test="${empty purchasedEpIds}">
													유료
												</c:when>
											</c:choose>
										</c:when>
									</c:choose>
								</p>
						</a>
					</c:forEach>
				</div>




		</body>

		</html>