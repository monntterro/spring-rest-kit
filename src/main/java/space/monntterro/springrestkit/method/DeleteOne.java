package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.core.BaseRestController;

public interface DeleteOne<TEntity, TId, TDto> extends BaseRestController<TEntity, TId, TDto> {

    @DeleteMapping("/{id}")
    default TDto deleteOne(@PathVariable("id") TId id) {
        TEntity entity = getService().deleteOne(id);
        return getMapper().toDto(entity);
    }
}
