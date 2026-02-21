package org.course.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_coupons")
public class ClientCoupons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    public ClientCoupons() {};

    public ClientCoupons(Client client, Coupon coupon) {
        this.client = client;
        this.coupon = coupon;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }
}
