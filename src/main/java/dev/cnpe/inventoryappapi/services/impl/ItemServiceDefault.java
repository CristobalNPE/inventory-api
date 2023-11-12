package dev.cnpe.inventoryappapi.services.impl;

import dev.cnpe.inventoryappapi.domain.dtos.ItemRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponse;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummary;
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

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceDefault implements ItemService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;

  @Override
  public Page<ItemSummary> findAllItems(Pageable pageable) {
    Page<Item> itemsPage = itemRepository.findAll(pageable);
    return itemsPage.map(itemMapper::toSummaryDTO);
  }

  @Override
  public ItemResponse createItem(ItemRequest itemRequest) {
    Item savedItem = itemRepository.save(itemMapper.toEntity(itemRequest));
    savedItem.setUrl("/api/items/" + savedItem.getId());
    return itemMapper.toResponseDTO(savedItem);
  }

  @Override
  public ItemResponse findItemById(Long id) {
    Item item = itemRepository
            .findById(id)
            .orElseThrow(() -> new ResourceWithIdNotFoundException(id));

    return itemMapper.toResponseDTO(item);
  }

  @Override
  public ItemResponse updateItemOnId(Long id, ItemRequest itemRequest) {

    return itemRepository.findById(id)
            .map(existingItem -> {
              Optional.ofNullable(itemRequest.getName())
                      .ifPresent(existingItem::setName);
              Optional.ofNullable(itemRequest.getDescription())
                      .ifPresent(existingItem::setDescription);
              Optional.ofNullable(itemRequest.getPrice())
                      .ifPresent(existingItem::setPrice);
              Optional.ofNullable(itemRequest.getInitialStock())
                      .ifPresent(existingItem::setStock);
              Item updatedEntity = itemRepository.save(existingItem);
              return itemMapper.toResponseDTO(updatedEntity);
            }).orElseThrow(() -> new ResourceWithIdNotFoundException(id));

  }

  @Override
  public void deleteItemById(Long id) {
    itemRepository.deleteById(id);
  }

}
