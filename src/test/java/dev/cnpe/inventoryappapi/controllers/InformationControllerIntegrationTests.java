package dev.cnpe.inventoryappapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponse;
import dev.cnpe.inventoryappapi.services.CategoryService;
import dev.cnpe.inventoryappapi.services.InfoService;
import dev.cnpe.inventoryappapi.services.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static dev.cnpe.inventoryappapi.TestDataUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class InformationControllerIntegrationTests {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;
  private final InfoService infoService;
  private final CategoryService categoryService;
  private final ItemService itemService;

  @Autowired
  public InformationControllerIntegrationTests(MockMvc mockMvc,
                                               InfoService infoService,
                                               CategoryService categoryService,
                                               ItemService itemService) {
    this.mockMvc = mockMvc;
    this.objectMapper = new ObjectMapper();
    this.infoService = infoService;
    this.categoryService = categoryService;
    this.itemService = itemService;
  }


  @DisplayName("""
          getInfo should return HTTPStatus conde 200 when successful.
          """)
  @Test
  void getInfoShouldReturn200() throws Exception {
    mockMvc.perform(get("/api/info")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @DisplayName("""
          getInfo endpoint should return the correct info when called.
          """)
  @Test
  void getInfoShouldReturnCorrectInfo() throws Exception {
    ItemRequest itemRequest = generateTestItemRequest();
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    ItemRequest itemRequest2 = generateTestItemRequest2();
    CategoryRequest categoryRequest2 = generateTestCategoryRequest2();

    itemService.createItem(itemRequest);
    ItemResponse itemResponse = itemService.createItem(itemRequest2);
    categoryService.createCategory(categoryRequest);
    categoryService.createCategory(categoryRequest2);

    BigDecimal totalValue1 = itemRequest.getPrice().multiply(BigDecimal.valueOf(itemRequest.getInitialStock()));
    BigDecimal totalValue2 = itemRequest2.getPrice().multiply(BigDecimal.valueOf(itemRequest2.getInitialStock()));
    var expectedInventoryValue = totalValue1.add(totalValue2);

    mockMvc.perform(get("/api/info").contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.numberOfItems")
                    .value(2))
            .andExpect(jsonPath("$.totalStock")
                    .value(itemRequest
                            .getInitialStock() + itemRequest2.getInitialStock()))
            .andExpect(jsonPath("$.totalInventoryValue").value(expectedInventoryValue))
            .andExpect(jsonPath("$.itemWithMostStock.id", is(itemResponse.getId().intValue())))
            .andExpect(jsonPath("$.itemWithMostStock.name", is(itemResponse.getName())))
            .andExpect(jsonPath("$.itemWithMostStock.stock", is(itemResponse.getStock())))
            .andExpect(jsonPath("$.itemWithMostStock.price", is(itemResponse.getPrice().doubleValue())))
            .andExpect(jsonPath("$.itemsOnLowStock", hasSize(0)))
            .andExpect(jsonPath("$.categoriesWithNoItems", hasSize(2)));


  }
}
