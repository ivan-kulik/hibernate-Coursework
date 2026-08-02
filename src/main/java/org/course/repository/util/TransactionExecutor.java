package org.course.repository.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class TransactionExecutor {

    private final SessionFactory sessionFactory;

    public TransactionExecutor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void executeInTransaction(Consumer<Session> action) {
        Transaction transaction = null;

        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.getTransaction();

            transaction.begin();
            action.accept(session);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }

    public<T> T executeInTransaction(Function<Session, T> action) {
        Transaction transaction = null;

        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.getTransaction();

            transaction.begin();
            T result = action.apply(session);
            transaction.commit();

            return result;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        }
    }
}
