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
import br.com.fiap.example.basicproductssystem.presentation.transferObjects.CategoryDTO;
import br.com.fiap.example.basicproductssystem.presentation.transferObjects.CreateCategoryDTO;
import br.com.fiap.example.basicproductssystem.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryApiController {

    private final CategoryService<Category, Long> categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll()
                .stream()
                .map(CategoryDTO::from)
                .toList());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(
            @RequestBody CreateCategoryDTO createCategoryDTO) {
        Category newCategory = categoryService.create(CreateCategoryDTO.to(createCategoryDTO));
        return new ResponseEntity<>(CategoryDTO.from(newCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable Long id,
            @RequestBody CreateCategoryDTO createCategoryDTO) {

        Category category = CreateCategoryDTO.to(createCategoryDTO);
        category.setId(id);

        Category updatedCategory = categoryService.partialUpdate(id, category);
        return ResponseEntity.ok(CategoryDTO.from(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        categoryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não encontrada."));

        categoryService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
