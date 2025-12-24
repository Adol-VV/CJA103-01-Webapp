<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<title>Hibernate Demo</title>
</head>
<body>
	<h1>商品網頁</h1>
	<h2>商品系統</h2>
	<a href="${pageContext.request.contextPath}/emp/emp.do?action=getAll">查詢所有商品</a>
	<br><br>
	<h3><b>複合查詢 (使用 Criteria Query)：</b></h3>
	<form action="${pageContext.request.contextPath}/emp/emp.do" method="post">
		<p><label>商品名稱模糊查詢：</label></p>
		<input type="text" name="ename"><br>
		<p><label>價格範圍</label></p>
		<input type="text" name="startsal"> ～ <input type="text" name="endsal"><br>
		<p><input type="submit" value="送出"></p>
		<input type="hidden" name="action" value="compositeQuery">
	</form>
</body>
</html>