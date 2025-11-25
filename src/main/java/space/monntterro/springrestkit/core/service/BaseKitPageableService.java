package space.monntterro.springrestkit.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseKitPageableService<TEntity, TId, TDto> extends BaseKitService<TEntity, TId, TDto> {
    Page<TEntity> getAll(Pageable pageable);
}
