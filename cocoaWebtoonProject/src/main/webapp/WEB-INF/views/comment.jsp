<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    
	<!-- 댓글 영역 -->
	
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

	
	
	