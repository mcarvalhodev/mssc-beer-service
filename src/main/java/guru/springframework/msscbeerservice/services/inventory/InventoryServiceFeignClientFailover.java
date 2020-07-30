package guru.springframework.msscbeerservice.services.inventory;

import guru.springframework.msscbeerservice.services.InventoryFailoverFeignClient;
import guru.springframework.msscbeerservice.services.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient {

  private final InventoryFailoverFeignClient failoverFeignClient;

  @Override
  public ResponseEntity<List<InventoryServiceImpl.BeerInventoryDto>> getOnhandInventory(
      UUID beerId) {
    return failoverFeignClient.getOnhandInventory(beerId);
  }
}
