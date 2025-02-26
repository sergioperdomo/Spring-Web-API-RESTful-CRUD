package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import java.util.List;
import java.util.Optional;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductUpdateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductCreateDto;
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
    public Product save(ProductCreateDto productCreateDto) {
//        Convertir DTO a entidad Product
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());
        product.setDescription(productCreateDto.getDescription());
        product.setSku(product.getSku());
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Long id, ProductUpdateDto fieldProductUpdateDto) {
        Optional<Product> existsProduct = productRepository.findById(id);

        if (existsProduct.isEmpty()){
            throw new EntityNotFoundException("Producto no encontrado con ID: " + id);
        }

        // SOLO SE ACTUALIZAN LOS CAMPOS QUE NO SEAN NULL
        Product idProductFound = existsProduct.get();
        if (fieldProductUpdateDto.getName() != null){
            idProductFound.setName(fieldProductUpdateDto.getName());
        }
        if (fieldProductUpdateDto.getPrice() != null){
            idProductFound.setPrice(fieldProductUpdateDto.getPrice());
        }
        if (fieldProductUpdateDto.getDescription() != null){
            idProductFound.setDescription(fieldProductUpdateDto.getDescription());
        }
        if(fieldProductUpdateDto.getSku() != null){
            idProductFound.setSku(fieldProductUpdateDto.getSku());
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

    @Transactional(readOnly = true)
    @Override
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

}
