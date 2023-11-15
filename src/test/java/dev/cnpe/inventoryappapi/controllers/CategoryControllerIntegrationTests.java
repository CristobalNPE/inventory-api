package dev.cnpe.inventoryappapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryRequest;
import dev.cnpe.inventoryappapi.domain.dtos.CategoryResponse;
import dev.cnpe.inventoryappapi.services.CategoryService;
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

import static dev.cnpe.inventoryappapi.TestDataUtil.generateTestCategoryRequest;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTests {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;
  private final CategoryService categoryService;

  @Autowired
  public CategoryControllerIntegrationTests(MockMvc mockMvc,
                                            CategoryService categoryService) {
    this.mockMvc = mockMvc;
    this.objectMapper = new ObjectMapper();
    this.categoryService = categoryService;
  }

  ///////////// --------  CREATE -------- /////////////
  @Test
  @DisplayName("""
          createCategory endpoint returns an HTTPStatus code 201 Created
           when successful.
          """)
  void createCategoryShouldReturn201() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    String createCategoryJSON = objectMapper.writeValueAsString(categoryRequest);

    mockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createCategoryJSON)
    ).andExpect(status().isCreated());

  }

  @Test
  @DisplayName("""
          createCategory endpoint should return the saved Category when
           successful
          """)
  void createCategoryReturnsCreatedCategory() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    String createCategoryJSON = objectMapper.writeValueAsString(categoryRequest);

    mockMvc.perform(post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createCategoryJSON))

            .andExpect(jsonPath("$.name").value(categoryRequest.getName()))
            .andExpect(jsonPath("$.description").value(categoryRequest.getDescription()));
  }

  @Test
  @DisplayName("""
          When creating a new Category, an empty JSON request should return an
           HTTPStatus code 400 BAD REQUEST
          """)
  void emptyJSONShouldReturn400() throws Exception {

    mockMvc.perform(
            post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
    ).andExpect(
            status().isBadRequest()
    );

  }

  @Test
  @DisplayName("""
          When creating a new Item, an not valid JSON request should return an
           HTTPStatus code 400 BAD REQUEST
          """)
  void notValidJSONShouldReturn400() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    categoryRequest.setName("");// Empty name should not be valid
    String createCategoryJSON = objectMapper.writeValueAsString(categoryRequest);


    mockMvc.perform(
            post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createCategoryJSON)
    ).andExpect(
            status().isBadRequest()
    );

  }

  ///////////// --------  GET ALL -------- /////////////
  @DisplayName("""
          getAllCategories endpoint should return HTTPStatus code 200 when
           successful
          """)
  @Test
  void getAllCategoriesShouldReturn200() throws Exception {
    mockMvc.perform(
            get("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)

    ).andExpect(
            status().isOk()
    );
  }

  @Test
  @DisplayName("""
          getAllCategories endpoint should return a Page with items when successful
          """)
  void getAllCategoriesShouldReturnPageOfCategories() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    categoryService.createCategory(categoryRequest);

    mockMvc.perform(get("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", hasSize(1)));
  }

  ///////////// --------  GET BY ID -------- /////////////

  @DisplayName("""
          getCategoryById endpoint should return HTTPStatus code 200 when successful
           and Category exists.
          """)
  @Test
  void getCategoryByIdShouldReturn200() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);

    mockMvc.perform(
            get("/api/categories/" + savedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)

    ).andExpect(
            status().isOk()
    );
  }

  @Test
  @DisplayName("""
          getCategoryById endpoint should return the return the correct Category
           when successful
          """)
  void getCategoryByIdShouldReturnCorrectCategory() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);


    mockMvc.perform(get("/api/categories/" + savedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value(categoryRequest.getName()))
            .andExpect(jsonPath("$.description").value(categoryRequest.getDescription()));

  }

  @Test
  @DisplayName("""
          getCategoryById endpoint should return HTTPStatus 404 NOT FOUND when
           requested with a non-existent ID
          """)
  void getCategoryByIdShouldReturn404ifNoExists() throws Exception {

    mockMvc.perform(
            get("/api/categories/" + "99")
                    .contentType(MediaType.APPLICATION_JSON)

    ).andExpect(
            status().isNotFound()
    );
  }

  ///////////// --------  UPDATE BY ID -------- /////////////

  @Test
  @DisplayName("""
          updateCategoryOnId endpoint should return HTTPStatus code 200 when
           successful.
          """)
  void updateCategoryShouldReturn200() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);

    CategoryRequest updateRequest = generateTestCategoryRequest();
    updateRequest.setName("Updated"); //We make a change
    String categoryUpdateJSON = objectMapper.writeValueAsString(updateRequest);

    mockMvc.perform(patch("/api/categories/" + savedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(categoryUpdateJSON))

            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("""
          updateCategoryOnId endpoint should return HTTPStatus code 404 NOT FOUND when
           ID provided does not exist.
          """)
  void updateCategoryShouldReturn404IfNoExist() throws Exception {
    CategoryRequest updateRequest = generateTestCategoryRequest();
    String categoryUpdateJSON = objectMapper.writeValueAsString(updateRequest);

    mockMvc.perform(patch("/api/categories/" + "999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(categoryUpdateJSON))

            .andExpect(status().isNotFound());
  }


  @Test
  @DisplayName("""
          updateCategoryOnId endpoint should update and return the updated
           Category
          """)
  void updateItemShouldReturnUpdatedItem() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);

    CategoryRequest updateRequest = generateTestCategoryRequest();
    updateRequest.setName("Updated"); //We make a change
    String categoryUpdateJSON = objectMapper.writeValueAsString(updateRequest);

    mockMvc.perform(patch("/api/categories/" + savedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(categoryUpdateJSON))

            .andExpect(jsonPath("$.name")
                    .value("Updated"))
            .andExpect(jsonPath("$.description")
                    .value(categoryRequest.getDescription()));


  }

 ///////////// --------  DELETE BY ID -------- /////////////
 @Test
 @DisplayName("""
          deleteCategory endpoint should return HTTPStatus code 204 NO CONTENT when
           ID provided exists.
          """)
 void deleteCategoryShouldReturn204IdExists() throws Exception {
   CategoryRequest categoryRequest = generateTestCategoryRequest();
   CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);

   mockMvc.perform(delete("/api/categories/" + savedCategory.getId())
                   .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNoContent());
 }

  @Test
  @DisplayName("""
          deleteCategory endpoint should return HTTPStatus code 404 NOT FOUND
           when ID provided does not exist.
          """)
  void deleteCategoryShouldReturn204NoIdExists() throws Exception {

    mockMvc.perform(delete("/api/categories/" + "990")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }


  @Test
  @DisplayName("""
          deleteCategory endpoint should delete the Category if it exists for
           provided id
          """)
  void deleteCategoryShouldDelete() throws Exception {
    CategoryRequest categoryRequest = generateTestCategoryRequest();
    CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);

    categoryService.deleteCategoryById(savedCategory.getId());

    mockMvc.perform(get("/api/categories/" + savedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());


  }

}
