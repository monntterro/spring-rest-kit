package space.monntterro.springrestkit.kit.service;

import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.core.repository.BaseKitRepository;
import space.monntterro.springrestkit.core.service.BaseKitService;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractKitService<TEntity, TId, TDto> implements BaseKitService<TEntity, TId, TDto> {
    private final BaseKitRepository<TEntity, TId> repository;
    private final BaseKitMapper<TEntity, TDto> mapper;

    public AbstractKitService(BaseKitRepository<TEntity, TId> repository, BaseKitMapper<TEntity, TDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<TEntity> getOne(TId id) {
        return repository.findById(id);
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
        TEntity entity = repository.findById(id).orElseThrow();
        entity = mapper.updateWithNull(entity, dto);
        return repository.save(entity);
    }

    @Override
    public TEntity deleteOne(TId id) {
        TEntity entity = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return entity;
    }
}