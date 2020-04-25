package guru.springframework.msscbeerservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "mssc", ignoreUnknownFields = false)
@Component
public class MsscConfigurationProperties {

  private String inventoryHost;
}
