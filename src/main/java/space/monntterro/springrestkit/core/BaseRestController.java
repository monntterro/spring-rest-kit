package space.monntterro.springrestkit.core;

import space.monntterro.springrestkit.kit.AbstractServiceKit;

public interface BaseRestController<TEntity, TId, TDto> {

    AbstractServiceKit<TEntity, TId, TDto> getService();

    BaseMapper<TEntity, TDto> getMapper();
}
