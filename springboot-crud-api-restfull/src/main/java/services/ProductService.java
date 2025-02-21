package services;

import java.util.List;
import java.util.Optional;

import entities.Product;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    Optional<Product> delete(Product product);
}
