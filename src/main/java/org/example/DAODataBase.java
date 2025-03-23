package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DAODataBase implements DAO<Book> {
    private final SessionFactory sessionFactory = getSessionFactory();
    private final Session session = sessionFactory.openSession();
    private final Transaction transaction = session.beginTransaction();

    @Override
    public void persist(Book entity) {
        session.persist(entity);
        closeSessionWithTransaction();
    }

    @Override
    public Book findById(Integer id) {
        String hql = "FROM Book B WHERE B.id = " + id;
        List<Book> books = session.createQuery(hql, Book.class).list();

        closeSession();
        return books.getFirst();
    }

    @Override
    public List<Book> findAll() {
        String hql = "FROM Book";
        List<Book> books = session.createQuery(hql, Book.class).list();
        closeSession();
        return books;
    }

    @Override
    public void update(Book entity) {
        session.merge(entity);
        closeSessionWithTransaction();
    }

    @Override
    public void delete(Book entity) {
        session.remove(entity);
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
