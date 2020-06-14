package guru.springframework.msscbeerservice.services.order;

import guru.springframework.msscbeerservice.brewery.model.BeerOrderDto;
import guru.springframework.msscbeerservice.brewery.model.BeerOrderLineDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class BeerOrderValidatorTest {

  @Autowired private BeerOrderValidator validator;

  @Test
  public void testValidate() {

    boolean result =
        validator.validate(
            BeerOrderDto.builder()
                .beerOrderLines(Arrays.asList(BeerOrderLineDto.builder().upc(19L).build()))
                .build());
    System.out.println(result);
  }
}
