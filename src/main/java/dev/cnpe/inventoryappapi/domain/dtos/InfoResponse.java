package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class InfoResponse {

  Long numberOfItems;
  Long totalStock;
  BigDecimal totalInventoryValue;
  ItemSummary itemWithMostStock;
  Set<ItemSummary> itemsOnLowStock;
  Set<CategorySummary> categoriesWithNoItems;

}
