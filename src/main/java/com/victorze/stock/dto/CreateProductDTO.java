package com.victorze.stock.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Product 📚", description = "Product characteristic")
public class CreateProductDTO {

    @Schema(description = "Nombre del producto", maxLength = 50, example = "Platano")
    private String name;

    @Schema(description = "Precio del producto", example = "50")
    private double price;

    @Schema(description = "Id de la categoría")
    private Long categoryId;

}
