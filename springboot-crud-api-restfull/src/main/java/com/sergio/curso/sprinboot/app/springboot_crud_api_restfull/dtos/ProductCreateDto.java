package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductCreateDto {

    @NotBlank(message = "no puede estar vacío.")
    @Size(min = 3, max = 20, message = "debe tener entre 3 y 20 caracteres")
    private String name;

    @NotNull
    @Positive(message = "debe ser mayor al cero.")
    private Integer price;

    @Size(min = 10, message = "debe tener al menos 10 caracteres")
    @NotBlank(message = "no puede estar vacío.")
    private String description;
}
