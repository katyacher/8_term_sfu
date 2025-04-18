package edu.sfu.lab5.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orderItem")
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;
    
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @MapsId("jewelryId")
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "price", nullable = false, columnDefinition = "numeric(10,2)")
    private BigDecimal price;

	public OrderItemId getId() {
		return id;
	}

	public void setId(OrderItemId id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Jewelry getJewelry() {
		return jewelry;
	}

	public void setJewelry(Jewelry jewelry) {
		this.jewelry = jewelry;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    
    
}

@Embeddable
@Data
class OrderItemId implements Serializable {
	private static final long serialVersionUID = 1L;  // Добавлен serialVersionUID
	
    @Column(name = "order_id")
    private Integer orderId;
    
    @Column(name = "jewelry_id")
    private Integer jewelryId;
}