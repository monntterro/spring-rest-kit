package space.monntterro.springrestkit.core.service;

import java.util.Collection;

public interface BaseKitService<TEntity, TId, TDto> {
    TEntity getOne(TId id);

    Collection<TEntity> getAll();

    TEntity createOne(TEntity entity);

    TEntity updateOne(TId id, TDto dto);

    TEntity deleteOne(TId id);
}
