package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseMapper;
import space.monntterro.springrestkit.method.*;

public abstract class CrudControllerKit<TEntity, TId, TDto> extends AbstractControllerKit<TEntity, TId, TDto> implements
        GetOne<TEntity, TId, TDto>,
        GetAll<TEntity, TId, TDto>,
        PostOne<TEntity, TId, TDto>,
        PutOne<TEntity, TId, TDto>,
        DeleteOne<TEntity, TId, TDto> {

    public CrudControllerKit(AbstractServiceKit<TEntity, TId, TDto> service, BaseMapper<TEntity, TDto> mapper) {
        super(service, mapper);
    }
}
