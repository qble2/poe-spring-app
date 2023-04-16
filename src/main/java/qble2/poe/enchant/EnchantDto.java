package qble2.poe.enchant;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnchantDto {

  @JsonProperty("id")
  @EqualsAndHashCode.Include
  private String id;

  @JsonProperty("tradeIds")
  private List<String> tradeIds = new ArrayList<>();

}
