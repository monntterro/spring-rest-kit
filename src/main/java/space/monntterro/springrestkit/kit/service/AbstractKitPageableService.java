package space.monntterro.springrestkit.kit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.monntterro.springrestkit.core.mapper.BaseKitMapper;
import space.monntterro.springrestkit.core.repository.BaseKitPageableRepository;
import space.monntterro.springrestkit.core.service.BaseKitPageableService;

public class AbstractKitPageableService<TEntity, TId, TDto> extends AbstractKitService<TEntity, TId, TDto>
        implements BaseKitPageableService<TEntity, TId, TDto> {
    private final BaseKitPageableRepository<TEntity, TId> repository;

    public AbstractKitPageableService(BaseKitPageableRepository<TEntity, TId> repository,
                                      BaseKitMapper<TEntity, TDto> mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public Page<TEntity> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
