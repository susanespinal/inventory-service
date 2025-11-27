package dev.sespinal.inventoryservice.kafka.consumer;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductServiceClient {

  private final RestTemplate restTemplate;

  @Value("${product.service.url}")
  private String productServiceUrl;

  public boolean existsById(Long productId) {
    try {
      ResponseEntity<Void> response = restTemplate.getForEntity(
          productServiceUrl + "/api/products/" + productId, Void.class
      );
      return response.getStatusCode().is2xxSuccessful();
    } catch (HttpClientErrorException.NotFound e) {
      return false;
    }
  }
}