package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductCreateDto {

    @NotBlank(message = "{productCreateDto.name.notBlank}")
    @Size(min = 3, max = 20, message = "{productCreateDto.name.size}")
    private String name;


    @Positive(message = "{productCreateDto.price.positive}")
    private Integer price;

    @Size(min = 10, message = "{productCreateDto.description.size}")
    @NotBlank(message = "{productCreateDto.description.notBlank}")
    private String description;

    @NotBlank(message = "{productCreateDto.sku.notBlank}")
    private String sku;
}
