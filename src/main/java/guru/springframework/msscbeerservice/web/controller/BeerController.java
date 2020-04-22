package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  private final BeerService beerService;

  @RequestMapping("/{beerId}")
  public ResponseEntity getBeerById(@PathVariable UUID beerId) {
    BeerDto dto = beerService.getById(beerId);
    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity saveBeer(@RequestBody @Validated BeerDto beerDto)
      throws URISyntaxException {
    BeerDto dto = beerService.saveNewBeer(beerDto);
    return ResponseEntity.created(new URI(dto.getId().toString())).build();
  }

  @PutMapping("/{beerId}")
  public ResponseEntity updateBeerById(
      @PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) {
    beerService.updateBeer(beerId, beerDto);
    return ResponseEntity.noContent().build();
  }
}
