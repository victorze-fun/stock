package com.victorze.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {

    private String name;
    private double price;
    private Long categoryId;

}
