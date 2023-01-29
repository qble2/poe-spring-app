package qble2.poe.stash;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import qble2.poe.item.ItemGgg;

@Data
public class GetStashItemsGgg {

  @JsonProperty("numTabs")
  private int numTabs;

  @JsonProperty("tabs")
  private List<StashTabGgg> tabs = new ArrayList<>();

  @JsonProperty("quadLayout")
  private boolean quadLayout;

  @JsonProperty("items")
  private List<ItemGgg> items = new ArrayList<>();

}
