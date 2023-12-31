package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorySummary {

  private Long id;
  private String name;
  private String url;

}
