package dev.cnpe.inventoryappapi.domain.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ItemCreateDTO {

  private String name;

  private String description;

//  private Set<String> categories;

  private Integer initialStock;

  private BigDecimal price;

}
