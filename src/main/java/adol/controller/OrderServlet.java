package adol.controller;

import java.io.IOException;
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

		Map<String,String[]> map = req.getParameterMap();
		if(map !=null) {
			List<Order> orderList=  orderService.getOrdersByCompositeQuery(map);
			req.setAttribute("orderList", orderList);
		}else {
			return "/index.jsp";
		}
		return "/order/listCompositeQueryOrders.jsp";
	}
	
}
