package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.controller.AbstractRestController;
import space.monntterro.springrestkit.mapper.MapperKit;
import space.monntterro.springrestkit.method.*;
import space.monntterro.springrestkit.service.ServiceKit;

public abstract class CrudKit<E, ID, M> extends AbstractRestController<E, ID, M> implements
        GetOne<E, ID, M>,
        GetAll<E, ID, M>,
        PostOne<E, ID, M>,
        PutOne<E, ID, M>,
        DeleteOne<E, ID, M> {

    public CrudKit(ServiceKit<E, ID, M> service, MapperKit<E, M> mapper) {
        super(service, mapper);
    }
}
