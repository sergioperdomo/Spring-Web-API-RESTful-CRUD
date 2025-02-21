package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entities.Product;
import repositories.ProductRepository;

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
    public Optional<Product> delete(Product product) {
        Optional<Product> productOptionalDb = productRepository.findById(product.getId());
        productOptionalDb.ifPresent(productDb -> {
            productRepository.delete(productDb);
        });

        return productOptionalDb;
    }

}
