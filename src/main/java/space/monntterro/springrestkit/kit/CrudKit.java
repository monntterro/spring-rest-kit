package space.monntterro.springrestkit.kit;

import space.monntterro.springrestkit.core.BaseMapper;
import space.monntterro.springrestkit.method.*;

public abstract class CrudKit<E, ID, D> extends AbstractControllerKit<E, ID, D> implements
        GetOne<E, ID, D>,
        GetAll<E, ID, D>,
        PostOne<E, ID, D>,
        PutOne<E, ID, D>,
        DeleteOne<E, ID, D> {

    public CrudKit(AbstractServiceKit<E, ID, D> service, BaseMapper<E, D> mapper) {
        super(service, mapper);
    }
}
