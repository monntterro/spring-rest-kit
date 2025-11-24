package space.monntterro.springrestkit.core;

import space.monntterro.springrestkit.kit.AbstractKitService;

public interface BaseKitRestController<TEntity, TId, TDto> {

    AbstractKitService<TEntity, TId, TDto> getService();

    BaseKitMapper<TEntity, TDto> getMapper();
}
