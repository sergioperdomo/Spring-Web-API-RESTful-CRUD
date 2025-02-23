package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.controllers.ProductController;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
public class ProductUpdateDto {

    /***
     * Para hacer validaciónes se usa el @Valid antes del @RequestBody como se muestra a continuación:
     * @see  ProductController#update(Long, ProductUpdateDto, BindingResult)
     * Quitamos @NotBlank, @NotNull, @Positive para permitir que los campos sean opcionales.
     */

    @Size(min = 3, message = "{productUpdateDto.name.size}")
    private String name;

    @Positive(message = "{productUpdateDto.price.positive}")
    private Integer price;

    @Size(min = 10, message = "{productUpdateDto.description.size}")
    private String description;
}
