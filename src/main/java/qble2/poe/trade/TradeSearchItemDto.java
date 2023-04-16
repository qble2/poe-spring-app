package qble2.poe.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import qble2.poe.trade.enums.ItemRarityEnum;
import qble2.poe.trade.enums.TradeSearchFilterItemCategoryEnum;

@Data
public class TradeSearchItemDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("baseType")
  private String baseType;

  @JsonProperty("category")
  private TradeSearchFilterItemCategoryEnum category;

  @JsonProperty("rarity")
  private ItemRarityEnum rarity;

  @JsonProperty("minIlvl")
  private Integer minIlvl;

  @JsonProperty("maxIlvl")
  private Integer maxIlvl;

  @JsonProperty("isIdentified")
  private Boolean isIdentified;

  @JsonProperty("isCorrupted")
  private Boolean isCorrupted;

  @JsonProperty("isMirrored")
  private Boolean isMirrored;

  @JsonProperty("isSplit")
  private Boolean isSplit;

  @JsonProperty("isVeiled")
  private Boolean isVeiled;

  @JsonProperty("isCrafted")
  private Boolean isCrafted;

  @JsonProperty("isFractured")
  private Boolean isFractured;

  @JsonProperty("isSynthesised")
  private Boolean isSynthesised;

  @JsonProperty("isSearing")
  private Boolean isSearing;

  @JsonProperty("isTangled")
  private Boolean isTangled;

}
