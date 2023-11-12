package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryResponse {
  private Long id;

  private String name;

  private String description;

  private String url;

  private Set<ItemSummary> categoryItems;
}
