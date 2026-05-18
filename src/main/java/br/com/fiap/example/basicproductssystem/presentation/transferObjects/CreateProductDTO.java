package br.com.fiap.example.basicproductssystem.presentation.transferObjects;

import java.math.BigDecimal;

import br.com.fiap.example.basicproductssystem.domainmodel.Category;
import br.com.fiap.example.basicproductssystem.domainmodel.Product;
import lombok.Builder;

@Builder
public record CreateProductDTO(String name, BigDecimal price, Long categoryId) {

    public static Product to(CreateProductDTO dto, Category category) {
        if (dto == null)
            return null;

        return Product.builder()
                .name(dto.name())
                .price(dto.price())
                .category(category)
                .build();
    }
}
