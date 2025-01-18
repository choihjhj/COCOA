<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class='header'>
<a class="storage" href="mystorage">보관함 이미지</a>
<a class="main" href="/layout">메인이미지</a>
<a class="search" href="/search">돋보기모양이미지</a>
<c:choose>
    <c:when test="${sessionScope.ToonUserDTO != null}">
        <a class="logo myinfo" href="/myinfo">내정보 이미지</a>
    </c:when>
    <c:otherwise>
        <a class="logo login" href="/login">로그인 이미지</a>
    </c:otherwise>
</c:choose>
</div>
