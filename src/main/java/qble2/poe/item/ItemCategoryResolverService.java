package qble2.poe.item;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.marketoverview.ItemCategoryEnum;

@Service
@Slf4j
public class ItemCategoryResolverService {

  public void updateItemCategory(List<Item> items) {
    items.stream().forEach(this::updateItemCategory);
  }

  public void updateItemCategory(Item item) {
    item.setCategory(resolveItemCategory(item));
  }

  /////
  /////
  /////

  private ItemCategoryEnum resolveItemCategory(Item item) {
    ItemCategoryEnum itemCategory = ItemCategoryEnum.UNKNOWN;

    if (!item.isIdentified()) {
      itemCategory = ItemCategoryEnum.UNIDENTIFIED;
    } else {
      itemCategory = switch (item.getFrameType()) {
        case 0, 1, 2 -> resolveItemCategoryForFrameType012(item);
        case 3 -> resolveItemCategoryForUniqueItem(item);
        case 4 -> ItemCategoryEnum.SKILL_GEM;
        case 5 -> resolveItemCategoryForFrameType5(item);
        case 6 -> ItemCategoryEnum.DIVINATION_CARD;
        case 7 -> ItemCategoryEnum.QUEST_ITEM;
        case 8 -> ItemCategoryEnum.PROPHECY;
        case 9, 10 -> ItemCategoryEnum.UNIQUE_FOIL_ITEM;
        default -> ItemCategoryEnum.UNKNOWN;
      };
    }

    if (itemCategory == ItemCategoryEnum.UNKNOWN) {
      log.warn("Could not resolve category for item (name: {} , baseType: {} , frameType: {})",
          item.getName(), item.getBaseType(), item.getFrameType());
    }

    return itemCategory;
  }

  private ItemCategoryEnum resolveItemCategoryForFrameType012(Item item) {
    if (item.getStackSize() != null) {
      return ItemCategoryEnum.FRAGMENT;
    }

    if (StringUtils.endsWith(item.getBaseType(), " Scarab")) {
      return ItemCategoryEnum.SCARAB;
    }

    if (StringUtils.endsWith(item.getBaseType(), " Map")) {
      return ItemCategoryEnum.MAP;
    }

    if (StringUtils.contains(item.getBaseType(), " Invitation")) {
      return ItemCategoryEnum.INVITATION;
    }

    return ItemCategoryEnum.UNKNOWN;
  }

  private ItemCategoryEnum resolveItemCategoryForUniqueItem(Item item) {
    ItemCategoryEnum categoryEnum = ItemCategoryEnum.UNIQUE_ITEM;

    if (item.getStackSize() != null) {
      return ItemCategoryEnum.FRAGMENT;
    }

    if (StringUtils.endsWith(item.getBaseType(), " Map")) {
      categoryEnum = ItemCategoryEnum.UNIQUE_MAP;
    }

    return categoryEnum;
  }

  private ItemCategoryEnum resolveItemCategoryForFrameType5(Item item) {
    ItemCategoryEnum categoryEnum = ItemCategoryEnum.CURRENCY;

    String baseType = item.getBaseType();

    if (baseType.equals("Chronicle of Atzoatl")) {
      categoryEnum = ItemCategoryEnum.MAP;
    } else if (baseType.contains("Essence of ") || "Remnant of Corruption".equals(baseType)) {
      categoryEnum = ItemCategoryEnum.ESSENCE;
    } else if (StringUtils.endsWith(baseType, " Splinter") || baseType.contains("Splinter of ")) {
      categoryEnum = ItemCategoryEnum.FRAGMENT;
    } else if (StringUtils.endsWith(baseType, " Incubator")) {
      categoryEnum = ItemCategoryEnum.INCUBATOR;
    } else if (StringUtils.endsWith(baseType, " Oil")) {
      categoryEnum = ItemCategoryEnum.OIL;
    } else if (StringUtils.endsWith(baseType, " Delirium Orb")) {
      categoryEnum = ItemCategoryEnum.DELIRIUM_ORB;
    } else if (StringUtils.endsWith(baseType, " Fossil")) {
      categoryEnum = ItemCategoryEnum.FOSSIL;
    } else if (StringUtils.endsWith(baseType, " Resonator")) {
      categoryEnum = ItemCategoryEnum.RESONATOR;
    } else if (StringUtils.endsWith(baseType, " Artifact") || baseType.equals("Burial Medallion")
        || baseType.equals("Exotic Coinage") || baseType.equals("Astragali")
        || baseType.equals("Scrap Metal")) {
      categoryEnum = ItemCategoryEnum.ARTIFACT;
    }

    return categoryEnum;
  }

}
