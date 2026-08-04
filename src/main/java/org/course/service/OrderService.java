package org.course.service;

import org.course.entity.Order;
import org.course.entity.OrderStatus;
import org.course.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long createOrder(Long clientId, LocalDate orderDate, Integer totalAmount) {
        Order order = new Order(orderDate, totalAmount, OrderStatus.NEW);
        this.orderRepository.save(clientId, order);
        return order.getId();
    }

    public Collection<Order> findOrdersByStatus(OrderStatus orderStatus) {
        return this.orderRepository.findByStatus(orderStatus);
    }
}
