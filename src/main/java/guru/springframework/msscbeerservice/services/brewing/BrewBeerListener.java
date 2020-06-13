package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.exception.ResourceNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {

  private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;
    private final RestTemplate restTemplate;

  @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
  public void listen(BrewBeerEvent event) {
      BeerDto beerDto = event.getBeerDto();
      Beer beer =
              beerRepository.findById(beerDto.getId()).orElseThrow(ResourceNotFoundException::new);
      beerDto.setQuantityOnHand(beer.getQuantityToBrew());
      NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
      log.debug("Brewed beer " + beer.getMinOnHand() + ": QOH: " + beerDto.getQuantityOnHand());
      // jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
      restTemplate.exchange(
              "http://localhost:8082/api/v1/beer",
              HttpMethod.POST,
              new HttpEntity<>(newInventoryEvent.getBeerDto()),
              BeerDto.class);
  }
}
