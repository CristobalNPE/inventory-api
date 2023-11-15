package dev.cnpe.inventoryappapi;

import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemRequest;

import java.math.BigDecimal;

public final class TestDataUtil {

  private TestDataUtil() {
  }

  public static ItemRequest generateTestItemRequest() {
    return ItemRequest.builder()
            .name("TEST ITEM 1")
            .description("TEST ITEM 1 DESCRIPTION")
            .initialStock(99)
            .price(new BigDecimal("99.9"))
            .build();
  }

  public static ItemRequest generateTestItemRequest2() {
    return ItemRequest.builder()
            .name("TEST ITEM 2")
            .description("TEST ITEM 2 DESCRIPTION")
            .initialStock(999)
            .price(new BigDecimal("999.9"))
            .build();
  }

  public static CategoryRequest generateTestCategoryRequest() {
    return CategoryRequest.builder()
            .name("TEST CATEGORY 1")
            .description("DESCRIPTION TEST CATEGORY 1")
            .build();
  }

  public static CategoryRequest generateTestCategoryRequest2() {
    return CategoryRequest.builder()
            .name("TEST CATEGORY 2")
            .description("DESCRIPTION TEST CATEGORY 2")
            .build();
  }

}
