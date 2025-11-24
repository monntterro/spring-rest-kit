package space.monntterro.springrestkit.core;

public interface BaseMapper<TEntity, TDto> {
    TDto toDto(TEntity entity);

    TEntity toEntity(TDto dto);

    TEntity updateWithNull(TEntity entity, TDto dto);
}
