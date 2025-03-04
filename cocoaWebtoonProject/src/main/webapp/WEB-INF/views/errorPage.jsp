<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 발생</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .error-container {
            border: 1px solid #ff0000;
            padding: 20px;
            background-color: #fff0f0;
            color: #ff0000;
            font-size: 18px;
            margin: 0 auto;
            width: 50%;
            border-radius: 10px;
        }
        .error-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 16px;
        }
        .back-button {
            margin-top: 30px;
            padding: 10px 20px;
            background-color: #ff0000;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .back-button:hover {
            background-color: #d10000;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-title">오류가 발생했습니다.</div>
        <div class="error-message">
            <!-- errorMessage가 있을 경우 그 내용을 출력 -->
            <c:if test="${not empty errorMessage}">
                ${errorMessage}
            </c:if>
            <!-- errorMessage가 비어있을 경우 기본 메시지 출력 -->
            <c:if test="${empty errorMessage}">
                예상치 못한 오류가 발생했습니다. 다시 시도해 주세요.
            </c:if>
            
        </div>
        <button class="back-button" onclick="history.back()">뒤로 가기</button>
    </div>
</body>
</html>
