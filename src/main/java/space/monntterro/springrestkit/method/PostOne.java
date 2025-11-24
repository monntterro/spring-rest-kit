package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.core.BaseRestController;

public interface PostOne<E, ID, D> extends BaseRestController<E, ID, D> {

    @PostMapping
    default D createOne(@RequestBody D dto) {
        E entity = getMapper().toEntity(dto);
        E resultEntity = getService().createOne(entity);
        return getMapper().toDto(resultEntity);
    }
}
