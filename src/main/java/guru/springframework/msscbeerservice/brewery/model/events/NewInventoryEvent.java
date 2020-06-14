package guru.springframework.msscbeerservice.brewery.model.events;

import guru.springframework.msscbeerservice.brewery.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
