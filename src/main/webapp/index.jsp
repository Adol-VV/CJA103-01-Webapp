<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<title>Hibernate Demo</title>
</head>
<body>
	<h1>訂單網頁</h1>
	<c:if test="${not empty errorMsgs}">
		<div style="color: red; background-color: #fff0f0; border: 1px solid red; padding: 10px; margin: 10px 0; border-radius: 5px;">
			<b>請修正以下錯誤：</b>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	<h2>訂單系統</h2>
	<a href="${pageContext.request.contextPath}/order/order.do?action=getAll">查詢所有訂單</a>
	<br><br>
	<h3><b>複合查詢 (使用 Criteria Query)：</b></h3>
	<form action="${pageContext.request.contextPath}/order/order.do" method="post">
		<p><label>會員ID查詢訂單：</label></p>
		<input type="text" name="memberId"><br>
		<p><label>訂單時間範圍</label></p>
		<input type="date" name="startorderdate"> ～ <input type="date" name="endorderdate"><br>
		<p><label>價格範圍</label></p>
		<input type="text" name="startpay"> ～ <input type="text" name="endpay"><br>
		<p><input type="submit" value="送出"></p>
		<input type="hidden" name="action" value="compositeQuery">
	</form>
</body>
</html>