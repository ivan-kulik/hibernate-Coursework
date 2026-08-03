package org.course.repository;

import org.course.entity.Coupon;
import org.course.repository.util.TransactionExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepository {

    private final TransactionExecutor transactionExecutor;

    public CouponRepository(TransactionExecutor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }

    public void save(Coupon coupon) {
        this.transactionExecutor.executeInTransaction(session -> {
            session.persist(coupon);
        });
    }

    public boolean existById(Long id) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select count(c) from Coupon c
                where c.id = :id
                """;
            return session.createQuery(hqlQuery, Long.class)
                    .setParameter("id", id)
                    .uniqueResult() > 0;
        });
    }

    public boolean existByCode(String code) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select count(c) from Coupon c
                where c.code = :code
                """;
            return session.createQuery(hqlQuery, Long.class)
                    .setParameter("code", code)
                    .uniqueResult() > 0;
        });
    }

    public Coupon findById(Long couponId) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                    select c from Coupon c
                    where c.id = :id
                    """;
            return session.createQuery(hqlQuery, Coupon.class)
                    .setParameter("id", couponId)
                    .uniqueResult();
        });
    }

    public void assignCouponToClient(Long clientId, Long couponId) {
        this.transactionExecutor.executeInTransaction(session -> {
            String sqlQuery = """
                    insert into client_coupons (client_id, coupon_id)
                    values (:clientId, :couponId)
                    """;
            session.createNativeQuery(sqlQuery)
                    .setParameter("clientId", clientId)
                    .setParameter("couponId", couponId)
                    .executeUpdate();
        });
    }

    public void update(Long id, String code, Float discount) {
        this.transactionExecutor.executeInTransaction(session -> {
            Coupon coupon = session.get(Coupon.class, id);
            coupon.setCode(code);
            coupon.setDiscount(discount);
        });
    }
}
