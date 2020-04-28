package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.exception.ResourceNotFoundException;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerEnhancer;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;
  private final BeerEnhancer enhancer;

  @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventory == false")
  @Override
  public BeerDto getById(UUID beerId, Boolean showInventory) {
    final Beer beer = beerRepository.findById(beerId).orElseThrow(ResourceNotFoundException::new);
    Function<Beer, BeerDto> action = toDtoFunction();
    if (showInventory) {
      return action.andThen(enhancer::enhance).apply(beer);
    }
    return action.apply(beer);
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

  @Override
  public List<BeerDto> getAll() {
    List<Beer> results = (List<Beer>) beerRepository.findAll();
    return results.stream().map(toDtoFunction()).collect(Collectors.toList());
  }

  @Cacheable(cacheNames = "beerListCache", condition = "#showInventory == false")
  @Override
  public BeerPagedList list(
      String beerName, BeerStyleEnum beerStyle, Boolean showInventory, PageRequest pageRequest) {

    Page<Beer> page;

    if (StringUtils.hasLength(beerName)) {
      if (!StringUtils.isEmpty(beerStyle)) {
        page = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
      } else {
        page = beerRepository.findAllByBeerName(beerName, pageRequest);
      }
    } else if (!StringUtils.isEmpty(beerStyle)) {
      page = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    } else {
      page = beerRepository.findAll(pageRequest);
    }

    Function<Beer, BeerDto> action = toDtoFunction();

    if (showInventory) {
      action = action.andThen(enhancer::enhance);
    }

    List<BeerDto> resultList = page.getContent().stream().map(action).collect(Collectors.toList());
    PageRequest of =
        PageRequest.of(page.getPageable().getPageNumber(), page.getPageable().getPageSize());
    return new BeerPagedList(resultList, of, page.getTotalElements());
  }

  @Cacheable(cacheNames = "beerUpcCache")
  @Override
  public BeerDto getBeerByUPC(Long beerUPC) {
    Beer beer = beerRepository.findByUpc(beerUPC);
    return toDtoFunction().apply(beer);
  }
}
