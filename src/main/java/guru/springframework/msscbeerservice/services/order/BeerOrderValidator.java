package guru.springframework.msscbeerservice.services.order;

import guru.springframework.msscbeerservice.brewery.model.BeerOrderDto;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
public class BeerOrderValidator implements Validator<BeerOrderDto> {

  private final BeerRepository beerRepository;

  @Override
  public boolean validate(BeerOrderDto order) {
    final AtomicInteger absent = new AtomicInteger();
    order
        .getBeerOrderLines()
        .forEach(
            beerOrderLineDto ->
                Optional.ofNullable(beerRepository.findByUpc(beerOrderLineDto.getUpc()))
                    .ifPresentOrElse(o -> {}, () -> absent.incrementAndGet()));
    return absent.get() == 0;
  }
}
