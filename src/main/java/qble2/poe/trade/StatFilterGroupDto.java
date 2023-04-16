package qble2.poe.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import qble2.poe.trade.enums.StatGroupFilterTypeEnum;

@Data
public class StatFilterGroupDto {

  @JsonProperty("type")
  private StatGroupFilterTypeEnum type;

  @JsonProperty("itemMods")
  private List<StatItemModFilterDto> itemMods = new ArrayList<>();

  @JsonProperty("min")
  private Integer min;

  @JsonProperty("max")
  private Integer max;

}
