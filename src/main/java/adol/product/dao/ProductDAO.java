package adol.product.dao;

import java.util.List;
import java.util.Map;

import adol.product.entity.Order;

public interface ProductDAO {
	
	void insert(Order order);
	void update(Order order);
	void delete(Integer id);
	Order getById(Integer id);
	List<Order> getAll();
	List<Order> getAll(int currentPage );
	List<Order> getByCompositeQuery(Map<String, String> map);
	long getTotal();

}
