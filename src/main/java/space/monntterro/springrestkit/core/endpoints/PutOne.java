package space.monntterro.springrestkit.core.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.core.controller.BaseKitRestController;

public interface PutOne<TEntity, TId, TDto> extends BaseKitRestController<TEntity, TId, TDto> {

    @Operation(summary = "Обновить сущность [название сущности смотреть в описании контроллера] по ID",
               description = "Обновляет сущность на основе переданного DTO и возвращает обновлённый объект")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Сущность с указанным ID не найдена"),
    })
    @PutMapping("/{id}")
    default TDto updateOne(@Valid @PathVariable("id") TId id, @RequestBody TDto dto) {
        TEntity entity = getService().updateOne(id, dto);
        return getMapper().toDto(entity);
    }
}
