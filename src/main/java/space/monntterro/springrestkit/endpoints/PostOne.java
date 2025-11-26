package space.monntterro.springrestkit.endpoints;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import space.monntterro.springrestkit.core.controller.BaseKitRestController;

public interface PostOne<TEntity, TId, TDto> extends BaseKitRestController<TEntity, TId, TDto> {

    @Operation(summary = "Создать новую сущность [название сущности смотреть в описании контроллера]",
               description = "Создаёт новую сущность на основе переданного DTO и возвращает её")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность успешно создана"),
    })
    @PostMapping
    default TDto createOne(@Valid @RequestBody TDto dto) {
        TEntity entity = getMapper().toEntity(dto);
        TEntity resultEntity = getService().createOne(entity);
        return getMapper().toDto(resultEntity);
    }
}
