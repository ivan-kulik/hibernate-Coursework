package org.course.repository;

import org.course.entity.Client;
import org.course.repository.util.TransactionExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {

    private final TransactionExecutor transactionExecutor;

    public ClientRepository(TransactionExecutor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }

    public void save(Client newClient) {
        this.transactionExecutor.executeInTransaction(session -> {
            session.persist(newClient);
        });
    }

    public boolean existsByName(String name) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select count(c) from Client c
                where c.name = :name
                """;
            return session.createQuery(hqlQuery, Long.class)
                    .setParameter("name", name)
                    .uniqueResult() > 0;
        });
    }

    public boolean existsByEmail(String email) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select count(c) from Client c
                where c.email = :email
                """;
            return session.createQuery(hqlQuery, Long.class)
                    .setParameter("email", email)
                    .uniqueResult() > 0;
        });
    }

    public boolean existsByPhone(String phone) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select count(c) from Client c
                join c.profile p
                where p.phone = :phone
                """;
            return session.createQuery(hqlQuery, Long.class)
                    .setParameter("phone", phone)
                    .uniqueResult() > 0;
        });
    }

    public Client findByName(String name) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select c from Client c
                where c.name = :name
                """;
            return session.createQuery(hqlQuery, Client.class)
                    .setParameter("name", name)
                    .uniqueResult();
        });
    }

    public void updateProfile(Long clientId, String newAddress, String newPhone) {
        this.transactionExecutor.executeInTransaction(session -> {
            Client client = session.get(Client.class, clientId);
            client.getProfile().setAddress(newAddress);
            client.getProfile().setPhone(newPhone);
        });
    }

    public boolean deleteByName(String name) {
        return this.transactionExecutor.executeInTransaction(session -> {
            String hqlQuery = """
                select c from Client c
                where c.name = :name
                """;
            Client client = session.createQuery(hqlQuery, Client.class)
                    .setParameter("name", name)
                    .uniqueResultOptional()
                    .orElse(null);

            if (client != null) {
                session.remove(client);
                return true;
            }
            return false;
        });
    }
}
