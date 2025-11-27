package space.monntterro.springrestkit.core.repository;

import java.util.Collection;
import java.util.Optional;

public interface BaseKitRepository<TEntity, TId> {

    Optional<TEntity> findById(TId id);

    Collection<TEntity> findAll();

    <S extends TEntity> S save(S entity);

    void deleteById(TId id);
}
