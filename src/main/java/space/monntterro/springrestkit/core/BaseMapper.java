package space.monntterro.springrestkit.core;

public interface BaseMapper<E, D> {
    D toDto(E entity);

    E toEntity(D dto);

    E updateWithNull(E entity, D dto);
}
