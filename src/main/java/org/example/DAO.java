package org.example;

public interface DAO<T> extends DeletableDAO<T>, UpdateableDAO<T>, FindableDAO<T>, PersistableDAO<T> {
}
