package org.course.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "status")
    private OrderStatus status;

    public Order() {};

    public Order(Integer totalAmount, OrderStatus status) {
        this.orderDate = LocalDate.now();
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(LocalDate newOrderDate) {
        this.orderDate = newOrderDate;
    }

    public Integer getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(Integer newTotalAmount) {
        this.totalAmount = newTotalAmount;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }
}
