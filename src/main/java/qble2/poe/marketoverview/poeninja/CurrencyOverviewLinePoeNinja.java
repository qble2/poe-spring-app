package qble2.poe.marketoverview.poeninja;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CurrencyOverviewLinePoeNinja {

  @EqualsAndHashCode.Include
  @JsonProperty("detailsId")
  private String detailsId; // mirror-of-kalandra

  @JsonProperty("currencyTypeName")
  private String currencyTypeName; // Mirror of Kalandra

  @JsonProperty("chaosEquivalent")
  private Double chaosEquivalent; // 101417.86

  // @JsonProperty("pay")
  // private Object pay;

  // @JsonProperty("receive")
  // private Object receive;

  // @JsonProperty("paySparkLine")
  // private Object paySparkLine;

  // @JsonProperty("receiveSparkLine")
  // private Object receiveSparkLine;

  // @JsonProperty("lowConfidencePaySparkLine")
  // private Object lowConfidencePaySparkLine;

  // @JsonProperty("lowConfidenceReceiveSparkLine")
  // private Object lowConfidenceReceiveSparkLine;

}
