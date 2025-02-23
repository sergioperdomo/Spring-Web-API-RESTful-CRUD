package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.controllers;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductCreateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.ProductUpdateDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Product;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/products")
public class ProductController {

    /***
     * @see ProductController#create(ProductCreateDto, BindingResult)   BindingResult captura los errores, tiene que estar al lado derecho del parametro que estamos pasando.
     */

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProductCreateDto productCreateDto, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productCreateDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto fieldUpdateProductDto, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }


        try {
            Product updatedProduct = productService.update(id, fieldUpdateProductDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Product> productOptional = productService.delete(id); // Elimina el producto que tenga ese ID.
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //    Método de validación
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
