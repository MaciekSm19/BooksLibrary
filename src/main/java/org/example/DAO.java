package org.example;

import java.io.Serializable;
import java.util.List;

public interface DAO<T extends Serializable> {
    void persist(T entity);
    T findById(Integer id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
    void deleteAll();
}
