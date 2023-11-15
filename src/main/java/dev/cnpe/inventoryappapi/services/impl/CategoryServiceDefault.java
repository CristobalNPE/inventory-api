package dev.cnpe.inventoryappapi.services.impl;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponse;
import dev.cnpe.inventoryappapi.domain.dtos.CategorySummary;
import dev.cnpe.inventoryappapi.domain.entities.Category;
import dev.cnpe.inventoryappapi.exceptions.ResourceWithIdNotFoundException;
import dev.cnpe.inventoryappapi.mappers.CategoryMapper;
import dev.cnpe.inventoryappapi.repositories.CategoryRepository;
import dev.cnpe.inventoryappapi.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceDefault implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;


  @Override
  public CategoryResponse createCategory(CategoryRequest categoryRequest) {
    Category savedCategory = categoryRepository
            .save(categoryMapper.toEntity(categoryRequest));

    savedCategory.setUrl("/api/categories/" + savedCategory.getId());

    return categoryMapper.toResponseDTO(savedCategory);
  }

  @Override
  public Page<CategoryResponse> findAllCategories(Pageable pageable) {
    Page<Category> categoriesPage = categoryRepository.findAll(pageable);
    return categoriesPage.map(categoryMapper::toResponseDTO);
  }

  @Override
  public CategoryResponse findCategoryById(Long id) {

    Category foundCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceWithIdNotFoundException(id));

    return categoryMapper.toResponseDTO(foundCategory);
  }

  @Override
  public CategoryResponse updateCategoryOnId(Long id, CategoryRequest categoryRequest) {

    return categoryRepository.findById(id)
            .map(existingItem -> {
              Optional.ofNullable((categoryRequest.getName()))
                      .ifPresent(existingItem::setName);
              Optional.ofNullable((categoryRequest.getDescription()))
                      .ifPresent(existingItem::setDescription);
              Category updatedEntity = categoryRepository.save(existingItem);
              return categoryMapper.toResponseDTO(updatedEntity);
            }).orElseThrow(() -> new ResourceWithIdNotFoundException(id));
  }

  @Override
  public void deleteCategoryById(Long id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceWithIdNotFoundException(id));
    category.removeItemsAssociation();
    categoryRepository.delete(category);
  }

  @Override
  public List<CategorySummary> findAllCategories() {
    return categoryRepository
            .findAll()
            .stream()
            .map(categoryMapper::toSummaryDTO)
            .toList();
  }


}
