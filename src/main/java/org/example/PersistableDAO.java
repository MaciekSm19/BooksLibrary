package org.example;

public interface PersistableDAO<T> {
    void persist(T entity);
}
