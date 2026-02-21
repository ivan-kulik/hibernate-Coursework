package org.course.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount")
    private Float discount;

    public Coupon() {};

    public Coupon(String code, Float discount) {
        this.code = code;
        this.discount = discount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String newCode) {
        this.code = code;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public void setDiscount(Float newDiscount) {
        this.discount = newDiscount;
    }
}
