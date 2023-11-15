package dev.cnpe.inventoryappapi.repositories;

import dev.cnpe.inventoryappapi.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(String name);

  boolean existsByName(String name);

  @Query("SELECT c FROM Category c WHERE c.categoryItems IS EMPTY")
  Set<Category> findCategoriesWithNoItems();

}
