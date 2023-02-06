package qble2.poe.marketoverview.poeninja;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemOverviewLinePoeNinja {

  // @JsonProperty("id")
  // private int id;

  @JsonProperty("detailsId")
  private String detailsId; // tainted-oil

  @JsonProperty("name")
  private String name; // Tainted Oil

  @JsonProperty("baseType")
  private String baseType; // Tainted Oil

  @JsonProperty("flavourText")
  private String flavourText;

  @JsonProperty("stackSize")
  private int stackSize;

  // @JsonProperty("itemClass")
  // private int itemClass;

  // @JsonProperty("sparkline")
  // private Object sparkline;

  // @JsonProperty("lowConfidenceSparkline")
  // private Object lowConfidenceSparkline;

  // @JsonProperty("implicitModifiers")
  // private Object[] implicitModifiers;

  // @JsonProperty("explicitModifiers")
  // private Object[] explicitModifiers;

  @JsonProperty("count")
  private int count; //

  @JsonProperty("listingCount")
  private int listingCount; // 485

  @JsonProperty("chaosValue")
  private double chaosValue; // 60.00

  @JsonProperty("exaltedValue")
  private double exaltedValue; // 2.52

  @JsonProperty("divineValue")
  private double divineValue; // 0.24

  @JsonProperty("icon")
  private String icon; // url

  ///// conditional

  @JsonProperty("mapTier")
  private Integer mapTier;

  @JsonProperty("variant")
  private String variant;

  @JsonProperty("levelRequired")
  private Integer levelRequired; // integer($int32)

  @JsonProperty("links")
  private Integer links; // integer($int32)

  @JsonProperty("corrupted")
  private Boolean corrupted; // boolean

  @JsonProperty("gemLevel")
  private Integer gemLevel; // integer($int32)

  @JsonProperty("gemQuality")
  private Integer gemQuality; // integer($int32)

  @JsonProperty("itemType")
  private String itemType; // string (Gloves/Helmet/..)

  @JsonProperty("mapRegion")
  private String mapRegion; // string;

}
