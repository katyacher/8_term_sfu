package edu.sfu.lab6.controllers;

import edu.sfu.lab6.model.Order;
import edu.sfu.lab6.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    /*
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable Integer customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }
 */
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}/total")
    public BigDecimal getOrderTotal(@PathVariable Integer id) {
        return orderService.calculateOrderTotal(id);
    }
   
}