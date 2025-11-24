package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import space.monntterro.springrestkit.core.BaseRestController;

import java.util.Collection;

public interface GetAll<E, ID, D> extends BaseRestController<E, ID, D> {

    @GetMapping
    default Collection<D> getAll() {
        return getService().getAll().stream()
                .map(getMapper()::toDto)
                .toList();
    }
}
