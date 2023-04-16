package qble2.poe.ladder;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class LadderDto {

  @JsonProperty("leagueId")
  private String leagueId;

  @JsonProperty("total")
  private int total;

  @JsonProperty("cachedSince")
  private Date cachedSince;

  @JsonProperty("entries")
  private List<LadderEntryDto> entries = new ArrayList<>();

}
