package dev.cnpe.inventoryappapi.mappers;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryCreateDTO;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponseDto;
import dev.cnpe.inventoryappapi.domain.dtos.CategorySummaryDTO;
import dev.cnpe.inventoryappapi.domain.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategorySummaryDTO toSummaryDTO(Category categoryEntity);

  CategoryResponseDto toResponseDTO(Category categoryEntity);

  @Mapping(target = "id",ignore = true)
  @Mapping(target = "url",ignore = true)
  @Mapping(target = "categoryItems",ignore = true)
  Category toEntity(CategoryCreateDTO categoryCreateDTO);

}
