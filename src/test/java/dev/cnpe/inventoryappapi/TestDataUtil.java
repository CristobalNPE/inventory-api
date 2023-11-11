package dev.cnpe.inventoryappapi;

import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;

import java.math.BigDecimal;

public final class TestDataUtil {

  private TestDataUtil() {
  }

  public static ItemCreateDTO generateTestItemCreateDTO() {
    return ItemCreateDTO.builder()
            .name("TEST ITEM 1")
            .description("TEST ITEM 1 DESCRIPTION")
            .initialStock(999)
            .price(new BigDecimal("99.9"))
            .build();
  }

}
