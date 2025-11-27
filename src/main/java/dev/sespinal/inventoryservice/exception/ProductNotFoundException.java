package dev.sespinal.inventoryservice.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(Long productId) {
    super("Producto inexistente: " + productId);
  }
}
