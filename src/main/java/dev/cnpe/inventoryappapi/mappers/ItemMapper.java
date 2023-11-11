package dev.cnpe.inventoryappapi.mappers;

import dev.cnpe.inventoryappapi.domain.dtos.ItemCreateDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemResponseDTO;
import dev.cnpe.inventoryappapi.domain.dtos.ItemSummaryDTO;
import dev.cnpe.inventoryappapi.domain.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

  ItemSummaryDTO toSummaryDTO(Item itemEntity);

  ItemResponseDTO toResponseDTO(Item itemEntity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "url", ignore = true)
  @Mapping(target = "categories", ignore = true)
  @Mapping(target = "stock", source = "initialStock")
  Item toEntity(ItemCreateDTO itemCreateDTO);

}
