package space.monntterro.springrestkit.core.impl.service;

import space.monntterro.springrestkit.core.exception.NotFoundException;
import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.core.repository.BaseKitRepository;
import space.monntterro.springrestkit.core.service.BaseKitService;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public abstract class AbstractKitService<TEntity, TId, TDto> implements BaseKitService<TEntity, TId, TDto> {
    private final BaseKitRepository<TEntity, TId> repository;
    private final BaseKitMapper<TEntity, TDto> mapper;

    public AbstractKitService(BaseKitRepository<TEntity, TId> repository, BaseKitMapper<TEntity, TDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TEntity getOne(TId id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id, getEntityClass()));
    }

    @Override
    public Collection<TEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public TEntity createOne(TEntity entity) {
        return repository.save(entity);
    }

    @Override
    public TEntity updateOne(TId id, TDto dto) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, getEntityClass()));
        entity = mapper.updateWithNull(entity, dto);
        return repository.save(entity);
    }

    @Override
    public TEntity deleteOne(TId id) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, getEntityClass()));
        repository.deleteById(id);
        return entity;
    }

    /**
     * Returns the class of the generic TEntity
     */
    @SuppressWarnings("unchecked")
    private Class<TEntity> getEntityClass() {
        return (Class<TEntity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}