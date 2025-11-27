# inventory-service
## Arquitectura
```
inventory/
 ├── controller/
 |    └── InventoryController.java
 ├── dto/
 │    ├── ErrorResponse.java
 │    ├── InventoryItemRequest.java
 │    └── InventoryItemResponse.java
 ├── exceptions/
 │    ├── GlobalExceptionHandler.java
 │    ├── ProductNotFoundException.java
 │    └── ResourceNotFoundException.java
 ├── kafka/
 |    └── consumer/
 │         ├── ProductServiceClient.java
 |         └── OrderEventConsumer.java
 |    └── event/
 |         ├── ProductExistenceEvent.java
 │         ├── InventoryUpdateEvent.java
 │         ├── OrderCancelledEvent.java
 │         ├── OrderConfirmedEvent.java
 │         └── OrderPlacedEvent.java
 |    └── producer/
 |         └── InventoryEventProducer
 ├── mapper/
 │    └── InventoryMapper.java
 ├── model/
 │    └── Entity /
 │         └── InventoryItem.java
 ├── repository/
 │    └── InventoryRepository.java
 ├── service/
 │    └── InventoryService.java
 └── InventoryServiceApplication.java
```
