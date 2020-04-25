package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  private static final Integer DEFAULT_PAGE_SIZE = 25;
  private static final Integer DEFAULT_PAGE_NUMBER = 0;

  private final BeerService beerService;

  @GetMapping(produces = {"application/json"})
  public ResponseEntity list(
      @RequestParam(required = false) Integer pageNumber,
      @RequestParam(required = false) Integer pageSize,
      @RequestParam(required = false) String beerName,
      @RequestParam(required = false) BeerStyleEnum beerStyle,
      @RequestParam(required = false, defaultValue = "false") Boolean showInventory) {

    if (pageNumber == null || pageNumber < 0) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }

    if (pageSize == null || pageSize < 1) {
      pageSize = DEFAULT_PAGE_SIZE;
    }

    BeerPagedList beerList =
        beerService.list(beerName, beerStyle, showInventory, PageRequest.of(pageNumber, pageSize));
    return ResponseEntity.ok(beerList);
  }

  @RequestMapping("/{beerId}")
  public ResponseEntity getBeerById(
      @PathVariable UUID beerId,
      @RequestParam(required = false, defaultValue = "false") Boolean showInventory) {
    BeerDto dto = beerService.getById(beerId, showInventory);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity saveBeer(@RequestBody @Validated BeerDto beerDto) {
    BeerDto dto = beerService.saveNewBeer(beerDto);
    return new ResponseEntity(dto, HttpStatus.CREATED);
  }

  @PutMapping("/{beerId}")
  public ResponseEntity updateBeerById(
      @PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) {
    beerService.updateBeer(beerId, beerDto);
    return ResponseEntity.noContent().build();
  }
}
