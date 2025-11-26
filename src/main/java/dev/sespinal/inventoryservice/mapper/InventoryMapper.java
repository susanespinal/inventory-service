package dev.sespinal.inventoryservice.mapper;

import dev.sespinal.inventoryservice.dto.InventoryItemRequest;
import dev.sespinal.inventoryservice.dto.InventoryItemResponse;
import dev.sespinal.inventoryservice.model.entity.InventoryItem;

public final class InventoryMapper {

  private InventoryMapper() {
    throw new AssertionError("Utility class, no debe instanciarse");
  }

  public static InventoryItemResponse toResponse(InventoryItem inventoryItem) {

    return new InventoryItemResponse(
        inventoryItem.getId(),
        inventoryItem.getProductId(),
        inventoryItem.getProductName(),
        inventoryItem.getAvailableStock(),
        inventoryItem.getReservedStock(),
        inventoryItem.getTotalStock(),
        inventoryItem.getCreatedAt(),
        inventoryItem.getUpdatedAt()
    );
  }

  public static InventoryItem toEntity(InventoryItemRequest request, InventoryItem entity) {

    entity.setProductId(request.getProductId());
    entity.setProductName(request.getProductName());
    entity.setAvailableStock(request.getInitialStock());

    return entity;
  }

}
