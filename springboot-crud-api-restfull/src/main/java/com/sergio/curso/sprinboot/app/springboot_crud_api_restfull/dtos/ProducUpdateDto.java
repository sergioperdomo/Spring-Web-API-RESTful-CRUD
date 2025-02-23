package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.controllers.ProductController;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
public class ProducUpdateDto {

    /***
     * Para hacer validaciónes se usa el @Valid antes del @RequestBody como se muestra a continuación:
     * @see  ProductController#update(Long, ProducUpdateDto, BindingResult)
     * Quitamos @NotBlank, @NotNull, @Positive para permitir que los campos sean opcionales.
     */

    @Size(min = 3, message = "debe tener entre 3 en adelante.")
    private String name;

    @Positive(message = "El precio debe ser mayor a cero")
    private Integer price;

    @Size(min = 10, message = "debe tener al menos 10 caracteres")
    private String description;
}
