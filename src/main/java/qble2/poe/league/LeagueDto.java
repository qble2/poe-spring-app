package qble2.poe.league;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LeagueDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("realm")
  private String realm;

  @JsonProperty("url")
  private String url;

  @JsonProperty("startAt")
  private LocalDateTime startAt;

  @JsonProperty("endAt")
  private LocalDateTime endAt;

}
