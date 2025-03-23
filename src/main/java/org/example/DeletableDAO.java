package org.example;

public interface DeletableDAO<T> {
    void delete(T entity);
    void deleteAll();
}
