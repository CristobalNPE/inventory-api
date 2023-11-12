package dev.cnpe.inventoryappapi.mappers;

import dev.cnpe.inventoryappapi.domain.dtos.ItemRequest;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponse;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummary;
import dev.cnpe.inventoryappapi.domain.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

  ItemSummary toSummaryDTO(Item itemEntity);

  ItemResponse toResponseDTO(Item itemEntity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "url", ignore = true)
  @Mapping(target = "categories", ignore = true) //Here we handle selected/sent categories
  @Mapping(target = "stock", source = "initialStock")
  Item toEntity(ItemRequest itemRequest);

}
