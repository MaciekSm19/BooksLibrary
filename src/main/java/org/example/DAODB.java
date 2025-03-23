package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DAODB implements DAO<Book> {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Override
    public void persist(Book entity) {
        openSessionWithTransaction().persist(entity);
        closeSessionWithTransaction();
    }

    @Override
    public Book findById(Integer id) {
        String hql = "FROM Book B WHERE B.id = " + id;
        List<Book> books = openSession().createQuery(hql, Book.class).list();

        closeSession();
        return books.getFirst();
    }

    @Override
    public List<Book> findAll() {
        String hql = "FROM Book";
        List<Book> books = openSession().createQuery(hql, Book.class).list();
        closeSession();
        return books;
    }

    @Override
    public void update(Book entity) {
        openSessionWithTransaction().merge(entity);
        closeSessionWithTransaction();
    }

    @Override
    public void delete(Book entity) {
        openSessionWithTransaction().remove(entity);
        closeSessionWithTransaction();
    }

    @Override
    public void deleteAll() {
        findAll().forEach(this::delete);
        closeSessionWithTransaction();
    }

    private SessionFactory getSessionFactory() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
        return metadata.buildSessionFactory();
    }

    private Session openSession() {
        sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        return session;
    }

    private Session openSessionWithTransaction() {
        openSession();
        transaction = session.beginTransaction();
        return session;
    }

    private void closeSession() {
        if (session != null) {
            session.close();
            sessionFactory.close();
        }
    }

    private void closeSessionWithTransaction() {
        if (transaction != null) {
            transaction.commit();
            closeSession();
        }
    }
}
