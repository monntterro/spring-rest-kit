package space.monntterro.springrestkit.impl.controller;

import space.monntterro.springrestkit.core.controller.BaseKitRestController;
import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.core.service.BaseKitService;
import space.monntterro.springrestkit.impl.service.AbstractKitService;

public abstract class AbstractKitController<TEntity, TId, TDto> implements BaseKitRestController<TEntity, TId, TDto> {
    private final AbstractKitService<TEntity, TId, TDto> service;
    private final BaseKitMapper<TEntity, TDto> mapper;

    public AbstractKitController(AbstractKitService<TEntity, TId, TDto> service, BaseKitMapper<TEntity, TDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public BaseKitService<TEntity, TId, TDto> getService() {
        return service;
    }

    @Override
    public BaseKitMapper<TEntity, TDto> getMapper() {
        return mapper;
    }
}