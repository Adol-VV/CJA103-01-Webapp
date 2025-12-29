package adol.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import adol.order.entity.Order;
import adol.order.service.OrderService;
import adol.order.service.OrderServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/order/order.do")
public class OrderServlet extends HttpServlet{
	private OrderService orderService;
	
	@Override
	public void init() throws ServletException {
		orderService = new OrderServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		doPost(req,res);
	};
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String forwardPath = "";
		switch (action){
		case "getAll":
			forwardPath = getAllOrder(req,res);
			break;
		case "compositeQuery":
			forwardPath = getCompositeOrdersQuery(req,res);
			break;
		case "update":
			forwardPath = updatePage(req,res);
			break;
		case "delete":
			forwardPath = deleteOrder(req,res);
			break;
		case "updateOrder":
			forwardPath = updateOrder(req,res);
			break;
		case "addOrder":
			forwardPath = addOrder(req,res);
			break;
		default:
			forwardPath = "/index.jsp";
		}
		res.setContentType("text/html; charset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);
	};
	
	private String addOrder(HttpServletRequest req, HttpServletResponse res) {

		List<String> errorMsgs = new LinkedList<>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		
		String mId = req.getParameter("memberId");
		String oId = req.getParameter("organizerId");
		String ttl = req.getParameter("total");
		String tk = req.getParameter("token");
		String status = req.getParameter("status");
		int memberT = orderService.getMemberTotal();
		int orgT = orderService.getOrganizerIdTotal();
		Integer memberId = null;
		if(mId.trim().isEmpty()) {
			errorMsgs.add("會員ID必須填寫");
		}else {
			if(mId!=null && mId.trim().length() !=0) {
				
				try {
					memberId = Integer.valueOf(mId.trim());
					if(memberId<0 || memberId > memberT) {
						errorMsgs.add("會員ID不能小於0與大於"+memberT);
					}
				}catch(NumberFormatException e){
					errorMsgs.add("會員ID必須為數字");
				}
			}
		}
		Integer organizerId = null;
		if(oId.trim().isEmpty()) {
			errorMsgs.add("主辦方ID必須填寫");
		}else {
			if(oId!=null && oId.trim().length() !=0) {
				
				try {
					organizerId = Integer.valueOf(oId.trim());
					if(organizerId<0|| organizerId > orgT) {
						errorMsgs.add("主辦方ID不能小於0與大於"+ orgT);
					}
				}catch(NumberFormatException e){
					errorMsgs.add("主辦方ID必須為數字");
				}
				}
		}
		Integer total = null;
		if(ttl.trim().isEmpty()) {
			errorMsgs.add("金額必須填寫");
		}else {
			if(ttl!=null && ttl.trim().length() !=0) {
				
				try {
					total = Integer.valueOf(ttl.trim());
					if(total<0) {
						errorMsgs.add("金額不能小於0");
					}
				}catch(NumberFormatException e){
					errorMsgs.add("金額必須為數字");
				}
			}
		}
		Integer token = null;
		if(tk.trim().isEmpty()) {
			errorMsgs.add("折價幣必須填寫");
		}else {
			if(tk!=null && tk.trim().length() !=0) {
				
				try {
					token = Integer.valueOf(tk.trim());
					if(token<0) {
						errorMsgs.add("折價幣不能小於0");
					}
				}catch(NumberFormatException e){
					errorMsgs.add("折價幣必須為數字");
				}
			}
		}
		
		if(total !=null && token !=null && total<token) {
			errorMsgs.add("金額價格必須大於折價幣");
		}
		
		if(!errorMsgs.isEmpty()) {
			return "/order/OrdersAdd.jsp";
		}
		
		Order order = new Order();
		order.setMemberId(memberId);
		order.setOrganizerId(organizerId);
		order.setTotal(total);
		order.setToken(token);
		order.setPayable(total-token);
		order.setStatus(Byte.valueOf(status));
		orderService.addOrder(order);
		
		return "/index.jsp";
	}
	
	private String updateOrder(HttpServletRequest req, HttpServletResponse res) {
		
		String id = req.getParameter("orderId");
		int orderId = Integer.valueOf(id);
		int total = Integer.valueOf(req.getParameter("total"));
		String status = req.getParameter("status");
		Order order = orderService.getOneOrder(Integer.valueOf(id));
		order.setTotal(total);
		order.setPayable(order.getTotal()-order.getToken());
		order.setStatus(Byte.valueOf(status));
		orderService.updateOrder(order);
		return "/index.jsp";
	}
	
	private String updatePage(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("orderId");
		Order order = orderService.getOneOrder(Integer.valueOf(id));
	    req.setAttribute("order", order);
		return "/order/OrdersUpdate.jsp";
	}
	
	private String deleteOrder(HttpServletRequest req, HttpServletResponse res) {
		String id = req.getParameter("orderId");
		int orderId = Integer.valueOf(id);
		orderService.deleteOrder(orderId);
		
		return "/index.jsp";
	}
	
	private String getAllOrder(HttpServletRequest req, HttpServletResponse res) {
		String page = req.getParameter("page");
		int currentPage = (page == null)? 1:Integer.valueOf(page);
		List<Order> orderList =  orderService.getAllOrders(currentPage);
		if (req.getSession().getAttribute("orderPageQty") == null) {
			int orderPageQty = orderService.getPageTotal();
			req.getSession().setAttribute("orderPageQty", orderPageQty);
		}
		req.setAttribute("orderList", orderList);
		req.setAttribute("currentPage", currentPage);

		return "/order/listAllOrders.jsp";
	}
	private String getCompositeOrdersQuery(HttpServletRequest req, HttpServletResponse res) {
		List<String> errorMsgs = new LinkedList<>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		String mIdStr = req.getParameter("memberId");
		String sp = req.getParameter("startpay");
		String ep = req.getParameter("endpay");
		
		if(mIdStr!=null && mIdStr.trim().length() !=0) {
			if(!mIdStr.trim().matches("\\d+")) {
				errorMsgs.add("ID 必須是數字");
			}
		}
		
		Integer startPay = null;
		if(sp!=null && sp.trim().length() !=0) {
			
			try {
				startPay = Integer.valueOf(sp.trim());
				if(startPay<0) {
					errorMsgs.add("最低金額不能小於0");
				}
			}catch(NumberFormatException e){
				errorMsgs.add("起始價格必須為數字");
			}
		}
		
		Integer endPay =null;
		if(ep!=null && ep.trim().length() !=0) {
			
			try {
				endPay = Integer.valueOf(ep.trim());
				if(endPay<0) {
					errorMsgs.add("最高金額不能小於0");
				}
			}catch(NumberFormatException e){
				errorMsgs.add("最高價格必須為數字");
			}
		}
		
		if(startPay !=null && endPay !=null && startPay>endPay) {
			errorMsgs.add("起始價格大於最高價格");
		}
		
		if(!errorMsgs.isEmpty()) {
			return "/index.jsp";
		}
		Map<String,String[]> map = req.getParameterMap();
		
		if (map == null || map.isEmpty()) {
	        return "/index.jsp";
	    }
		
		
		List<Order> orderList=  orderService.getOrdersByCompositeQuery(map);
		req.setAttribute("orderList", orderList);
		return "/order/listCompositeQueryOrders.jsp";
	}
	
}
