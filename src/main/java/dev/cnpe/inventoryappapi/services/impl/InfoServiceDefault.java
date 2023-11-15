package dev.cnpe.inventoryappapi.services.impl;

import dev.cnpe.inventoryappapi.domain.dtos.CategorySummary;
import dev.cnpe.inventoryappapi.domain.dtos.InfoResponse;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummary;
import dev.cnpe.inventoryappapi.domain.entities.Item;
import dev.cnpe.inventoryappapi.mappers.CategoryMapper;
import dev.cnpe.inventoryappapi.mappers.ItemMapper;
import dev.cnpe.inventoryappapi.repositories.CategoryRepository;
import dev.cnpe.inventoryappapi.repositories.ItemRepository;
import dev.cnpe.inventoryappapi.services.InfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InfoServiceDefault implements InfoService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public InfoResponse getInfo() {
    Set<ItemSummary> itemsLowStock = itemRepository
            .findItemsWithLowStock()
            .stream()
            .map(itemMapper::toSummaryDTO)
            .collect(Collectors.toSet());

    Item itemWithMostStock = itemRepository
            .findTop1ByOrderByStockDesc()
            .orElse(new Item());

    Set<CategorySummary> categoriesWithNoItems = categoryRepository
            .findCategoriesWithNoItems()
            .stream()
            .map(categoryMapper::toSummaryDTO)
            .collect(Collectors.toSet());

    return InfoResponse.builder()
            .numberOfItems(itemRepository.count())
            .itemsOnLowStock(itemsLowStock)
            .totalStock(itemRepository.sumOfStock())
            .itemWithMostStock(itemMapper.toSummaryDTO(itemWithMostStock))
            .totalInventoryValue(itemRepository.totalValue())
            .categoriesWithNoItems(categoriesWithNoItems)
            .build();
  }
}
