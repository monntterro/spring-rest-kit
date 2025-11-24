package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.controller.RestKit;

public interface PutOne<E, ID, M> extends RestKit<E, ID, M> {

    @PutMapping("/{id}")
    default M updateOne(@PathVariable("id") ID id, @RequestBody M model) {
        E entity = getService().updateOne(id, model);
        return getMapper().toModel(entity);
    }
}
