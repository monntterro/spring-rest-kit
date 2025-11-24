package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseKitMapper;
import space.monntterro.springrestkit.core.BaseKitRestController;

public abstract class AbstractKitController<TEntity, TId, TDto> implements BaseKitRestController<TEntity, TId, TDto> {
    private final AbstractKitService<TEntity, TId, TDto> service;
    private final BaseKitMapper<TEntity, TDto> mapper;

    public AbstractKitController(AbstractKitService<TEntity, TId, TDto> service, BaseKitMapper<TEntity, TDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public AbstractKitService<TEntity, TId, TDto> getService() {
        return service;
    }

    @Override
    public BaseKitMapper<TEntity, TDto> getMapper() {
        return mapper;
    }
}
