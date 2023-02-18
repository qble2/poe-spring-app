package qble2.poe.marketoverview;

import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.item.Item;
import qble2.poe.item.ItemPropertyEnum;

@Service
@Slf4j
public class ItemPoeNinjaDetailsIdResolverService {

  public void updatePoeNinjaDetailsId(List<Item> items) {
    items.stream().forEach(this::updateItemPoeNinjaDetailsId);
  }

  public void updateItemPoeNinjaDetailsId(Item item) {
    item.setPoeNinjaDetailsId(resolveItemPoeNinjaDetailsId(item));
  }

  /////
  /////
  /////

  private String resolveItemPoeNinjaDetailsId(Item item) {
    if (!isElligibleToPriceCheck(item)) {
      log.trace(
          "item is not elligible to price check (name: {} , baseType: {} , frameType: {}) , category: {}",
          item.getName(), item.getBaseType(), item.getFrameType(), item.getCategory());
      return null;
    }

    return switch (item.getCategory()) {
      case SKILL_GEM -> resolveItemPoeNinjaDetailsIdForSkillGem(item);
      case MAP -> resolveItemPoeNinjaDetailsIdForMap(item);
      case UNIQUE_MAP -> resolveItemPoeNinjaDetailsIdForUniqueMap(item);
      case UNIQUE_FOIL_ITEM -> resolveItemPoeNinjaDetailsIdForUniqueFoilItem(item);
      default -> resolveItemPoeNinjaDetailsIdGeneric(item);
    };
  }

  private boolean isElligibleToPriceCheck(Item item) {
    return item.getCategory() != null && item.getCategory() != ItemCategoryEnum.UNKNOWN
        && item.getCategory() != ItemCategoryEnum.UNIDENTIFIED;
  }

  private String resolveItemPoeNinjaDetailsIdForSkillGem(Item item) {
    String detailsId = normalizeToPoeNinjaStandard(item.getTypeLine());

    String gemLevel = item.getPropertyValue(ItemPropertyEnum.LEVEL);
    if (gemLevel != null) {
      detailsId = detailsId + "-" + gemLevel;
    }

    String gemQuality = item.getPropertyValue(ItemPropertyEnum.QUALITY);
    if (gemQuality != null) {
      detailsId = detailsId + "-" + gemQuality;
    }

    if (BooleanUtils.isTrue(item.getCorrupted())) {
      detailsId = detailsId + "c";
    }

    return detailsId;
  }

  private String resolveItemPoeNinjaDetailsIdForMap(Item item) {
    String detailsId = normalizeToPoeNinjaStandard(item.getBaseType());

    String mapTier = item.getPropertyValue(ItemPropertyEnum.MAP_TIER);
    if (mapTier != null) {
      detailsId = detailsId + "-t" + mapTier;
    }

    detailsId = detailsId + "-gen-16";

    return detailsId;
  }

  private String resolveItemPoeNinjaDetailsIdForUniqueMap(Item item) {
    String detailsId = normalizeToPoeNinjaStandard(item.getName());

    String mapTier = item.getPropertyValue(ItemPropertyEnum.MAP_TIER);
    if (mapTier != null) {
      detailsId = detailsId + "-t" + mapTier;
    }

    return detailsId;
  }

  private String resolveItemPoeNinjaDetailsIdForUniqueFoilItem(Item item) {
    String detailsId = resolveItemPoeNinjaDetailsIdGeneric(item);
    detailsId += "-relic";

    return detailsId;
  }

  // name (if present) + baseType
  private String resolveItemPoeNinjaDetailsIdGeneric(Item item) {
    return normalizeToPoeNinjaStandard(
        Joiner.on(" ").skipNulls().join(item.getName(), item.getBaseType()));
  }

  private String normalizeToPoeNinjaStandard(String detailsId) {
    detailsId = detailsId.trim().toLowerCase();

    // maelström -> maelstrom
    detailsId = StringUtils.stripAccents(detailsId);

    detailsId = detailsId.replaceAll(" ", "-");
    detailsId = detailsId.replaceAll("'", "");

    return detailsId;
  }

}
