package dev.cnpe.inventoryappapi.services;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponse;
import dev.cnpe.inventoryappapi.domain.dtos.CategorySummary;
import dev.cnpe.inventoryappapi.domain.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
  CategoryResponse createCategory(CategoryRequest categoryRequest);

  Page<CategoryResponse> findAllCategories(Pageable pageable);

  CategoryResponse findCategoryById(Long id);

  CategoryResponse updateCategoryOnId(Long id, CategoryRequest categoryRequest);

  void deleteCategoryById(Long id);


}
