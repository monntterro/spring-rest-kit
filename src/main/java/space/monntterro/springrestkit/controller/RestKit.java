package space.monntterro.springrestkit.controller;

import space.monntterro.springrestkit.mapper.MapperKit;
import space.monntterro.springrestkit.service.ServiceKit;

public interface RestKit<E, ID, M> {

    ServiceKit<E, ID, M> getService();

    MapperKit<E, M> getMapper();
}
