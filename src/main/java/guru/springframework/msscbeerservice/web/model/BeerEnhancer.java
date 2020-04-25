package guru.springframework.msscbeerservice.web.model;

import guru.springframework.msscbeerservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BeerEnhancer {

  private final InventoryService inventoryService;

  public BeerDto enhance(BeerDto dto) {
    dto.setQuantityOnHand(inventoryService.getOnHandInventory(dto.getId()));
    return dto;
  }
}
