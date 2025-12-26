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
	<h1>訂單列表</h1>
	<c:if test="${orderPageQty > 0}">
  		<b><font color=red>第${currentPage}/${orderPageQty}頁</font></b>
	</c:if>
	<br>
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/cat.png">
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/cat.png">
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/cat.png">
	<table style="width:50%; text-align:center;">
		<tr>
			<th>訂單編號</th>
			<th>會員編號</th>
			<th>商家編號</th>
			<th>訂單日期</th>
			<th>總金額</th>
			<th>折價幣</th>
			<th>應付帳款</th>
			<th>應帳狀態</th>
		</tr>
		
		<c:forEach var="order" items="${orderList}">
		<p>測試：${orderList[0].orderId}</p>
			<tr>
				<td>${order.orderId}</td>
				<td>${order.memberId}</td>
				<td>${order.organizerId}</td>
				<td>${order.createdDate}</td>
				<td>${order.total}</td>
				<td>${order.token}</td>
				<td>${order.payable}</td>
				<td>${order.status}</td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${currentPage > 1}">
		<a href="${pageContext.request.contextPath}/order/order.do?action=getAll&page=1">至第一頁</a>&nbsp;
	</c:if>
	<c:if test="${currentPage - 1 != 0}">
		<a href="${pageContext.request.contextPath}/order/order.do?action=getAll&page=${currentPage - 1}">上一頁</a>&nbsp;
	</c:if>
	<c:if test="${currentPage + 1 <= orderPageQty}">
		<a href="${pageContext.request.contextPath}/order/order.do?action=getAll&page=${currentPage + 1}">下一頁</a>&nbsp;
	</c:if>
	<c:if test="${currentPage != orderPageQty}">
		<a href="${pageContext.request.contextPath}/order/order.do?action=getAll&page=${orderPageQty}">至最後一頁</a>&nbsp;
	</c:if>
	<br>
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	<br><br>
	
	<a href="${pageContext.request.contextPath}/index.jsp">回首頁</a>	
</body>
</html>