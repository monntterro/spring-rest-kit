package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.controller.RestKit;

public interface GetOne<E, ID, M> extends RestKit<E, ID, M> {

    @GetMapping("/{id}")
    default M getOne(@PathVariable("id") ID id) {
        E entity = getService().getOne(id).orElseThrow();
        return getMapper().toModel(entity);
    }
}
