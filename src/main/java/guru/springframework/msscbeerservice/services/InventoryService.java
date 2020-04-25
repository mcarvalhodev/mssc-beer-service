package guru.springframework.msscbeerservice.services;

import java.util.UUID;

public interface InventoryService {

  Integer getOnHandInventory(UUID beerId);
}
