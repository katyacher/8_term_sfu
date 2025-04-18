package edu.sfu.lab5.controller;

/**
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/jewelry")
@Tag(name = "Ювелирные изделия", description = "API для работы с ювелирными изделиями")
public class JewelryController {

    @GetMapping
    @Operation(
        summary = "Получить список изделий",
        description = "Возвращает отфильтрованный список ювелирных изделий с пагинацией",
        responses = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
        }
    )
    public Page<Jewelry> getJewelry(
        @Parameter(description = "Минимальная цена") 
        @RequestParam(required = false) BigDecimal minPrice,
        
        @Parameter(description = "Максимальная цена") 
        @RequestParam(required = false) BigDecimal maxPrice,
        
        @Parameter(description = "Минимальный вес") 
        @RequestParam(required = false) Double minWeight,
        
        @Parameter(description = "Тип материала (GEMSTONE/METAL)") 
        @RequestParam(required = false) String materialType,
        
        @Parameter(hidden = true) Pageable pageable) {
        // реализация метода
    }
}
**/