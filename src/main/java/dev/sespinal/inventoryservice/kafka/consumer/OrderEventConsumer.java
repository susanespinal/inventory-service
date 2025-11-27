package dev.sespinal.inventoryservice.kafka.consumer;

import dev.sespinal.inventoryservice.kafka.event.OrderPlacedEvent;
import dev.sespinal.inventoryservice.kafka.producer.InventoryEventProducer;
import dev.sespinal.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer de eventos OrderPlacedEvent. Escucha el topic ecommerce.orders.placed y procesa las
 * órdenes validando y reservando stock en el inventario.
 */
@Component
public class OrderEventConsumer {

  private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

  private final InventoryService inventoryService;
  private final InventoryEventProducer verificationService;

  // Constructor injection
  public OrderEventConsumer(InventoryService inventoryService,
      InventoryEventProducer verificationService) {
    this.inventoryService = inventoryService;
    this.verificationService = verificationService;
  }

  /**
   * Consume eventos OrderPlacedEvent del topic ecommerce.orders.placed
   *
   * @KafkaListener - Marca el método como listener de Kafka topics - Topic(s) a escuchar (puede ser
   * array) groupId - Consumer group al que pertenece este consumer
   */
  @KafkaListener(
      topics = "ecommerce.orders.placed",
      groupId = "inventory-service"
  )
  public void consumeOrderPlaced(OrderPlacedEvent event) {
    log.info("Received OrderPlacedEvent: {}", event);

    try {
      // Delegar la lógica de negocio al Service
      inventoryService.processOrderPlaced(event);

      log.info("Order processed successfully: orderId={}", event.getOrderId());
    } catch (Exception e) {
      log.error("Error processing OrderPlacedEvent: orderId={}, error={}",
          event.getOrderId(), e.getMessage(), e);
      // En este lab: Solo logging del error
      // Lab 02: Publicaremos OrderCancelledEvent si falla
    }
  }

  @KafkaListener(topics = "ecommerce.product.validated", groupId = "product-verification-group")
  public void listen(OrderPlacedEvent event) {
    verificationService.verifyProduct(event);
  }
}