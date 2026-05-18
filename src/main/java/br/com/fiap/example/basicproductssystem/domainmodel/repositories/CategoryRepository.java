package br.com.fiap.example.basicproductssystem.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.example.basicproductssystem.domainmodel.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
