package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.core.BaseRestController;

public interface GetOne<E, ID, D> extends BaseRestController<E, ID, D> {

    @GetMapping("/{id}")
    default D getOne(@PathVariable("id") ID id) {
        E entity = getService().getOne(id).orElseThrow();
        return getMapper().toDto(entity);
    }
}
