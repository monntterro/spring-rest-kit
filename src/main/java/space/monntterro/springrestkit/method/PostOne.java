package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.controller.RestKit;

public interface PostOne<E, ID, M> extends RestKit<E, ID, M> {

    @PostMapping
    default M createOne(@RequestBody M model) {
        E entity = getMapper().toEntity(model);
        E resultEntity = getService().createOne(entity);
        return getMapper().toModel(resultEntity);
    }
}
