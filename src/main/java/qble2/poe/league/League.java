package qble2.poe.league;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name = "League")
@Table(name = "League")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class League {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @EqualsAndHashCode.Include
  private String id;

  @Column(name = "realm")
  private String realm;

  @Column(name = "url")
  private String url;

  @Column(name = "startAt")
  private ZonedDateTime startAt;

  @Column(name = "endAt")
  private ZonedDateTime endAt;

}
