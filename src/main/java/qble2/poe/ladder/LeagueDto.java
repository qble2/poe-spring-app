package qble2.poe.ladder;

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
  private String startAt;

  @JsonProperty("endAt")
  private String endAt;

}
