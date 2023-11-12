package dev.cnpe.inventoryappapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponseDTO;
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

  @Test
  @DisplayName("""
          When creating a new Item, an empty JSON request should return an
           HTTPStatus code 400 BAD REQUEST
          """)
  void emptyJSONShouldReturn400() throws Exception {

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
    ).andExpect(
            MockMvcResultMatchers.status().isBadRequest()
    );

  }

  @Test
  @DisplayName("""
          When creating a new Item, an not valid JSON request should return an
           HTTPStatus code 400 BAD REQUEST
          """)
  void notValidJSONShouldReturn400() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    itemCreateDTO.setName(""); // Empty name should not be validated
    String createItemJSON = objectMapper.writeValueAsString(itemCreateDTO);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createItemJSON)
    ).andExpect(
            MockMvcResultMatchers.status().isBadRequest()
    );

  }

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

  @DisplayName("""
          getItemById endpoint should return HTTPStatus code 200 when successful
           and item exists.
          """)
  @Test
  void getItemByIdShouldReturn200() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    ItemResponseDTO savedItem = itemService.createItem(itemCreateDTO);

    mockMvc.perform(
            MockMvcRequestBuilders.get("/api/items/" + savedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)

    ).andExpect(
            MockMvcResultMatchers.status().isOk()
    );
  }

  @Test
  @DisplayName("""
          getItemById endpoint should return the return the correct item
           when successful
          """)
  void getItemByIdShouldReturnCorrectItem() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    ItemResponseDTO savedItem = itemService.createItem(itemCreateDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/items/" + savedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value(itemCreateDTO.getName()))
            .andExpect(jsonPath("$.description").value(itemCreateDTO.getDescription()))
            .andExpect(jsonPath("$.stock").value(itemCreateDTO.getInitialStock()))
            .andExpect(jsonPath("$.price").value(itemCreateDTO.getPrice()));

  }

  @Test
  @DisplayName("""
          getItemById endpoint should return HTTPStatus 404 NOT FOUND when
           requested with a non-existent ID
          """)
  void getItemByIdShouldReturn404ifNoExists() throws Exception {

    mockMvc.perform(
            MockMvcRequestBuilders.get("/api/items/" + "99")
                    .contentType(MediaType.APPLICATION_JSON)

    ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
    );
  }

  @Test
  @DisplayName("""
          updateItemOnId endpoint should return HTTPStatus code 200 when
           successful.
          """)
  void updateItemShouldReturn200() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    ItemResponseDTO savedItem = itemService.createItem(itemCreateDTO);

    ItemCreateDTO updateRequest = generateTestItemCreateDTO();
    updateRequest.setName("Updated"); //We make a change
    String itemUpdateJSON = objectMapper.writeValueAsString(updateRequest);

    mockMvc.perform(MockMvcRequestBuilders.patch("/api/items/" + savedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(itemUpdateJSON))

            .andExpect(MockMvcResultMatchers.status().isOk());


  }


  @Test
  @DisplayName("""
          updateItemOnId endpoint should return HTTPStatus code 404 NOT FOUND when
           ID provided does not exist.
          """)
  void updateItemShouldReturn404IfNoExist() throws Exception {
    ItemCreateDTO updateRequest = generateTestItemCreateDTO();
    String itemUpdateJSON = objectMapper.writeValueAsString(updateRequest);

    mockMvc.perform(MockMvcRequestBuilders.patch("/api/items/" + "999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(itemUpdateJSON))

            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }


  @Test
  @DisplayName("""
          updateItemOnId endpoint should update and return the updated item
          """)
  void updateItemShouldReturnUpdatedItem() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    ItemResponseDTO savedItem = itemService.createItem(itemCreateDTO);

    ItemCreateDTO updateRequest = generateTestItemCreateDTO();
    updateRequest.setName("Updated"); //We make a change
    String itemUpdateJSON = objectMapper.writeValueAsString(updateRequest);

    mockMvc.perform(MockMvcRequestBuilders.patch("/api/items/" + savedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(itemUpdateJSON))

            .andExpect(jsonPath("$.name").value("Updated"))
            .andExpect(jsonPath("$.description").value(itemCreateDTO.getDescription()))
            .andExpect(jsonPath("$.stock").value(itemCreateDTO.getInitialStock()))
            .andExpect(jsonPath("$.price").value(itemCreateDTO.getPrice()));


  }

  @Test
  @DisplayName("""
          deleteItem endpoint should return HTTPStatus code 204 NO CONTENT when
           ID provided exists.
          """)
  void deleteItemShouldReturn204IdExists() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    ItemResponseDTO savedItem = itemService.createItem(itemCreateDTO);

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/items/" + savedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("""
          deleteItem endpoint should return HTTPStatus code 204 NO CONTENT when
           ID provided does not exist.
          """)
  void deleteItemShouldReturn204NoIdExists() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/items/" + "990")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
  }


  @Test
  @DisplayName("""
          deleteItem endpoint should delete the item if it exists for provided id
          """)
  void deleteItemShouldDelete() throws Exception {
    ItemCreateDTO itemCreateDTO = generateTestItemCreateDTO();
    ItemResponseDTO savedItem = itemService.createItem(itemCreateDTO);

    itemService.deleteItemById(savedItem.getId());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/items/" + savedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());


  }

}
