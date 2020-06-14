package guru.springframework.msscbeerservice.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Beer {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(length = 36, columnDefinition = "varchar", nullable = false, updatable = false)
  private UUID id;

  @Version private Long version;

  @CreationTimestamp private Timestamp createdDate;

  @UpdateTimestamp private Timestamp lastModifiedDate;

  private String beerName;
  private String beerStyle;

  @Column(unique = true)
  private Long upc;

  private BigDecimal price;

  private Integer minOnHand;
  private Integer quantityToBrew;

  public Optional optional() {
    return Optional.ofNullable(this);
  }
}
