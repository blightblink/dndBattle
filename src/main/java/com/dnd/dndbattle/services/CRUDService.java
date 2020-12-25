package com.dnd.dndbattle.services;

import java.util.List;

public interface CRUDService<T> {
    List<?> listAll();

    T getById(Long id);

    T saveOrUpdate(T t);

    void deleteById(Long id);
}
