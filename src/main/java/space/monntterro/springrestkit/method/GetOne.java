package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.core.BaseRestController;

public interface GetOne<TEntity, TId, TDto> extends BaseRestController<TEntity, TId, TDto> {

    @GetMapping("/{id}")
    default TDto getOne(@PathVariable("id") TId id) {
        TEntity entity = getService().getOne(id).orElseThrow();
        return getMapper().toDto(entity);
    }
}
