package br.com.fiap.example.basicproductssystem.domainmodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.example.basicproductssystem.domainmodel.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
