package br.com.fiap.example.basicproductssystem.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.example.basicproductssystem.domainmodel.Category;
import br.com.fiap.example.basicproductssystem.domainmodel.Product;
import br.com.fiap.example.basicproductssystem.presentation.transferObjects.CreateProductDTO;
import br.com.fiap.example.basicproductssystem.presentation.transferObjects.ProductDTO;
import br.com.fiap.example.basicproductssystem.service.CategoryService;
import br.com.fiap.example.basicproductssystem.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductApiController {

    private final ProductService<Product, Long> productService;
    private final CategoryService<Category, Long> categoryService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll()
                .stream()
                .map(ProductDTO::from)
                .toList());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(
            @RequestBody CreateProductDTO createProductDTO) {
        Category category = categoryService.findById(createProductDTO.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não encontrada."));

        Product newProduct = productService.create(CreateProductDTO.to(createProductDTO, category));
        return new ResponseEntity<>(ProductDTO.from(newProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable Long id,
            @RequestBody CreateProductDTO createProductDTO) {
        Category category = categoryService.findById(createProductDTO.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não encontrada."));

        Product product = CreateProductDTO.to(createProductDTO, category);
        product.setId(id);

        Product updatedProduct = productService.partialUpdate(id, product);
        return ResponseEntity.ok(ProductDTO.from(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto não encontrado."));

        productService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
