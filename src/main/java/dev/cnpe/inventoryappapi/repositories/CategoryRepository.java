package dev.cnpe.inventoryappapi.repositories;

import dev.cnpe.inventoryappapi.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
