package qble2.poe.trade.ggg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiscFiltersFiltersGgg {

  @JsonProperty("ilvl")
  private NumericalFilterValueGgg ilvl;

  @JsonProperty("identified")
  private StringFilterValueGgg identified;

  @JsonProperty("corrupted")
  private StringFilterValueGgg corrupted;

  @JsonProperty("mirrored")
  private StringFilterValueGgg mirrored;

  @JsonProperty("crafted")
  private StringFilterValueGgg crafted;

  @JsonProperty("split")
  private StringFilterValueGgg split;

  @JsonProperty("veiled")
  private StringFilterValueGgg veiled;

  @JsonProperty("fractured_item")
  private StringFilterValueGgg fractured;

  @JsonProperty("synthesised_item")
  private StringFilterValueGgg synthesised;

  @JsonProperty("searing_item")
  private StringFilterValueGgg searing;

  @JsonProperty("tangled_item")
  private StringFilterValueGgg tangled;

}
