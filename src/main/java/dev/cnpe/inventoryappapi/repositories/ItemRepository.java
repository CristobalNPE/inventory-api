package dev.cnpe.inventoryappapi.repositories;

import dev.cnpe.inventoryappapi.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long> {


  @Query("SELECT i FROM Item i WHERE i.stock < 5")
  Set<Item> findItemsWithLowStock();

  @Query("SELECT SUM(i.stock) FROM Item i")
  Long sumOfStock();

  @Query("SELECT SUM(i.price * i.stock) FROM Item i")
  BigDecimal totalValue();


  Optional<Item> findTop1ByOrderByStockDesc();

}
