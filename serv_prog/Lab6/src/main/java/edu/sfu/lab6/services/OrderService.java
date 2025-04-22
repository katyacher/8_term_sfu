package edu.sfu.lab6.services;

import edu.sfu.lab6.dao.OrderDAO;
import edu.sfu.lab6.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
//import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

  /*  public List<Order> getOrdersByCustomer(Integer customerId) {
        return orderDAO.findByCustomerId(customerId);
    }*/

    public Order createOrder(Order order) {
        Integer id = orderDAO.save(order);
        return orderDAO.getById(id);
    }

    public BigDecimal calculateOrderTotal(Integer orderId) {
        Order order = orderDAO.getById(orderId);
        if (order != null) {
            return order.getTotal();
        }
        return BigDecimal.ZERO;
    }
}