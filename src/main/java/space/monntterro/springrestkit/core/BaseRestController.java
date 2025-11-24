package space.monntterro.springrestkit.core;

import space.monntterro.springrestkit.kit.AbstractServiceKit;

public interface BaseRestController<E, ID, D> {

    AbstractServiceKit<E, ID, D> getService();

    BaseMapper<E, D> getMapper();
}
