package dev.sespinal.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemRequest {
  @NotNull(message = "El ID del producto es requerido")
  private Long productId;

  @NotBlank(message = "El nombre del producto es requerido")
  private String productName;

  @NotNull(message = "El stock inicial es requerido")
  @Min(value = 0, message = "El stock inicial debe ser no negativo")
  private Integer initialStock;
//  // Constructor vacío
//  public InventoryItemRequest() {
//  }

//  // Constructor completo
//  public InventoryItemRequest(Long productId, String productName, Integer initialStock) {
//    this.productId = productId;
//    this.productName = productName;
//    this.initialStock = initialStock;
//  }

  @Override
  public String toString() {
    return "InventoryItemRequest{" +
        "productId=" + productId +
        ", productName='" + productName + '\'' +
        ", initialStock=" + initialStock +
        '}';
  }
}
