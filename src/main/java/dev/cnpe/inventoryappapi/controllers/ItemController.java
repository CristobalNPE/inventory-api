package dev.cnpe.inventoryappapi.controllers;

import dev.cnpe.inventoryappapi.domain.dtos.ItemRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponse;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummary;
import dev.cnpe.inventoryappapi.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @PostMapping
  public ResponseEntity<ItemResponse> createItem(
          @Valid @RequestBody ItemRequest itemRequest) {

    ItemResponse createdItemDto = itemService.createItem(itemRequest);
    return new ResponseEntity<>(createdItemDto, HttpStatus.CREATED);

  }

  @GetMapping
  public Page<ItemSummary> getAllItems(Pageable pageable) {
    return itemService.findAllItems(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemResponse> getItemById(
          @PathVariable(name = "id") Long id) {

    ItemResponse foundItem = itemService.findItemById(id);
    return new ResponseEntity<>(foundItem, HttpStatus.OK);

  }

  @PatchMapping("/{id}")
  public ResponseEntity<ItemResponse> updateItem(
          @PathVariable(name = "id") Long id,
          @Valid @RequestBody ItemRequest itemRequest) {
    ItemResponse updated = itemService.updateItemOnId(id, itemRequest);
    return new ResponseEntity<>(updated, HttpStatus.OK);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable(name = "id") Long id) {
    itemService.deleteItemById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }



}
