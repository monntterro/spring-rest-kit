package space.monntterro.springrestkit.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseKitPageableRepository<TEntity, TId> extends BaseKitRepository<TEntity, TId> {

    Page<TEntity> findAll(Pageable pageable);
}
