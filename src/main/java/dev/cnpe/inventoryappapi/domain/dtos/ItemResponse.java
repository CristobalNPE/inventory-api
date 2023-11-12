package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ItemResponse {
  private Long id;

  private String name;

  private String description;

  private Integer stock;

  private Set<CategorySummary> categories;

  private BigDecimal price;

  private String url;
}
