package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseMapper;
import space.monntterro.springrestkit.core.BaseRestController;

public abstract class AbstractControllerKit<E, ID, D> implements BaseRestController<E, ID, D> {
    private final AbstractServiceKit<E, ID, D> service;
    private final BaseMapper<E, D> mapper;

    public AbstractControllerKit(AbstractServiceKit<E, ID, D> service, BaseMapper<E, D> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public AbstractServiceKit<E, ID, D> getService() {
        return service;
    }

    @Override
    public BaseMapper<E, D> getMapper() {
        return mapper;
    }
}
