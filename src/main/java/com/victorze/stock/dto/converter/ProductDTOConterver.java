package com.victorze.stock.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.victorze.stock.dto.CreateProductDTO;
import com.victorze.stock.dto.ProductDTO;
import com.victorze.stock.models.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDTOConterver {

    private final ModelMapper modelMapper;

    public ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product convertToProduct(CreateProductDTO createProductDTO) {
        var result = modelMapper.map(createProductDTO, Product.class);
        result.setId(null);
        return result;
    }

}
