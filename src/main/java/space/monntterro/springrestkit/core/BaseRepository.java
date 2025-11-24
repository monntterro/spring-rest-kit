package space.monntterro.springrestkit.core;

import java.util.Collection;
import java.util.Optional;

public interface BaseRepository<TEntity, TId> {

    Optional<TEntity> findById(TId id);

    Collection<TEntity> findAll();

    TEntity save(TEntity entity);

    void deleteById(TId id);
}
