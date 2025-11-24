package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.controller.RestKit;

public interface DeleteOne<E, ID, M> extends RestKit<E, ID, M> {

    @DeleteMapping("/{id}")
    default M deleteOne(@PathVariable("id") ID id) {
        E entity = getService().deleteOne(id);
        return getMapper().toModel(entity);
    }
}
