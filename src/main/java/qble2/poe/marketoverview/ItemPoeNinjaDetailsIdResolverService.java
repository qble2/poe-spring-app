package qble2.poe.marketoverview;

import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.item.Item;

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
      default -> resolveItemPoeNinjaDetailsIdFromNameAndBaseType(item);
    };
  }

  private boolean isElligibleToPriceCheck(Item item) {
    return item.getCategory() != null && item.getCategory() != ItemCategoryEnum.UNKNOWN
        && item.getCategory() != ItemCategoryEnum.UNIDENTIFIED;
  }

  // name
  private String resolveItemPoeNinjaDetailsIdFromName(Item item) {
    String detailsId = item.getName().trim().toLowerCase();

    return replaceWhitespaceAndQuote(detailsId);
  }

  // baseType
  private String resolveItemPoeNinjaDetailsIdFromBaseType(Item item) {
    String detailsId = item.getBaseType().trim().toLowerCase();

    return replaceWhitespaceAndQuote(detailsId);
  }

  // name (if present) + baseType
  private String resolveItemPoeNinjaDetailsIdFromNameAndBaseType(Item item) {
    String detailsId =
        Joiner.on(" ").skipNulls().join(item.getName(), item.getBaseType()).trim().toLowerCase();

    return replaceWhitespaceAndQuote(detailsId);
  }

  private String resolveItemPoeNinjaDetailsIdForSkillGem(Item item) {
    String detailsId = resolveItemPoeNinjaDetailsIdFromNameAndBaseType(item);

    Integer gemLevel = item.getGemLevel();
    if (gemLevel != null) {
      detailsId = detailsId + "-" + gemLevel;
    }

    Integer gemQuality = item.getGemQuality();
    if (gemQuality != null) {
      detailsId = detailsId + "-" + gemQuality;
    }

    if (BooleanUtils.isTrue(item.getCorrupted())) {
      detailsId = detailsId + "c";
    }

    return detailsId;
  }

  private String resolveItemPoeNinjaDetailsIdForMap(Item item) {
    String detailsId = resolveItemPoeNinjaDetailsIdFromBaseType(item);

    Integer mapTier = item.getMapTier();
    if (mapTier != null) {
      detailsId = detailsId + "-t" + mapTier;
    }

    detailsId = detailsId + "-gen-16";

    return detailsId;
  }

  private String resolveItemPoeNinjaDetailsIdForUniqueMap(Item item) {
    String detailsId = resolveItemPoeNinjaDetailsIdFromName(item);

    Integer mapTier = item.getMapTier();
    if (mapTier != null) {
      detailsId = detailsId + "-t" + mapTier;
    }

    return detailsId;
  }

  private String resolveItemPoeNinjaDetailsIdForUniqueFoilItem(Item item) {
    String detailsId = resolveItemPoeNinjaDetailsIdFromNameAndBaseType(item);
    detailsId += "-relic";

    return detailsId;
  }

  private String replaceWhitespaceAndQuote(String detailsId) {
    detailsId = detailsId.replaceAll(" ", "-");
    detailsId = detailsId.replaceAll("'", "");

    return detailsId;
  }

}
