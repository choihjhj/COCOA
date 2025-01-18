<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

<title>코코아내역</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<link rel="stylesheet" href="/resources/css/header.css">
<link rel="stylesheet" href="/resources/css/cocoahistory.css">
</head>

<body>
	<%@include file="./header.jsp"%>

	<c:choose>
		<c:when test="${not empty list}">
			<c:forEach items="${list}" var="cphistory">
				<div class="history">
					<div class="history-list">
						<div class="history-item">
							<div class="history-header">
								<span class="date">${cphistory.historyDate}</span> 
								<span class="status">${cphistory.cpType}</span> 
								<span class="cocoa">${cphistory.cocoa} 코코아</span>
								<span class="balance">잔액 : ${cphistory.balance}</span>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="history">
				<div class="history-list">
					<div class="history-item">
						<div class="history-header">
					
							<span class="status">내역이 존재하지 않습니다.</span> 
				
						</div>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

</body>

</html>