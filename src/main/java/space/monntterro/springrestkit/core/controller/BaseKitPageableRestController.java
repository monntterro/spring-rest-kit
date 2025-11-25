package space.monntterro.springrestkit.core.controller;

import space.monntterro.springrestkit.core.service.BaseKitPageableService;

public interface BaseKitPageableRestController<TEntity, TId, TDto> extends BaseKitRestController<TEntity, TId, TDto> {

    @Override
    BaseKitPageableService<TEntity, TId, TDto> getService();
}
