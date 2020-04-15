package guru.springframework.msscbeerservice.bootstrap;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner {

  private final BeerRepository repository;

  public DatabaseLoader(BeerRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadBeerObjects();
  }

  private void loadBeerObjects() {

    repository.save(
        Beer.builder()
            .beerName("Mango")
            .beerStyle("IPA")
            .minOnHand(12)
            .price(new BigDecimal("12.95"))
            .upc(301500001L)
            .build());

    repository.save(
        Beer.builder()
            .beerName("Galaxy Cat")
            .beerStyle("PALE ALE")
            .minOnHand(15)
            .price(new BigDecimal("11.75"))
            .upc(301500002L)
            .build());
  }
}
