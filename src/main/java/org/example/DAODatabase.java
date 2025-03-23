package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class DAODatabase implements DAO<Book> {
    @Override
    public void persist(Book entity) {
        executeInTransaction(session -> session.persist(entity));
    }

    @Override
    public Optional<Book> findById(Integer id) {
        List<Book> books;
        try(Session session = getSessionFactory().openSession()) {
            String hql = "FROM Book B WHERE B.id = " + id;
            books = session.createQuery(hql, Book.class).list();
        }
        return Optional.of(books.getFirst());
    }

    @Override
    public Optional<List<Book>> findAll() {
        List<Book> books;
        try(Session session = getSessionFactory().openSession()) {
            String hql = "FROM Book";
            books = session.createQuery(hql, Book.class).list();
        }

        return Optional.of(books);
    }

    @Override
    public void update(Book entity) {
        executeInTransaction(session -> session.merge(entity));
    }

    @Override
    public void delete(Book entity) {
        executeInTransaction(session -> session.remove(entity));
    }

    @Override
    public void deleteAll() {
        if (findAll().isPresent()) {
            findAll().get().forEach(this::delete);
        }
    }

    private SessionFactory getSessionFactory() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
        return metadata.buildSessionFactory();
    }

    private void executeInTransaction(Consumer<Session> action) {
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                action.accept(session);
                transaction.commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
