package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.core.BaseRestController;

public interface PutOne<E, ID, D> extends BaseRestController<E, ID, D> {

    @PutMapping("/{id}")
    default D updateOne(@PathVariable("id") ID id, @RequestBody D dto) {
        E entity = getService().updateOne(id, dto);
        return getMapper().toDto(entity);
    }
}
