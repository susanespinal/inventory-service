package dev.sespinal.inventoryservice.mapper;

import dev.sespinal.inventoryservice.dto.InventoryItemRequest;
import dev.sespinal.inventoryservice.dto.InventoryItemResponse;
import dev.sespinal.inventoryservice.model.entity.InventoryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
//import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

  @Mapping(target = "totalStock", expression = "java(item.getAvailableStock() + item.getReservedStock())")
  InventoryItemResponse toResponse(InventoryItem item);

  InventoryItem toEntity(InventoryItemRequest request);
}