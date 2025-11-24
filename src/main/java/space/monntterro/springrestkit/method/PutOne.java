package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.core.BaseRestController;

public interface PutOne<TEntity, TId, TDto> extends BaseRestController<TEntity, TId, TDto> {

    @PutMapping("/{id}")
    default TDto updateOne(@PathVariable("id") TId id, @RequestBody TDto dto) {
        TEntity entity = getService().updateOne(id, dto);
        return getMapper().toDto(entity);
    }
}
