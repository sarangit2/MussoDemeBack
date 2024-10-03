package com.kalanso.mussoback.Service;

import com.kalanso.mussoback.Model.SuperAdmin;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    SuperAdmin add(SuperAdmin superAdmin, Long roleId);

    T add(T entity);
    List<T> List();
    Optional<T> findById(ID id);
    T update(T entity, ID id);
    void delete(ID id);
}
