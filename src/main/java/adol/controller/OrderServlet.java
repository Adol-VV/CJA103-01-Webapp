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
		default:
			forwardPath = "/index.jsp";
		}
		res.setContentType("text/html; charset=UTF-8");
		RequestDispatcher dispatcher = req.getRequestDispatcher(forwardPath);
		dispatcher.forward(req, res);
	};
	
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
