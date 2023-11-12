package dev.cnpe.inventoryappapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {

  @NotBlank(message = "Must provide a name for the category")
  @Size(min = 3, message = "Name should at have at least 3 characters")
  private String name;

  @NotBlank(message = "Must provide a description")
  private String description;

}
