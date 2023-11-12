package dev.cnpe.inventoryappapi.domain.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ItemRequest {

  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
  private String name;

  @NotBlank(message = "Description cannot be empty")
  private String description;

  // Categories should be strings.
  // Once received in the controller and handed to the
  // service, we should check that the categories exist.
  // Then assign the item to those categories.

  private Set<String> categories;

  @NotNull(message = "Must indicate an initial stock number")
  @Min(value = 0, message = "Initial stock must be at least 0")
  private Integer initialStock;

  @NotNull(message = "Must indicate a price")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  private BigDecimal price;

}
