<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/main/main.css">
<title>List Orders</title>
</head>
<body>
	<h1>訂單列表</h1>
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
			<th>修改刪除</th>
		</tr>
		<c:forEach var="order" items="${orderList}">
			<tr>
				<td>${order.orderId}</td>
				<td>${order.memberId}</td>
				<td>${order.organizerId}</td>
				<td>${order.createdDate}</td>
				<td>${order.total}</td>
				<td>${order.token}</td>
				<td>${order.payable}</td>
				<td>${order.status == 0 ? '未付款' : '已付款'}</td>
				<td>
                <div style="display: flex; justify-content: center; gap: 5px;">
                    <form method="post" action="${pageContext.request.contextPath}/order/order.do">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <input type="hidden" name="action" value="update">
                        <input type="submit" value="修改">
                    </form>

                    <form method="post" action="${pageContext.request.contextPath}/order/order.do" 
                          onsubmit="return confirm('確定要刪除訂單編號: ${order.orderId} 嗎？');">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <input type="hidden" name="whichPage" value="listCompositeQueryOrders">
                        <input type="hidden" name="action" value="delete">
                        <input type="submit" value="刪除" style="color: red;">
                    </form>
                </div>
            	</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	<img width="140px" height="100px" alt="要飛囉貓貓" src="${pageContext.request.contextPath}/img/inversecat.png">
	<br><br>
	
	<a href="${pageContext.request.contextPath}/index.jsp">回首頁</a>	
</body>
</html>