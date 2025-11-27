package dev.sespinal.inventoryservice.kafka.producer;

import dev.sespinal.inventoryservice.kafka.event.InventoryUpdatedEvent;
import dev.sespinal.inventoryservice.kafka.event.OrderCancelledEvent;
import dev.sespinal.inventoryservice.kafka.event.OrderConfirmedEvent;
import dev.sespinal.inventoryservice.kafka.event.OrderPlacedEvent;
import dev.sespinal.inventoryservice.kafka.event.ProductExistenceEvent;
import dev.sespinal.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Producer de eventos de inventario. Publica OrderConfirmedEvent y OrderCancelledEvent a Kafka.
 */
@Component
public class InventoryEventProducer {

  private static final Logger log = LoggerFactory.getLogger(InventoryEventProducer.class);

  private final InventoryRepository inventoryRepository;

  private static final String TOPIC_ORDERS_CONFIRMED = "ecommerce.orders.confirmed";
  private static final String TOPIC_ORDERS_CANCELLED = "ecommerce.orders.cancelled";
  private static final String TOPIC_INVENTORY_UPDATE = "ecommerce.inventory.updated";
  private static final String TOPIC_PRODUCT_VERIFICATION = "ecommerce.product.validated";

  private final KafkaTemplate<String, OrderConfirmedEvent> confirmedKafkaTemplate;
  private final KafkaTemplate<String, OrderCancelledEvent> cancelledKafkaTemplate;
  private final KafkaTemplate<String, InventoryUpdatedEvent> inventoryUpdatedKafkaTemplate;
  private final KafkaTemplate<String, ProductExistenceEvent> productExistenceKafkaTemplate;

  // Constructor injection
  public InventoryEventProducer(
      InventoryRepository inventoryRepository, KafkaTemplate<String, OrderConfirmedEvent> confirmedKafkaTemplate,
      KafkaTemplate<String, OrderCancelledEvent> cancelledKafkaTemplate,
      KafkaTemplate<String, InventoryUpdatedEvent> inventoryUpdatedKafkaTemplate,
      KafkaTemplate<String, ProductExistenceEvent> productExistenceKafkaTemplate) {
    this.inventoryRepository = inventoryRepository;
    this.confirmedKafkaTemplate = confirmedKafkaTemplate;
    this.cancelledKafkaTemplate = cancelledKafkaTemplate;
    this.inventoryUpdatedKafkaTemplate = inventoryUpdatedKafkaTemplate;
    this.productExistenceKafkaTemplate = productExistenceKafkaTemplate;
  }

  /**
   * Publica OrderConfirmedEvent cuando el stock fue reservado exitosamente
   */
  public void publishOrderConfirmed(OrderConfirmedEvent event) {
    log.info("Publishing OrderConfirmedEvent: orderId={}, productId={}, quantity={}",
        event.getOrderId(), event.getProductId(), event.getQuantity());

    String key = event.getOrderId().toString();

    confirmedKafkaTemplate.send(TOPIC_ORDERS_CONFIRMED, key, event)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            log.error("Failed to publish OrderConfirmedEvent: orderId={}, error={}",
                event.getOrderId(), ex.getMessage(), ex);
          } else {
            log.info(
                "OrderConfirmedEvent published successfully: orderId={}, partition={}, offset={}",
                event.getOrderId(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
          }
        });
  }

  /**
   * Publica OrderCancelledEvent cuando no hay stock suficiente
   */
  public void publishOrderCancelled(OrderCancelledEvent event) {
    log.info("Publishing OrderCancelledEvent: orderId={}, productId={}, reason={}",
        event.getOrderId(), event.getProductId(), event.getReason());

    String key = event.getOrderId().toString();

    cancelledKafkaTemplate.send(TOPIC_ORDERS_CANCELLED, key, event)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            log.error("Failed to publish OrderCancelledEvent: orderId={}, error={}",
                event.getOrderId(), ex.getMessage(), ex);
          } else {
            log.info(
                "OrderCancelledEvent published successfully: orderId={}, partition={}, offset={}",
                event.getOrderId(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().offset());
          }
        });
  }

  public void publishInventoryUpdated(InventoryUpdatedEvent event) {
    log.info("Publishing InventoryUpdatedEvent: productId={},availableStock={}",
        event.getProductId(), event.getAvailableStock());
    inventoryUpdatedKafkaTemplate.send(TOPIC_INVENTORY_UPDATE,
        event.getProductId().toString(),
        event);
  }

  public void verifyProduct(OrderPlacedEvent event) {
    boolean exists = inventoryRepository.existsByProductId(event.getProductId());

    ProductExistenceEvent response = new ProductExistenceEvent(
        event.getOrderId(),
        event.getProductId(),
        exists,
        exists ? "Product exists" : "Product does not exist"
    );

    // Publicar evento a Kafka
    productExistenceKafkaTemplate.send(TOPIC_PRODUCT_VERIFICATION, response);
    if (!exists) {
      log.warn("Product with ID {} does not exist. Order {} cannot proceed.",
          event.getProductId(), event.getOrderId());
    }
  }
}