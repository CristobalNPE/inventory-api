package dev.cnpe.inventoryappapi.mappers;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponse;
import dev.cnpe.inventoryappapi.domain.dtos.CategorySummary;
import dev.cnpe.inventoryappapi.domain.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  CategorySummary toSummaryDTO(Category categoryEntity);

  @Mapping(target = "items", source = "." ,qualifiedByName = "itemsSize")
  CategoryResponse toResponseDTO(Category categoryEntity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "url", ignore = true)
  Category toEntity(CategoryRequest categoryRequest);

  @Named("itemsSize")
  default Integer getItemsSize(Category categoryEntity) {
    return categoryEntity.getCategoryItems() != null
            ? categoryEntity.getCategoryItems().size()
            : 0;
  }

}
