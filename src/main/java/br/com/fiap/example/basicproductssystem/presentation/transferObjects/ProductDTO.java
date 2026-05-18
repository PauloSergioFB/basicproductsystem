package br.com.fiap.example.basicproductssystem.presentation.transferObjects;

import java.math.BigDecimal;

import br.com.fiap.example.basicproductssystem.domainmodel.Product;
import lombok.Builder;

@Builder
public record ProductDTO(Long id, String name, BigDecimal price, CategoryDTO category) {

    public static ProductDTO from(Product product) {
        if (product == null)
            return null;

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(CategoryDTO.from(product.getCategory()))
                .build();
    }

}
