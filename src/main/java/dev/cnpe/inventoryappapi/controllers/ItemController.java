package dev.cnpe.inventoryappapi.controllers;

import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponseDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummaryDTO;
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
  public ResponseEntity<ItemResponseDTO> createItem(
          @Valid @RequestBody ItemCreateDTO itemCreateDTO) {

    ItemResponseDTO createdItemDto = itemService.createItem(itemCreateDTO);
    return new ResponseEntity<>(createdItemDto, HttpStatus.CREATED);

  }

  @GetMapping
  public Page<ItemSummaryDTO> getAllItems(Pageable pageable) {
    return itemService.findAllItems(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> getItemById(
          @PathVariable(name = "id") Long id) {

    ItemResponseDTO foundItem = itemService.findItemById(id);
    return new ResponseEntity<>(foundItem, HttpStatus.OK);

  }

  @PatchMapping("/{id}")
  public ResponseEntity<ItemResponseDTO> updateItem(
          @PathVariable(name = "id") Long id,
          @Valid @RequestBody ItemCreateDTO itemCreateDTO) {
    ItemResponseDTO updated = itemService.updateItemOnId(id, itemCreateDTO);
    return new ResponseEntity<>(updated, HttpStatus.OK);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable(name = "id") Long id) {
    itemService.deleteItemById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
