package adol.order.service;

import java.util.List;
import java.util.Map;

import adol.order.entity.Order;

public interface OrderService {
	
	List<Order> getAllOrders(int currentPage);
	int getPageTotal();
	List<Order> getOrdersByCompositeQuery(Map<String,String[]>map);
	
}
