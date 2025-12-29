package adol.order.service;

import static adol.util.Constants.PAGE_MAX_RESULT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adol.order.dao.OrderDAO;
import adol.order.dao.OrderDAOImpl;
import adol.order.entity.Order;

public class OrderServiceImpl implements OrderService{
	private OrderDAO dao;
	
	public OrderServiceImpl() {
		dao = new OrderDAOImpl();
	}
	@Override
	public List<Order> getAllOrders(int currentPage) {
		
		return dao.getAll(currentPage);
	}

	@Override
	public int getPageTotal() {
		long total = dao.getTotal();
		int pageQty = (int)(total % PAGE_MAX_RESULT == 0 ? (total / PAGE_MAX_RESULT) : (total / PAGE_MAX_RESULT + 1));
		return pageQty;
	}

	@Override
	public List<Order> getOrdersByCompositeQuery(Map<String, String[]> map) {
		Map<String,String> query = new HashMap();
		Set<Map.Entry<String,String[]>> entry = map.entrySet();
		
		for(Map.Entry<String,String[]> row :entry) {
			String key = row.getKey();
			if ("action".equals(key)) {
				continue;
			}
			String value = row.getValue()[0];
			if (value == null || value.isEmpty() ||value.trim().length() == 0) {
				continue;
			}
			query.put(key, value);
		}
		return  dao.getByCompositeQuery(query);
	}
	@Override
	public void deleteOrder(int orderId) {
		dao.delete(orderId);
	}
	@Override
	public void updateOrder(Order order) {

		dao.update(order);
	}
	@Override
	public Order getOneOrder(int orderId) {
		return dao.getById(orderId);
	}
	@Override
	public void addOrder(Order order) {
		dao.insert(order);
	}
	@Override
	public int getMemberTotal() {

		return (int)dao.getMember();
	}
	@Override
	public int getOrganizerIdTotal() {
		return (int)dao.getOrganizer();
	}

}
