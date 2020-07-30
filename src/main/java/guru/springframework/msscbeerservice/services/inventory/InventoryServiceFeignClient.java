package guru.springframework.msscbeerservice.services.inventory;

import guru.springframework.msscbeerservice.services.InventoryServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = InventoryServiceFeignClientFailover.class)
public interface InventoryServiceFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = InventoryServiceImpl.INVENTORY_PATH)
  ResponseEntity<List<InventoryServiceImpl.BeerInventoryDto>> getOnhandInventory(
      @PathVariable UUID beerId);
}
