package qble2.poe.ladder.ggg;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class LadderGgg {

  @JsonProperty("total")
  private int total;

  @JsonProperty("cachedSince")
  private Date cachedSince;

  @JsonProperty("entries")
  private List<LadderEntryGgg> entries = new ArrayList<>();

}
