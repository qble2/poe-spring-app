package qble2.poe.stash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import qble2.poe.item.ItemDto;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StashTabDto {

  @JsonProperty("id")
  @EqualsAndHashCode.Include
  private String id;

  @JsonProperty("index")
  private int index;

  @JsonProperty("name")
  private String name;

  @JsonProperty("type")
  private String type;

  @JsonProperty("color")
  private int color;

  @JsonProperty("leagueId")
  private String leagueId;

  @JsonProperty("items")
  private List<ItemDto> items = new ArrayList<>();

  @JsonIgnore
  public String getHexColor() {
    return String.format("#%06X", (0xFFFFFF & this.color));
  }

  //

  @JsonProperty("reloadedAt")
  private ZonedDateTime reloadedAt;

  @JsonProperty("priceCheckedAt")
  private ZonedDateTime priceCheckedAt;

}
