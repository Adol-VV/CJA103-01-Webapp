package adol.order.entity;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prod_order")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROD_ORDER_ID", updatable = false)
	private Integer orderId;
	
	@Column(name="MEMBER_ID")
	private Integer memberId;
	
	@Column(name="ORGANIZER_ID")
	private Integer organizerId;
	
	@Column(name="CREATED_AT")
	private Date createdDate;
	
	@Column(name="TOTAL")
	private Integer total;
	
	@Column(name="TOKEN_USED")
	private Integer token;
	
	@Column(name="PAYABLE")
	private Integer payable;
	
	@Column(name="PAY_STATUS")
	private Integer status;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getOrganizerId() {
		return organizerId;
	}

	public void setOrganizerId(Integer organizerId) {
		this.organizerId = organizerId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
	}

	public Integer getPayable() {
		return payable;
	}

	public void setPayable(Integer payable) {
		this.payable = payable;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
