package br.com.fiap.example.basicproductssystem.service;

import java.util.List;
import java.util.Optional;

public interface CategoryService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T create(T o);

    public T partialUpdate(ID id, T o);

    void removeById(ID id);

    boolean existsById(ID id);

}