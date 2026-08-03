package org.course.service;

import org.course.entity.Coupon;
import org.course.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Long createCoupon(String code, Float discount) {
        Coupon coupon = new Coupon(code, discount);
        this.couponRepository.save( coupon);
        return coupon.getId();
    }

    public boolean existByCode(String code) {
        return this.couponRepository.existByCode(code);
    }

    public void assignCouponToClient(Long clientId, Long couponId) {
        this.couponRepository.assignCouponToClient(clientId, couponId);
    }
}
