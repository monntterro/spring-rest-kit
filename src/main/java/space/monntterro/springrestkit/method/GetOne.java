package space.monntterro.springrestkit.method;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.monntterro.springrestkit.core.controller.BaseKitRestController;

public interface GetOne<TEntity, TId, TDto> extends BaseKitRestController<TEntity, TId, TDto> {

    @Operation(summary = "Получить сущность [название сущности смотреть в описании контроллера] по ID",
               description = "Возвращает сущность в виде DTO по указанному идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Сущность с указанным ID не найдена")
    })
    @GetMapping("/{id}")
    default TDto getOne(@PathVariable("id") TId id) {
        TEntity entity = getService().getOne(id).orElseThrow();
        return getMapper().toDto(entity);
    }
}
