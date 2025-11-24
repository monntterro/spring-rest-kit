package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseMapper;
import space.monntterro.springrestkit.core.BaseRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractServiceKit<E, ID, D> {
    private final BaseRepository<E, ID> repository;
    private final BaseMapper<E, D> mapper;

    public AbstractServiceKit(BaseRepository<E, ID> repository, BaseMapper<E, D> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<E> getOne(ID id) {
        return repository.findById(id);
    }

    public Collection<E> getAll() {
        return repository.findAll();
    }

    public E createOne(E entity) {
        return repository.save(entity);
    }

    public E updateOne(ID id, D dto) {
        E entity = repository.findById(id).orElseThrow();
        entity = mapper.updateWithNull(entity, dto);
        return repository.save(entity);
    }

    public E deleteOne(ID id) {
        E entity = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        return entity;
    }
}
