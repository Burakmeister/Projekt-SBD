package dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 *
 * @author BURAK
 */
public abstract class DAO<T> {

    protected Class<T> modelClass;

    private SessionFactory sf = HibernateUtil.getSessionFactory();

    public void setmodelClass(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    protected final Session getSession() {
        Session session = null;
        try {
            session = this.sf.getCurrentSession();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }

        if (session == null) {
            session = sf.openSession();
        }
        return session;
    }

    protected final Transaction getTransaction(Session session) {
        Transaction tx = session.getTransaction();
        if (!TransactionStatus.ACTIVE.equals(tx.getStatus())) {
            tx = session.beginTransaction();
        }
        return tx;
    }

    public final void create(T obj) {
        Session session = this.getSession();
        Transaction tx = this.getTransaction(session);
        session.save(obj);
        tx.commit();
    }

    public final void delete(T obj) {
        Session session = this.getSession();
        Transaction tx = this.getTransaction(session);
        session.delete(obj);
        tx.commit();
    }

    public final void update(T obj) {
        Session session = this.getSession();
        Transaction tx = this.getTransaction(session);
        session.update(obj);
        tx.commit();
    }

    public T getById(Long id) {
        return getSession().get(modelClass, id);
    }
}
