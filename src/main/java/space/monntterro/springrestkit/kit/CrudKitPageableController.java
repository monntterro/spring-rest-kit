package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.endpoints.*;
import space.monntterro.springrestkit.impl.controller.AbstractKitPageableController;
import space.monntterro.springrestkit.impl.service.AbstractKitPageableService;

public class CrudKitPageableController<TEntity, TId, TDto> extends AbstractKitPageableController<TEntity, TId, TDto> implements
        GetOne<TEntity, TId, TDto>,
        GetAllPageable<TEntity, TId, TDto>,
        PostOne<TEntity, TId, TDto>,
        PutOne<TEntity, TId, TDto>,
        DeleteOne<TEntity, TId, TDto> {

    public CrudKitPageableController(AbstractKitPageableService<TEntity, TId, TDto> service,
                                     BaseKitMapper<TEntity, TDto> mapper) {
        super(service, mapper);
    }
}
