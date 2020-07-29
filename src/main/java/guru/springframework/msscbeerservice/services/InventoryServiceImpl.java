package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.config.MsscConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("!local-discovery")
@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

  public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
  private final MsscConfigurationProperties properties;
  private final RestTemplate restTemplate;

  @Override
  public Integer getOnHandInventory(UUID beerId) {

    log.debug("Calling inventory service...");

    ResponseEntity<List<BeerInventoryDto>> responseEntity =
        restTemplate.exchange(
            properties.getInventoryHost() + INVENTORY_PATH,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<>() {},
            beerId);
    return Objects.requireNonNull(responseEntity.getBody()).stream()
        .mapToInt(BeerInventoryDto::getQuantityOnHand)
        .sum();
  }

  @Getter
  @Setter
  public static class BeerInventoryDto {

    private Integer quantityOnHand;
  }
}
