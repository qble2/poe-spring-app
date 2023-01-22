package qble2.poe.trade.ggg;

import org.apache.commons.lang3.BooleanUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import qble2.poe.trade.enums.ItemCategoryEnum;
import qble2.poe.trade.enums.ItemRarityEnum;
import qble2.poe.trade.enums.ListedSinceEnum;
import qble2.poe.trade.enums.OnlineStatusEnum;
import qble2.poe.trade.enums.SortPriceEnum;

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeSearchPayloadGgg {

  @JsonProperty("query")
  private QueryGgg query = new QueryGgg();

  @JsonProperty("sort")
  private SortGgg sort;

  /////
  /////
  /////

  public TradeSearchPayloadGgg setOnlineStatus(OnlineStatusEnum onlineStatusEnum) {
    if (onlineStatusEnum != null) {
      query.setStatus(new StringFilterValueGgg(onlineStatusEnum.getValue()));
    }
    return this;
  }

  public TradeSearchPayloadGgg setSortPrice(SortPriceEnum sortPriceEnum) {
    if (sortPriceEnum != null) {
      sort = new SortGgg(sortPriceEnum.getValue());
    }
    return this;
  }

  public TradeSearchPayloadGgg setItemName(String itemName) {
    if (itemName != null) {
      query.setName(itemName);
    }
    return this;
  }

  public TradeSearchPayloadGgg setItemType(String itemType) {
    if (itemType != null) {
      query.setType(itemType);
    }
    return this;
  }

  public TradeSearchPayloadGgg setListedSince(ListedSinceEnum listedSinceEnum) {
    if (listedSinceEnum != null) {
      getTradeFiltersFilters().setIndexed(new StringFilterValueGgg(listedSinceEnum.getValue()));
    }
    return this;
  }

  public TradeSearchPayloadGgg setCategory(ItemCategoryEnum itemCategoryEnum) {
    if (itemCategoryEnum != null) {
      getTypeFiltersFilters().setCategory(new StringFilterValueGgg(itemCategoryEnum.getValue()));
    }
    return this;
  }

  public TradeSearchPayloadGgg setRarity(ItemRarityEnum itemRarityEnum) {
    if (itemRarityEnum != null) {
      getTypeFiltersFilters().setRarity(new StringFilterValueGgg(itemRarityEnum.getValue()));
    }
    return this;
  }

  public TradeSearchPayloadGgg setIlvl(Integer min, Integer max) {
    if (min != null || max != null) {
      getMiscFiltersFilters().setIlvl(new NumericalFilterValueGgg(min, max));
    }
    return this;
  }

  public TradeSearchPayloadGgg setIdentified(Boolean isIdentified) {
    if (isIdentified != null) {
      getMiscFiltersFilters().setCorrupted(createBooleanAsStringFilter(isIdentified));
    }
    return this;
  }

  public TradeSearchPayloadGgg setCorrupted(Boolean isCorrupted) {
    if (isCorrupted != null) {
      getMiscFiltersFilters().setCorrupted(createBooleanAsStringFilter(isCorrupted));
    }
    return this;
  }

  public TradeSearchPayloadGgg setMirrored(Boolean isMirrored) {
    if (isMirrored != null) {
      getMiscFiltersFilters().setMirrored(createBooleanAsStringFilter(isMirrored));
    }
    return this;
  }

  public TradeSearchPayloadGgg setSplit(Boolean isSplit) {
    if (isSplit != null) {
      getMiscFiltersFilters().setSplit(createBooleanAsStringFilter(isSplit));
    }
    return this;
  }

  public TradeSearchPayloadGgg setVeiled(Boolean isVeiled) {
    if (isVeiled != null) {
      getMiscFiltersFilters().setVeiled(createBooleanAsStringFilter(isVeiled));
    }
    return this;
  }

  public TradeSearchPayloadGgg setCrafted(Boolean isCrafted) {
    if (isCrafted != null) {
      getMiscFiltersFilters().setCrafted(createBooleanAsStringFilter(isCrafted));
    }
    return this;
  }

  public TradeSearchPayloadGgg setFractured(Boolean isFractured) {
    if (isFractured != null) {
      getMiscFiltersFilters().setFractured(createBooleanAsStringFilter(isFractured));
    }
    return this;
  }

  public TradeSearchPayloadGgg setSynthesised(Boolean isSynthesised) {
    if (isSynthesised != null) {
      getMiscFiltersFilters().setSynthesised(createBooleanAsStringFilter(isSynthesised));
    }
    return this;
  }

  public TradeSearchPayloadGgg setSearing(Boolean isSearing) {
    if (isSearing != null) {
      getMiscFiltersFilters().setSearing(createBooleanAsStringFilter(isSearing));
    }
    return this;
  }

  public TradeSearchPayloadGgg setTangled(Boolean isTangled) {
    if (isTangled != null) {
      getMiscFiltersFilters().setTangled(createBooleanAsStringFilter(isTangled));
    }
    return this;
  }

  /////
  /////
  /////

  private MiscFiltersFiltersGgg getMiscFiltersFilters() {
    return query.getFilters().getMiscFilters().getFilters();
  }

  private TradeFiltersFiltersGgg getTradeFiltersFilters() {
    return query.getFilters().getTradeFilters().getFilters();
  }

  private TypeFiltersFiltersGgg getTypeFiltersFilters() {
    return query.getFilters().getTypeFilters().getFilters();
  }

  // that's how GGG handles trinary filters: "true" / "false" / no filter
  private StringFilterValueGgg createBooleanAsStringFilter(Boolean value) {
    return new StringFilterValueGgg(BooleanUtils.toStringTrueFalse(value).toLowerCase());
  }

  /////
  /////
  /////
  /**
   * <pre>
      {
        "query": {
            "status": {
                "option": "online"
            },
            "type": "Pig-Faced Bascinet",
            "stats": [
                {
                    "type": "count",
                    "filters": [
                        {
                            "id": "enchant.stat_939320550"
                        },
                        {
                            "id": "enchant.stat_804667127"
                        }
                    ],
                    "disabled": false,
                    "value": {
                        "min": 1
                    }
                },
                {
                    "filters": [
                        {
                            "id": "pseudo.pseudo_has_influence_count"
                        }
                    ],
                    "type": "not"
                }
            ],
            "filters": {
                "misc_filters": {
                    "filters": {
                        "corrupted": {
                            "option": "false"
                        },
                        "fractured_item": {
                            "option": "false"
                        },
                        "mirrored": {
                            "option": "false"
                        },
                        "ilvl": {
                            "min": 85
                        }
                    },
                    "disabled": false
                },
                "trade_filters": {
                    "filters": {
                        "collapse": {
                            "option": "true"
                        }
                    },
                    "disabled": false
                },
                "type_filters": {
                    "filters": {
                        "rarity": {
                            "option": "nonunique"
                        }
                    }
                }
            }
        },
        "sort": {
            "price": "asc"
        }
      }
   * </pre>
   */

}
