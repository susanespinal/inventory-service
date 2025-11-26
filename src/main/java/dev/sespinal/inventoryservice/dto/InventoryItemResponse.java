package dev.sespinal.inventoryservice.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponse {
  private Long id;
  private Long productId;
  private String productName;
  private Integer availableStock;
  private Integer reservedStock;
  private Integer totalStock;
  private Instant createdAt;
  private Instant updatedAt;

//  public InventoryItemResponse(Long id, Long productId, String productName, Integer availableStock, Integer reservedStock, Integer totalStock, Instant createdAt,
//      Instant updatedAt) {
//  }


  @Override
  public String toString() {
    return "InventoryItemResponse{" +
        "id=" + id +
        ", productId=" + productId +
        ", productName='" + productName + '\'' +
        ", availableStock=" + availableStock +
        ", reservedStock=" + reservedStock +
        ", totalStock=" + totalStock +
        '}';
  }
}
