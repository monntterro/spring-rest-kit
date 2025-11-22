package space.monntterro.springrestkit.service;

import space.monntterro.springrestkit.mapper.MapperKit;
import space.monntterro.springrestkit.repository.RepositoryKit;

import java.util.Collection;
import java.util.Optional;

public abstract class ServiceKit<E, ID, M> {
    private final RepositoryKit<E, ID> repository;
    private final MapperKit<E, M> mapper;

    public ServiceKit(RepositoryKit<E, ID> repository, MapperKit<E, M> mapper) {
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

    public E updateOne(ID id, M model) {
        E entity = repository.findById(id).orElseThrow();
        entity = mapper.updateWithNull(entity, model);
        return repository.save(entity);
    }

    public E deleteOne(ID id) {
        E entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.deleteById(id);
        }
        return entity;
    }
}
