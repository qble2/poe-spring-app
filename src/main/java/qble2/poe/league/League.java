package qble2.poe.league;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name = "League")
@Table(name = "League")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class League {

  @Id
  @EqualsAndHashCode.Include
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Column(name = "realm")
  private String realm;

  @Column(name = "url")
  private String url;

  @Column(name = "startAt")
  private String startAt;

  @Column(name = "endAt")
  private String endAt;

}
