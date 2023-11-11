package dev.cnpe.inventoryappapi.exceptions;

public class ResourceWithIdNotFoundException extends RuntimeException {
  public ResourceWithIdNotFoundException(Long id) {
    super("Resource with ID [" + id + "] was not found.");
  }
}
