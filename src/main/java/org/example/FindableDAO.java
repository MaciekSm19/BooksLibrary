package org.example;

import java.util.List;

public interface FindableDAO<T> {
    T findById(Integer id);
    List<T> findAll();
}
