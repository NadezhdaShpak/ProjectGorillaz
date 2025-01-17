package com.javarush.repository;

import java.util.Collection;
import java.util.stream.Stream;

public interface Repository<T> {

    Collection<T> getAll();

    T get(long id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);

    Stream<T> find(T pattern);
}
