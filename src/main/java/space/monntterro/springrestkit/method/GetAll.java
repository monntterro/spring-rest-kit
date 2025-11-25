package space.monntterro.springrestkit.method;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import space.monntterro.springrestkit.core.controller.BaseKitRestController;

import java.util.Collection;

public interface GetAll<TEntity, TId, TDto> extends BaseKitRestController<TEntity, TId, TDto> {

    @Operation(summary = "Получить все сущности [название сущности смотреть в описании контроллера]",
               description = "Возвращает список всех сущностей в виде DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущности успешно получены")
    })
    @GetMapping
    default Collection<TDto> getAll() {
        return getService().getAll().stream()
                .map(getMapper()::toDto)
                .toList();
    }
}
