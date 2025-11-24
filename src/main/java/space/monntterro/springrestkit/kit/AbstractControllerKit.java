package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseMapper;
import space.monntterro.springrestkit.core.BaseRestController;

public abstract class AbstractControllerKit<TEntity, TId, TDto> implements BaseRestController<TEntity, TId, TDto> {
    private final AbstractServiceKit<TEntity, TId, TDto> service;
    private final BaseMapper<TEntity, TDto> mapper;

    public AbstractControllerKit(AbstractServiceKit<TEntity, TId, TDto> service, BaseMapper<TEntity, TDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public AbstractServiceKit<TEntity, TId, TDto> getService() {
        return service;
    }

    @Override
    public BaseMapper<TEntity, TDto> getMapper() {
        return mapper;
    }
}
