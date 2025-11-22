package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PostOne<M> {

    @PostMapping
    M createOne(@RequestBody M model);
}
