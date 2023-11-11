package dev.cnpe.inventoryappapi.domain.dtos;

import dev.cnpe.inventoryappapi.domain.entities.Item;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryResponseDto {
  private Long id;

  private String name;

  private String description;

  private String url;

  private Set<ItemSummaryDTO> categoryItems;
}
