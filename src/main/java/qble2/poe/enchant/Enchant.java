package qble2.poe.enchant;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name = "Enchant")
@Table(name = "Enchant")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class Enchant {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @EqualsAndHashCode.Include
  private String id;

  // Not a unique id, because deprecated ids still exist (not cleaned up by GGG)
  @ElementCollection
  @Builder.Default
  private List<String> tradeIds = new ArrayList<>();

}
