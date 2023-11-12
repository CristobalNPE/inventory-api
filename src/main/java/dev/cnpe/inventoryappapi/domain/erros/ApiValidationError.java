package dev.cnpe.inventoryappapi.domain.erros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
public class ApiValidationError extends ApiSubError {

  private String object;
  private String field;
  private Object rejectedValue;
  private String message;

}
