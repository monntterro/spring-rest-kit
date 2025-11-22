package space.monntterro.springrestkit.controller;

import space.monntterro.springrestkit.mapper.MapperKit;
import space.monntterro.springrestkit.method.*;
import space.monntterro.springrestkit.service.ServiceKit;

import java.util.Collection;

public abstract class CrudKit<E, ID, M> implements
        GetOne<ID, M>,
        PostOne<M>,
        GetAll<M>,
        PutOne<ID, M>,
        DeleteOne<ID, M> {
    private final ServiceKit<E, ID, M> service;
    private final MapperKit<E, M> mapper;

    public CrudKit(ServiceKit<E, ID, M> service, MapperKit<E, M> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public M getOne(ID id) {
        return mapper.toModel(service.getOne(id).orElseThrow());
    }

    @Override
    public Collection<M> getAll() {
        return service.getAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public M createOne(M model) {
        E entity = mapper.toEntity(model);
        E resultEntity = service.createOne(entity);
        return mapper.toModel(resultEntity);
    }

    @Override
    public M updateOne(ID id, M model) {
        E entity = service.updateOne(id, model);
        return mapper.toModel(entity);
    }

    @Override
    public M deleteOne(ID id) {
        E entity = service.deleteOne(id);
        return mapper.toModel(entity);
    }
}