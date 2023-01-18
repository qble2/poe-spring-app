package qble2.poe.ladder;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = false)
public class LadderEntryId implements Serializable {

  private static final long serialVersionUID = 1L;

  private int rank;

  private String leagueId;

}
