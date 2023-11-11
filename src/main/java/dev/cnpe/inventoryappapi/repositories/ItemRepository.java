package dev.cnpe.inventoryappapi.repositories;

import dev.cnpe.inventoryappapi.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
