package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.utils.MsscContants;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class InventoryServiceImplTest {

  @Autowired InventoryService inventoryService;

  @Test
  void getOnHandInventory() {
    final Integer onHandInventory = inventoryService.getOnHandInventory(MsscContants.BEER_1_UUID);
    System.out.println(onHandInventory);
  }
}
