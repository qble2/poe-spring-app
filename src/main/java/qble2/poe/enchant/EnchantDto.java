package qble2.poe.enchant;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EnchantDto {

  @JsonProperty("id")
  private String id;

  @JsonProperty("tradeIds")
  private List<String> tradeIds;

}
