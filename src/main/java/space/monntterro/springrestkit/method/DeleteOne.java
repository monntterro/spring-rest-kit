package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.core.BaseRestController;

public interface DeleteOne<E, ID, D> extends BaseRestController<E, ID, D> {

    @DeleteMapping("/{id}")
    default D deleteOne(@PathVariable("id") ID id) {
        E entity = getService().deleteOne(id);
        return getMapper().toDto(entity);
    }
}
