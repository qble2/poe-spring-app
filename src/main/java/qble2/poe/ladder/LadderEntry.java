package qble2.poe.ladder;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import qble2.poe.character.Character;

@Entity(name = "LadderEntry")
@Table(name = "LadderEntry")
@IdClass(LadderEntryId.class)
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class LadderEntry {

  @Id
  @Column(name = "rank", nullable = false)
  @EqualsAndHashCode.Include
  private int rank;

  @Id
  @Column(name = "leagueId", nullable = false)
  @EqualsAndHashCode.Include
  private String leagueId;

  @Column(name = "public")
  private boolean isPublic;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Character character;

  @Column(name = "lastUpdateAt")
  private LocalDateTime lastUpdateAt;

}
