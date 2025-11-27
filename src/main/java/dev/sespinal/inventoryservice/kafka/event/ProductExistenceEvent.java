package dev.sespinal.inventoryservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductExistenceEvent {
  private Long orderId;
  private Long productId;
  private Boolean exists;
  private String message; // "Product exists" o "Product does not exist"
}