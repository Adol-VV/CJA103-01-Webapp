<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<title>List Orders</title>
</head>
<body>
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
	<br>
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/cat.png">
	<form action="${pageContext.request.contextPath}/order/order.do" method="post">
    <h3>修改訂單</h3>
    
    <p>訂單編號：${order.orderId}</p>
    <input type="hidden" name="orderId" value="${order.orderId}">
    
    <label>總金額：</label>
    <input type="text" name="total" value="${order.total}"><br>
    
    <label>訂單狀態：</label>
    <select name="status">
        <option value="0" ${order.status == 0 ? 'selected' : ''}>未付款</option>
        <option value="1" ${order.status == 1 ? 'selected' : ''}>已付款</option>
    </select>
    <br>

    <input type="hidden" name="action" value="updateOrder">
    <input type="submit" value="送出修改">
	</form>
	<a href="${pageContext.request.contextPath}/index.jsp">回首頁</a>	
</body>
</html>