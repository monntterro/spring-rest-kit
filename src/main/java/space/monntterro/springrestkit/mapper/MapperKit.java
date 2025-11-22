package space.monntterro.springrestkit.mapper;

public interface MapperKit<E, M> {
    M toModel(E entity);

    E toEntity(M model);

    E updateWithNull(E entity, M model);
}
