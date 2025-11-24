package space.monntterro.springrestkit.core;

import java.util.Collection;
import java.util.Optional;

public interface BaseRepository<E, ID> {

    Optional<E> findById(ID id);

    Collection<E> findAll();

    E save(E entity);

    void deleteById(ID id);
}
