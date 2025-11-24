package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseKitMapper;
import space.monntterro.springrestkit.core.BaseKitRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractKitService<TEntity, TId, TDto> {
    private final BaseKitRepository<TEntity, TId> repository;
    private final BaseKitMapper<TEntity, TDto> mapper;

    public AbstractKitService(BaseKitRepository<TEntity, TId> repository, BaseKitMapper<TEntity, TDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<TEntity> getOne(TId id) {
        return repository.findById(id);
    }

    public Collection<TEntity> getAll() {
        return repository.findAll();
    }

    public TEntity createOne(TEntity entity) {
        return repository.save(entity);
    }

    public TEntity updateOne(TId id, TDto dto) {
        TEntity entity = repository.findById(id).orElseThrow();
        entity = mapper.updateWithNull(entity, dto);
        return repository.save(entity);
    }

    public TEntity deleteOne(TId id) {
        TEntity entity = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return entity;
    }
}
