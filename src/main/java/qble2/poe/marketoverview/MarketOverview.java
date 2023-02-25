package qble2.poe.marketoverview;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name = "MarketOverview")
@Table(name = "MarketOverview")
@IdClass(MarketOverviewId.class)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Accessors(chain = true, fluent = false)
public class MarketOverview {

  @Id
  @EqualsAndHashCode.Include
  private String detailsId;

  @Id
  @Column(name = "leagueId")
  @EqualsAndHashCode.Include
  private String leagueId;

  @Column(name = "type")
  @Convert(converter = MarketOverviewTypePoeNinjaEnumAttributeConverter.class)
  private MarketOverviewTypePoeNinjaEnum type;

  @Column(name = "name")
  private String name;

  @Column(name = "baseType")
  private String baseType;

  @Column(name = "stackSize")
  private Integer stackSize;

  @Column(name = "count")
  private Integer count;

  @Column(name = "listingCount")
  private Integer listingCount;

  @Column(name = "chaosValue")
  private double chaosValue;

  @Column(name = "exaltedValue")
  private Double exaltedValue;

  @Column(name = "divineValue")
  private Double divineValue;

  @Column(name = "icon")
  private String icon;

  ///// conditional

  @Column(name = "mapTier")
  private Integer mapTier;

  @Column(name = "variant")
  private String variant;

  @Column(name = "levelRequired")
  private Integer levelRequired;

  @Column(name = "links")
  private Integer links;

  @Column(name = "corrupted")
  private Boolean corrupted;

  @Column(name = "gemLevel")
  private Integer gemLevel;

  @Column(name = "gemQuality")
  private Integer gemQuality;

  @Column(name = "itemType")
  private String itemType;

  @Column(name = "mapRegion")
  private String mapRegion;

}
