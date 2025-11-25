package space.monntterro.springrestkit.kit.controller;

import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.kit.service.AbstractKitService;
import space.monntterro.springrestkit.method.*;

public abstract class CrudKitController<TEntity, TId, TDto> extends AbstractKitController<TEntity, TId, TDto> implements
        GetOne<TEntity, TId, TDto>,
        GetAll<TEntity, TId, TDto>,
        PostOne<TEntity, TId, TDto>,
        PutOne<TEntity, TId, TDto>,
        DeleteOne<TEntity, TId, TDto> {

    public CrudKitController(AbstractKitService<TEntity, TId, TDto> service, BaseKitMapper<TEntity, TDto> mapper) {
        super(service, mapper);
    }
}
