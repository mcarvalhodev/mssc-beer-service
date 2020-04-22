package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.ResourceNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public BeerDto getById(UUID beerId) {
    final Beer beer = beerRepository.findById(beerId).orElseThrow(ResourceNotFoundException::new);
    return toDtoFunction().apply(beer);
  }

  private Function<Beer, BeerDto> toDtoFunction() {
    return beerMapper::toDto;
  }

  private Function<BeerDto, Beer> toResourceFunction() {
    return beerMapper::toEntity;
  }

  @Override
  public BeerDto saveNewBeer(BeerDto beerDto) {
    Beer beer = beerRepository.save(toResourceFunction().apply(beerDto));
    return toDtoFunction().apply(beer);
  }

  @Override
  public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
    final Beer beer = beerRepository.findById(beerId).orElseThrow(ResourceNotFoundException::new);
    beer.setBeerName(beerDto.getBeerName());
    beer.setBeerStyle(beerDto.getBeerStyle().name());
    beer.setPrice(beerDto.getPrice());
    beer.setUpc(beerDto.getUpc());
    return toDtoFunction().apply(beerRepository.save(beer));
  }
}
