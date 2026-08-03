package org.course.repository;

import org.course.entity.Client;
import org.course.entity.Order;
import org.course.repository.util.TransactionExecutor;
import org.springframework.stereotype.Repository;

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
}
