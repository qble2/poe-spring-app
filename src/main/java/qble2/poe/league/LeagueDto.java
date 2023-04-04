package qble2.poe.league;

import java.time.ZonedDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LeagueDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("realm")
  private String realm;

  @JsonProperty("url")
  private String url;

  @JsonProperty("startAt")
  private ZonedDateTime startAt;

  @JsonProperty("endAt")
  private ZonedDateTime endAt;

}
