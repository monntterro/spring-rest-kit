package space.monntterro.springrestkit.controller;

import space.monntterro.springrestkit.mapper.MapperKit;
import space.monntterro.springrestkit.service.ServiceKit;

public abstract class AbstractRestController<E, ID, M> implements RestKit<E, ID, M> {
    private final ServiceKit<E, ID, M> service;
    private final MapperKit<E, M> mapper;

    public AbstractRestController(ServiceKit<E, ID, M> service, MapperKit<E, M> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ServiceKit<E, ID, M> getService() {
        return service;
    }

    @Override
    public MapperKit<E, M> getMapper() {
        return mapper;
    }
}
