package space.monntterro.springrestkit.method;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

public interface GetAll<M> {

    @GetMapping
    Collection<M> getAll();
}
