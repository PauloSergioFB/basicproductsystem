package br.com.fiap.example.basicproductssystem.presentation.transferObjects;

import br.com.fiap.example.basicproductssystem.domainmodel.Category;
import lombok.Builder;

@Builder
public record CategoryDTO(Long id, String name) {

    public static CategoryDTO from(Category category) {
        if (category == null)
            return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
