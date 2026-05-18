package br.com.fiap.example.basicproductssystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.example.basicproductssystem.domainmodel.Category;
import br.com.fiap.example.basicproductssystem.domainmodel.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService<Category, Long> {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categoryRepository.findAll());
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category partialUpdate(Long id, Category category) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found");
        }

        Category categoryFromDatabase = categoryRepository.findById(id).orElse(null);

        if (category.getName() != null && !categoryFromDatabase.getName().equals(category.getName()))
            categoryFromDatabase.setName(category.getName());

        return create(categoryFromDatabase);
    }

    @Override
    public void removeById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

}