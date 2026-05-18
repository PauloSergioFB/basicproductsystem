package br.com.fiap.example.basicproductssystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.example.basicproductssystem.domainmodel.Product;
import br.com.fiap.example.basicproductssystem.domainmodel.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService<Product, Long> {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productRepository.findAll());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product partialUpdate(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        Product productFromDatabase = productRepository.findById(id).orElse(null);

        if (product.getName() != null && !productFromDatabase.getName().equals(product.getName()))
            productFromDatabase.setName(product.getName());

        if (product.getPrice() != null && !productFromDatabase.getPrice().equals(product.getPrice()))
            productFromDatabase.setPrice(product.getPrice());

        if (product.getCategory() != null && !productFromDatabase.getCategory().equals(product.getCategory()))
            productFromDatabase.setCategory(product.getCategory());

        return create(productFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

}