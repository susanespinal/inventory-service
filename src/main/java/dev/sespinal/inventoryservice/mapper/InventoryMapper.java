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

  @Mapping(target = "availableStock", source = "initialStock") // inicializamos con el stock del request
  @Mapping(target = "reservedStock", constant = "0")           // siempre empieza en 0
  @Mapping(target = "totalStock", source = "initialStock")    // totalStock = initialStock al crear
  InventoryItem toEntity(InventoryItemRequest request);

  // Mapear InventoryItem -> InventoryItemResponse
  InventoryItemResponse toResponse(InventoryItem item);
}