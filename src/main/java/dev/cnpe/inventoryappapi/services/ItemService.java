package dev.cnpe.inventoryappapi.services;

import dev.cnpe.inventoryappapi.domain.dtos.ItemRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponse;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
  Page<ItemSummary> findAllItems(Pageable pageable);

  ItemResponse createItem(ItemRequest itemRequest);

  ItemResponse findItemById(Long id);

  ItemResponse updateItemOnId(Long id, ItemRequest itemRequest);

  void deleteItemById(Long id);
}
