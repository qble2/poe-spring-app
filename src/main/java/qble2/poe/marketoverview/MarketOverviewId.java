package qble2.poe.marketoverview;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = false)
public class MarketOverviewId implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String detailsId;

  private String leagueId;
}
