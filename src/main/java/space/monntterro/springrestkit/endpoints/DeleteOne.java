package space.monntterro.springrestkit.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.core.controller.BaseKitRestController;

public interface DeleteOne<TEntity, TId, TDto> extends BaseKitRestController<TEntity, TId, TDto> {

    @Operation(summary = "Удалить сущность [название сущности смотреть в описании контроллера] по ID",
               description = "Удаляет сущность и возвращает удалённый объект в виде DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Сущность с указанным ID не найдена")
    })
    @DeleteMapping("/{id}")
    default TDto deleteOne(@PathVariable("id") TId id) {
        TEntity entity = getService().deleteOne(id);
        return getMapper().toDto(entity);
    }
}