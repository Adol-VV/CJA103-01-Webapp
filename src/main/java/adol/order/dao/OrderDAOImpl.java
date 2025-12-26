package adol.order.dao;
import static adol.util.Constants.PAGE_MAX_RESULT;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import adol.order.entity.Order;
import adol.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrderDAOImpl implements OrderDAO{
	private SessionFactory factory;
	public OrderDAOImpl() {
		factory = HibernateUtil.getSessionFactory();
	}
	private Session getSession() {
		return factory.getCurrentSession();
	}
	@Override
	public void insert(Order order) {
		// TODO Auto-generated method stub
		getSession().persist(order);
	}

	@Override
	public void update(Order order) {
		// TODO Auto-generated method stub
		getSession().merge(order);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Order order = getSession().get(Order.class,id);
		if(order != null) {
			getSession().remove(order);
		}
		
	}

	@Override
	public Order getById(Integer id) {
		// TODO Auto-generated method stub
		return getSession().find(Order.class,id);
	}

	@Override
	public List<Order> getAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery("from Order",Order.class).getResultList();
	}

	@Override
	public List<Order> getAll(int currentPage) {
	    Session session = getSession();
	    int page = (currentPage - 1) * PAGE_MAX_RESULT;
	    return session.createQuery("from Order", Order.class) // 注意：這裡要用類別名
	                  .setFirstResult(page)
	                  .setMaxResults(PAGE_MAX_RESULT)
	                  .getResultList();
	}

	@Override
	public List<Order> getByCompositeQuery(Map<String, String> map) {
		if(map.size() == 0) {
			return getAll();
		}
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
		Root<Order> root =  criteria.from(Order.class);
		List<Predicate> predicates = new ArrayList<>();
		
		if(map.containsKey("startorderdate") && map.containsKey("endorderdate")) {
			predicates.add(builder.between(root.get("createdDate"), Date.valueOf(map.get("startorderdate")), Date.valueOf(map.get("endorderdate"))));
		}
		if(map.containsKey("startpay") && map.containsKey("endpay")) {
			predicates.add(builder.between(root.get("TOTAL"),Integer.valueOf(map.get("startpay")), Integer.valueOf(map.get("endpay"))));
		}
		for(Map.Entry<String, String> row :map.entrySet()) {
			if("memberId".equals(row.getKey())) {
				predicates.add(builder.equal(root.get("memberId"), row.getValue()));
			}
			if ("startorderdate".equals(row.getKey())) {
				if (!map.containsKey("endorderdate"))
					predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), Date.valueOf(row.getValue())));
			}

			if ("endorderdate".equals(row.getKey())) {
				if (!map.containsKey("startorderdate"))
					predicates.add(builder.lessThanOrEqualTo(root.get("createdDate"), Date.valueOf(row.getValue())));

			}

			if ("startpay".equals(row.getKey())) {
				if (!map.containsKey("endpay"))
					predicates.add(builder.greaterThanOrEqualTo(root.get("total"), Integer.valueOf(row.getValue())));

			}

			if ("endpay".equals(row.getKey())) {
				if (!map.containsKey("startpay"))
					predicates.add(builder.lessThanOrEqualTo(root.get("total"), Integer.valueOf(row.getValue())));

			}
		}
		
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		criteria.orderBy(builder.asc(root.get("orderId")));
		TypedQuery<Order> query = getSession().createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public long getTotal() {
		// TODO Auto-generated method stub
		return getSession().createQuery("select count(*) from Order",long.class).
				getSingleResult();
	}

}
