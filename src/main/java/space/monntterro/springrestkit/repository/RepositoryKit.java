package space.monntterro.springrestkit.repository;

import java.util.Collection;
import java.util.Optional;

public interface RepositoryKit<T, ID> {

    Optional<T> findById(ID id);

    Collection<T> findAll();

    T save(T entity);

    void deleteById(ID id);
}
