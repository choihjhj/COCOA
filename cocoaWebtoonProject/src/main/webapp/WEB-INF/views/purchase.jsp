<%@ page  language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>구매페이지</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="/resources/js/purchase.js"></script>  
<link rel="stylesheet" href="/resources/css/purchase.css">

</head>
<body>

	<div id="form" class="form" data-episode-id="${EpisodeDTO.epId}">
		<div class="webtoon-info" data-toon-id="${EpisodeDTO.toonId}">
			<h1 id="webtoon-title">${ToonName}</h1>
			<h2 id="episode">${EpisodeDTO.epNumber} 화</h2>
			<h2 id="episode">${EpisodeDTO.epTitle}</h2>
		</div>
		
		<div class="image">
			<img src="/resources/image/thumbnail/${EpisodeDTO.epId}.jpg">
		</div>
		
		<div class="split_sections">
			
			<div class="section price"  id="price">유료 회차 코코아 가격: ${EpisodeDTO.price} </div>
			<div class="section balance" id="cocoabalance" >내 코코아 잔액 : ${UserCocoa} </div>

			<div class="btn_group">
				<button class="charge_btn">충전하기</button>

				<form class="purchaseaction" id="purchase" action="/purchase" method="post">
					<input type="hidden" name="epId" value="${EpisodeDTO.epId}">
					<button class="purchase_btn" type="submit" >구매하기</button>
				</form>

				<button class="cancel_btn">취소하기</button>
			</div>

		</div>
	</div>
	

</body>