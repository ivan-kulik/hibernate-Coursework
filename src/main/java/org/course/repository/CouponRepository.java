package org.course.repository;

import org.course.entity.Client;
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

    public void assignCouponToClient(Long clientId, Long couponId) {
        this.transactionExecutor.executeInTransaction(session -> {
            Client client = session.get(Client.class, clientId);
            Coupon couponRef = session.getReference(Coupon.class, couponId);

            client.addCoupon(couponRef);
        });
    }
}
