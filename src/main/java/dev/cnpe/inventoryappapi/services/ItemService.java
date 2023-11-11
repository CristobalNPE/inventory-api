package dev.cnpe.inventoryappapi.services;

import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponseDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
  Page<ItemSummaryDTO> findAllItems(Pageable pageable);

  ItemResponseDTO createItem(ItemCreateDTO itemCreateDTO);

  ItemResponseDTO findItemById(Long id);
}
