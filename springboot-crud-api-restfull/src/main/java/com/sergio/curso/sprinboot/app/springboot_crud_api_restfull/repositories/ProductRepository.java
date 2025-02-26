package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    //    Método personalizado para verificar si existe sku
    boolean existsBySku(String sku);
}
