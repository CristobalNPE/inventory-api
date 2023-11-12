package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ItemSummary {
  private Long id;
  private String name;
  private Integer stock;
  private BigDecimal price;
  private String url;
}
