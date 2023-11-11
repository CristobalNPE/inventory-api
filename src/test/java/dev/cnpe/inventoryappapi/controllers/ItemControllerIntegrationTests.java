package dev.cnpe.inventoryappapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static dev.cnpe.inventoryappapi.TestDataUtil.generateTestItemCreateDTO;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ItemControllerIntegrationTests {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;
  private final ItemService itemService;

  @Autowired
  public ItemControllerIntegrationTests(MockMvc mockMvc, ItemService itemService) {
    this.mockMvc = mockMvc;
    this.itemService = itemService;
    this.objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("""
          createItem endpoint returns an HTTPStatus code 201 Created
           when successful.
          """)
  void createItemShouldReturn201() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    String createItemJSON = objectMapper.writeValueAsString(itemCreateDTO);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createItemJSON)
    ).andExpect(
            MockMvcResultMatchers.status().isCreated()
    );

  }

  @Test
  @DisplayName("""
          createItem endpoint should return the saved Item when successful
          """)
  void createItemReturnsCreatedItem() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    String createItemJSON = objectMapper.writeValueAsString(itemCreateDTO);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createItemJSON)
    ).andExpect(
            jsonPath("$.name").value(itemCreateDTO.getName())
    ).andExpect(
            jsonPath("$.description").value(itemCreateDTO.getDescription())
    ).andExpect(
            jsonPath("$.stock").value(itemCreateDTO.getInitialStock())
    ).andExpect(
            jsonPath("$.price").value(itemCreateDTO.getPrice())
    );
  }

  // Test for invalid Inputs, like Empty JSON
  // Test for JSON that doesn't meet the validations after applying validation.

  @DisplayName("""
          getAllItems endpoint should return HTTPStatus code 200 when successful
          """)
  @Test
  void getAllItemsShouldReturn200() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)

    ).andExpect(
            MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  @DisplayName("""
          getAllItems endpoint should return a List with items when successful
          """)
  void getAllItemsShouldReturnListOfItems() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    itemService.createItem(itemCreateDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/items")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", hasSize(1)));
  }
}
