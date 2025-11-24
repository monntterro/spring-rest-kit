package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import space.monntterro.springrestkit.core.BaseRestController;

import java.util.Collection;

public interface GetAll<TEntity, TId, TDto> extends BaseRestController<TEntity, TId, TDto> {

    @GetMapping
    default Collection<TDto> getAll() {
        return getService().getAll().stream()
                .map(getMapper()::toDto)
                .toList();
    }
}
