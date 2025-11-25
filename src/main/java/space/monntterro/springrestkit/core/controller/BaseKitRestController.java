package space.monntterro.springrestkit.core.controller;

import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.core.service.BaseKitService;

public interface BaseKitRestController<TEntity, TId, TDto> {

    BaseKitService<TEntity, TId, TDto> getService();

    BaseKitMapper<TEntity, TDto> getMapper();
}