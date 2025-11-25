package space.monntterro.springrestkit.kit.controller;

import space.monntterro.springrestkit.core.controller.BaseKitPageableRestController;
import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.core.service.BaseKitPageableService;
import space.monntterro.springrestkit.kit.service.AbstractKitPageableService;

public class AbstractKitPageableController<TEntity, TId, TDto> implements BaseKitPageableRestController<TEntity, TId, TDto> {
    private final AbstractKitPageableService<TEntity, TId, TDto> service;
    private final BaseKitMapper<TEntity, TDto> mapper;

    public AbstractKitPageableController(AbstractKitPageableService<TEntity, TId, TDto> service,
                                         BaseKitMapper<TEntity, TDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public BaseKitPageableService<TEntity, TId, TDto> getService() {
        return service;
    }

    @Override
    public BaseKitMapper<TEntity, TDto> getMapper() {
        return mapper;
    }
}
