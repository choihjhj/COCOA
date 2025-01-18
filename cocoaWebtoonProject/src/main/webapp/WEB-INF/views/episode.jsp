<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
			<!DOCTYPE html>

			<html lang="en">

			<head>
				<meta charset="UTF-8">
				<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
				<META HTTP-EQUIV="Expires" CONTENT="-1">
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
				<meta http-equiv="X-UA-Compatible" content="IE=edge">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<title>에피소드 페이지</title>
				<link rel="stylesheet" href="/resources/css/episode.css">
				<link rel="stylesheet" href="/resources/css/header.css">
				<script src="/resources/js/episode.js"></script>
				<script>
					// EpIds 값을 JSON 형식으로 변환하여 JS 파일로 전달
					var EpIds = ${ EpIds };
					var epNumberz = ${ EpisodeDTO.epNumber };
				</script>
			</head>

			<body>

				<%@include file="./header.jsp" %>


					<!-- 웹툰 이미지 -->
					<div class="webtoon-image-container"></div>


					<div class="scroll">
						<button id="up">⬆</button>
					</div>

					<div class="button-container">
						<c:set var="firstEpId" value="${EpIds[0]}" />
						<c:set var="lastEpId" value="${EpIds[fn:length(EpIds) - 1]}" />

						<a href="#" class="prev-button"
							style="visibility: ${EpisodeDTO.epId == firstEpId ? 'hidden' : 'visible'}">이전 에피소드</a>
						<a href="#" class="category-button">목록</a>
						<a href="#" class="next-button"
							style="visibility: ${EpisodeDTO.epId == lastEpId ? 'hidden' : 'visible'}">다음 에피소드</a>
					</div>


					<div class="c1">
						<div class="comment-input-container" data-episode-id="${sessionScope.ToonUserDTO.userId}">
							<c:choose>
								<c:when test="${sessionScope.ToonUserDTO.userId != null}">
									<input class="comment-input" maxlength="50" type="text" id="write"
										placeholder="댓글 작성은 50자 제한입니다.">
								</c:when>
								<c:otherwise>
									<input class="comment-input" maxlength="50" type="text" id="write"
										placeholder="로그인 후 작성할 수 있습니다." readonly>
								</c:otherwise>
							</c:choose>
							<button class="submit-comment" id="writebutton">댓글작성</button>
						</div>
					</div>

					<div class="sort-buttons">
						<button class="sort-button active" id="best">베스트순</button>
						<button class="sort-button" id="latest">최신순</button>
					</div>


					<main>
						<!-- 댓글 영역 -->
						<div class="comments">
							<c:forEach items="${EpCommentDTO}" var="epcomment">
								<!-- 댓글 리스트 -->
								<div class="comment-list">

									<!-- 각 댓글 -->
									<div class="comment-item">
										<div class="comment-header">
											<span class="comment-author">${epcomment.userId}</span> 
											<div class="comment-date"><fmt:formatDate value="${epcomment.writeDate}" pattern="yyyy-MM-dd HH:mm" /></div>
											<button class="likeButton" data-commentid="${epcomment.commentId}"></button>	
											<span class="comment-likes">${epcomment.likeCnt}</span>				
										</div>
										<div class="comment-body">
											<p class="commentbody">${epcomment.commentBody}</p>
											<c:if test="${sessionScope.ToonUserDTO.userId == epcomment.userId}">
												<button class="modifyButton" id="modifyButton">수정</button>
												<button class="removeButton" id="removeButton">삭제</button>
											</c:if>
										</div>
									</div>
									<!-- 동적으로 댓글 추가 -->

								</div>
							</c:forEach>
						</div>
					</main>

					<input type="hidden" value="${sessionScope.ToonUserDTO.userId}" id="userId" />
			</body>

			</html>