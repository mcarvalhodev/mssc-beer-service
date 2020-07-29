package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.services.inventory.InventoryServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BeerInventoryServiceFeign implements InventoryService {

  private final InventoryServiceFeignClient feignClient;

  @Override
  public Integer getOnHandInventory(UUID beerId) {

    log.debug("Calling inventory service...");
    final ResponseEntity<List<InventoryServiceImpl.BeerInventoryDto>> responseEntity =
        feignClient.getOnhandInventory(beerId);
    return Objects.requireNonNull(responseEntity.getBody()).stream()
        .mapToInt(InventoryServiceImpl.BeerInventoryDto::getQuantityOnHand)
        .sum();
  }
}
