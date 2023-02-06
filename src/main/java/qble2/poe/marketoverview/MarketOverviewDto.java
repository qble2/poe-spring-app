package qble2.poe.marketoverview;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MarketOverviewDto {

  @JsonProperty("detailsId")
  @EqualsAndHashCode.Include
  private String detailsId;

  // not returned by poe.ninja
  @JsonProperty("leagueId")
  @EqualsAndHashCode.Include
  private String leagueId;

  // not returned by poe.ninja
  @JsonProperty("type")
  private MarketOverviewTypePoeNinjaEnum type;

  @JsonProperty("name")
  private String name;

  @JsonProperty("baseType")
  private String baseType;

  // @JsonProperty("flavourText")
  // private String flavourText;

  @JsonProperty("stackSize")
  private Integer stackSize;

  @JsonProperty("count")
  private Integer count;

  @JsonProperty("listingCount")
  private Integer listingCount;

  @JsonProperty("chaosValue")
  private double chaosValue;

  @JsonProperty("exaltedValue")
  private Double exaltedValue;

  @JsonProperty("divineValue")
  private Double divineValue;

  @JsonProperty("icon")
  private String icon;

  // itemClass; // * ItemClass integer

  ///// conditional

  @JsonProperty("mapTier")
  private Integer mapTier;

  @JsonProperty("variant")
  private String variant;

  @JsonProperty("levelRequired")
  private Integer levelRequired;

  @JsonProperty("links")
  private Integer links;

  @JsonProperty("corrupted")
  private Boolean corrupted;

  @JsonProperty("gemLevel")
  private Integer gemLevel;

  @JsonProperty("gemQuality")
  private Integer gemQuality;

  @JsonProperty("itemType")
  private String itemType;

  @JsonProperty("mapRegion")
  private String mapRegion;

}
