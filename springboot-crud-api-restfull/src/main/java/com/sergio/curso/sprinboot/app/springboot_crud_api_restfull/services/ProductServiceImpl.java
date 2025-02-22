package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import java.util.List;
import java.util.Optional;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Product;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Long id, Product fieldUpdateProduct) {
        Optional<Product> existsProduct = productRepository.findById(id);

        if (existsProduct.isEmpty()){
            throw new EntityNotFoundException("Producto no encontrado con ID: " + id);
        }

        // SOLO SE ACTUALIZAN LOS CAMPOS QUE NO SEAN NULL
        Product idProductFound = existsProduct.get();
        if (fieldUpdateProduct.getName() != null){
            idProductFound.setName(fieldUpdateProduct.getName());
        }
        if (fieldUpdateProduct.getPrice() != null){
            idProductFound.setPrice(fieldUpdateProduct.getPrice());
        }
        if (fieldUpdateProduct.getDescription() != null){
            idProductFound.setDescription(fieldUpdateProduct.getDescription());
        }
        return productRepository.save(idProductFound);
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptionalDb = productRepository.findById(id);
        productOptionalDb.ifPresent(productDb -> {
            productRepository.delete(productDb);
        });

        return productOptionalDb;
    }

}
