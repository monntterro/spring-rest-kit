package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DeleteOne<ID, M> {

    @DeleteMapping("/{id}")
    M deleteOne(@PathVariable("id") ID id);
}
