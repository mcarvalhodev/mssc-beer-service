package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.config.MsscConfigurationProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

  private final MsscConfigurationProperties properties;
  private final RestTemplate restTemplate;

  @Override
  public Integer getOnHandInventory(UUID beerId) {

    log.debug("Calling inventory service...");

    ResponseEntity<List<BeerInventoryDto>> responseEntity =
        restTemplate.exchange(
            properties.getInventoryHost() + "/api/v1/beer/{beerId}/inventory",
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
  private static class BeerInventoryDto {

    private Integer quantityOnHand;
  }
}
