package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PutOne<ID, M> {

    @PutMapping("/{id}")
    M updateOne(@PathVariable("id") ID id, @RequestBody M model);
}
