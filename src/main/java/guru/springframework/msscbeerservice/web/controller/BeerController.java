package guru.springframework.msscbeerservice.web.controller;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  @RequestMapping("/{beerId}")
  public ResponseEntity getBeerById(@PathVariable UUID beerId) {
    // TODO Impl
    return ResponseEntity.ok(BeerDto.builder().build());
  }

  @PostMapping
  public ResponseEntity saveBeer(@RequestBody @Validated BeerDto beerDto)
      throws URISyntaxException {
    // TODO Impl
    return ResponseEntity.created(new URI("")).build();
  }

  @PutMapping("/{beerId}")
  public ResponseEntity updateBeerById(
      @PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) {
    // TODO Impl
    return ResponseEntity.noContent().build();
  }
}
