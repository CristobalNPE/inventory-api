package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ItemResponseDTO {
  private Long id;

  private String name;

  private String description;

  private Integer stock;

  private Set<CategorySummaryDTO> categories;

  private BigDecimal price;

  private String url;
}
