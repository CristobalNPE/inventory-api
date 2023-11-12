package dev.cnpe.inventoryappapi.mappers;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponse;
import dev.cnpe.inventoryappapi.domain.dtos.CategorySummary;
import dev.cnpe.inventoryappapi.domain.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategorySummary toSummaryDTO(Category categoryEntity);

  CategoryResponse toResponseDTO(Category categoryEntity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "url", ignore = true)
  @Mapping(target = "categoryItems", expression = "java(new LinkedHashSet<>())")
  Category toEntity(CategoryRequest categoryRequest);

}
