package org.example;

import java.util.List;
import java.util.Optional;

public interface FindableDAO<T> {
    Optional<T> findById(Integer id);
    Optional<List<T>> findAll();
}
