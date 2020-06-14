package guru.springframework.msscbeerservice.brewery.model.events;

import guru.springframework.msscbeerservice.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateOrderRequest {

  private BeerOrderDto order;
}
