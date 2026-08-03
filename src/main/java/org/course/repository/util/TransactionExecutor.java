package org.course.repository.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

/// Or use @PersistenceContext with EntityManager and @Transactional with services
@Component
public class TransactionExecutor {

    private final SessionFactory sessionFactory;

    public TransactionExecutor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void executeInTransaction(Consumer<Session> action) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.getTransaction();

            transaction.begin();
            action.accept(session);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public<T> T executeInTransaction(Function<Session, T> action) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = this.sessionFactory.openSession();
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
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
