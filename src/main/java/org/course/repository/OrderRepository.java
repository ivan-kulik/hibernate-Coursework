package org.course.repository;

import org.course.entity.Client;
import org.course.entity.Order;
import org.course.entity.OrderStatus;
import org.course.repository.util.TransactionExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class OrderRepository {

    private final TransactionExecutor transactionExecutor;

    public OrderRepository(TransactionExecutor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }

    public void save(Long clientId, Order order) {
        this.transactionExecutor.executeInTransaction(session -> {
            Client clientRef = session.getReference(Client.class, clientId);
            order.setClient(clientRef);
            session.persist(order);
        });
    }

    public Collection<Order> findByStatus(OrderStatus status) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                    select o from Order o
                    join fetch o.client c
                    where o.status = :status
                    """;
            return session.createQuery(hqlQuery, Order.class)
                    .setParameter("status", status)
                    .getResultList();
        });
    }
}
