package guru.springframework.msscbeerservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient("inventory-failover")
public interface InventoryFailoverFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/inventory-failover")
  ResponseEntity<List<InventoryServiceImpl.BeerInventoryDto>> getOnhandInventory(
      @PathVariable UUID beerId);
}
