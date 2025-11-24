package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.core.BaseRestController;

public interface PostOne<TEntity, TId, TDto> extends BaseRestController<TEntity, TId, TDto> {

    @PostMapping
    default TDto createOne(@RequestBody TDto dto) {
        TEntity entity = getMapper().toEntity(dto);
        TEntity resultEntity = getService().createOne(entity);
        return getMapper().toDto(resultEntity);
    }
}
