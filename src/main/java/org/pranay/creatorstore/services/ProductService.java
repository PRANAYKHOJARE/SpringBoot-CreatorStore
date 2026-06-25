package org.pranay.creatorstore.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pranay.creatorstore.entities.Product;
import org.pranay.creatorstore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct( Product product) {
        return productRepository.save(product);
    }


    public Product updateProduct( Long id,  Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found with id " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStockQuantity(product.getStockQuantity());

        return productRepository.save(existingProduct);
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public Product getProductById( Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found with id " + id));
    }

    public void deleteProduct( Long id) {
        productRepository.deleteById(id);
    }
}

