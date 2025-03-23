package org.example;

import java.util.List;

public interface DAO<T> {
    void persist(T entity);
    T findById(Integer id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
    void deleteAll();
}
