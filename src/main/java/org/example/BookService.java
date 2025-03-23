package org.example;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final DAO<Book> bookDAO;

    public BookService(DAO<Book> bookDAO) {
        this.bookDAO = bookDAO;
    }

    void persist(Book book) {
        bookDAO.persist(book);
    }

    Optional<Book> findById(Integer id) {
        return bookDAO.findById(id);
    }

    Optional<List<Book>> findAll() {
        return bookDAO.findAll();
    }

    void update(Book book) {
        bookDAO.update(book);
    }

    void delete(Book book) {
        bookDAO.delete(book);
    }

    void deleteAll() {
        bookDAO.deleteAll();
    }
}
