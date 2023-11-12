package dev.cnpe.inventoryappapi.advices;

import dev.cnpe.inventoryappapi.domain.erros.ApiError;
import dev.cnpe.inventoryappapi.domain.erros.ApiValidationError;
import dev.cnpe.inventoryappapi.exceptions.ResourceWithIdNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {

    String error = "Malformed JSON request";
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex);
    return buildResponseEntity(apiError);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request) {

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage("Validation Error");
    apiError.setDebugMessage(ex.getMessage());
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<ApiValidationError> subErrors = fieldErrors.stream()
            .map(fieldError -> ApiValidationError.builder()
                    .rejectedValue(fieldError.getRejectedValue())
                    .object(fieldError.getObjectName())
                    .message(fieldError.getDefaultMessage())
                    .field(fieldError.getField())
                    .build())
            .toList();
    apiError.setSubErrors(subErrors);

    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ResourceWithIdNotFoundException.class)
  protected ResponseEntity<Object> handleResourceWithIdNotFound(
          ResourceWithIdNotFoundException ex) {

    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
    apiError.setMessage("Invalid ID");
    apiError.setDebugMessage(ex.getLocalizedMessage());
    return buildResponseEntity(apiError);
  }


  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }


}
