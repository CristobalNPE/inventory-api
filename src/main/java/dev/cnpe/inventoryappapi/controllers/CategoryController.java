package dev.cnpe.inventoryappapi.controllers;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponse;
import dev.cnpe.inventoryappapi.domain.dtos.CategorySummary;
import dev.cnpe.inventoryappapi.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(
          @Valid @RequestBody CategoryRequest categoryRequest
  ) {
    CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);
    return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
  }

  @GetMapping
  public Page<CategoryResponse> getAllCategories(Pageable pageable) {
    return categoryService.findAllCategories(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> getCategoryById
          (@PathVariable(name = "id") Long id) {

    CategoryResponse response = categoryService.findCategoryById(id);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CategoryResponse> updateCategory
          (@PathVariable(name = "id") Long id,
           @Valid @RequestBody CategoryRequest categoryRequest) {

    CategoryResponse response = categoryService
            .updateCategoryOnId(id, categoryRequest);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
    categoryService.deleteCategoryById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
