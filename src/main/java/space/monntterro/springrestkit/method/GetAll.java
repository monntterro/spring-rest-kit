package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import space.monntterro.springrestkit.controller.RestKit;

import java.util.Collection;

public interface GetAll<E, ID, M> extends RestKit<E, ID, M> {

    @GetMapping
    default Collection<M> getAll() {
        return getService().getAll().stream()
                .map(getMapper()::toModel)
                .toList();
    }
}
