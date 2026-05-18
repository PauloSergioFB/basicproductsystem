package br.com.fiap.example.basicproductssystem.presentation.transferObjects;

import br.com.fiap.example.basicproductssystem.domainmodel.Category;
import lombok.Builder;

@Builder
public record CreateCategoryDTO(Long id, String name) {

    public static Category to(CreateCategoryDTO dto) {
        if (dto == null)
            return null;

        return Category.builder()
                .name(dto.name())
                .build();
    }

}
