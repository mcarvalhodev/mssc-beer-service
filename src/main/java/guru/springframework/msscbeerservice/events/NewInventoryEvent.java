package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {
    NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
