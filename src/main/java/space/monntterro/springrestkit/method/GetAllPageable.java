package space.monntterro.springrestkit.method;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import space.monntterro.springrestkit.core.controller.BaseKitPageableRestController;

public interface GetAllPageable<TEntity, TId, TDto> extends BaseKitPageableRestController<TEntity, TId, TDto> {

    @Operation(summary = "Получить все сущности [название сущности смотреть в описании контроллера] с пагинацией",
               description = "Возвращает список всех сущностей в виде DTO с пагинацией")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущности успешно получены")
    })
    @GetMapping
    default PagedModel<TDto> getAll(@ParameterObject Pageable pageable) {
        Page<TDto> orders = getService().getAll(pageable).map(getMapper()::toDto);
        return new PagedModel<>(orders);
    }
}
