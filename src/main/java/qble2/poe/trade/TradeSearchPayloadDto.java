package qble2.poe.trade;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import qble2.poe.trade.enums.ListedSinceEnum;
import qble2.poe.trade.enums.OnlineStatusEnum;
import qble2.poe.trade.enums.SortPriceEnum;

@Data
public class TradeSearchPayloadDto {

  @JsonProperty("leagueId")
  private String leagueId;

  @JsonProperty("onlineStatus")
  private OnlineStatusEnum onlineStatus;

  @JsonProperty("sortPrice")
  private SortPriceEnum sortPrice;

  @JsonProperty("listedSince")
  private ListedSinceEnum listedSince;

  @JsonProperty("item")
  private TradeSearchItemDto item;

  @JsonProperty("statFilterGroups")
  private List<StatFilterGroupDto> statFilterGroups = new ArrayList<>();

}
