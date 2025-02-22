package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import java.util.List;
import java.util.Optional;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Product;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    Product update(Long id, Product fieldUpdateProduct);
    Optional<Product> delete(Long id);
}
