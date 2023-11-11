package dev.cnpe.inventoryappapi.services.impl;

import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponseDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummaryDTO;
import dev.cnpe.inventoryappapi.domain.entities.Item;
import dev.cnpe.inventoryappapi.exceptions.ResourceWithIdNotFoundException;
import dev.cnpe.inventoryappapi.mappers.ItemMapper;
import dev.cnpe.inventoryappapi.repositories.ItemRepository;
import dev.cnpe.inventoryappapi.services.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceDefault implements ItemService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;

  @Override
  public Page<ItemSummaryDTO> findAllItems(Pageable pageable) {
    Page<Item> itemsPage = itemRepository.findAll(pageable);
    return itemsPage.map(itemMapper::toSummaryDTO);
  }

  @Override
  public ItemResponseDTO createItem(ItemCreateDTO itemCreateDTO) {
    Item savedItem = itemRepository.save(itemMapper.toEntity(itemCreateDTO));
    savedItem.setUrl("/api/items/" + savedItem.getId());
    return itemMapper.toResponseDTO(savedItem);
  }

  @Override
  public ItemResponseDTO findItemById(Long id) {
    Item item = itemRepository
            .findById(id)
            .orElseThrow(() -> new ResourceWithIdNotFoundException(id));

    return itemMapper.toResponseDTO(item);
  }
}
