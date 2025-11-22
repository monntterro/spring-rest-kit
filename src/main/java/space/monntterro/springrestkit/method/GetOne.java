package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface GetOne<ID, M> {

    @GetMapping("/{id}")
    M getOne(@PathVariable("id") ID id);
}
