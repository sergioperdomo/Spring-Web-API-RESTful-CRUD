package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Table(name = "products")
@Data
public class Product {

    /***
     * Para hacer validaciónes se usa el @Valid antes del @RequestBody como se muestra a continuación:
     * @see  com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.controllers.ProductController#update(Long, Product)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @Positive(message = "El precio debe ser mayor a cero")
    private Integer price;

    @NotBlank
    private String description;
}
