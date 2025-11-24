package space.monntterro.springrestkit.core;

public interface BaseKitMapper<TEntity, TDto> {
    TDto toDto(TEntity entity);

    TEntity toEntity(TDto dto);

    TEntity updateWithNull(TEntity entity, TDto dto);
}
