package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import java.util.List;
import java.util.Optional;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductUpdateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductCreateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Product;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(ProductCreateDto productCreateDto);
    Product update(Long id, ProductUpdateDto fieldProductUpdateDto);
    Optional<Product> delete(Long id);
}
