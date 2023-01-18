package qble2.poe.item;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qble2.poe.enchant.Enchant;
import qble2.poe.enchant.EnchantRepository;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ItemMapper {

  Logger log = LoggerFactory.getLogger(ItemMapper.class);

  static final String ENCHANT_REGEX = "^[^\\d+%]*((\\d+)?%)?.*$";
  static final Pattern ENCHANT_PATTERN = Pattern.compile(ENCHANT_REGEX);

  @Named("toItemDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toItemDtoFromGgg")
  List<ItemDto> toDtoListFromGggList(List<ItemGgg> listOfItemGggSource);

  @Named("toItemDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "leagueId", source = "league")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "typeLine", source = "typeLine")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "frameType", source = "frameType")
  @Mapping(target = "ilvl", source = "ilvl")
  @Mapping(target = "inventoryId", source = "inventoryId")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "verified", source = "verified")
  @Mapping(target = "identified", source = "identified")
  @Mapping(target = "searing", source = "searing")
  @Mapping(target = "tangled", source = "tangled")
  @Mapping(target = "fractured", source = "fractured")
  @Mapping(target = "w", source = "w")
  @Mapping(target = "h", source = "h")
  @Mapping(target = "x", source = "y")
  @Mapping(target = "descrText", source = "descrText")
  @Mapping(target = "enchantMods", source = "enchantMods")
  @Mapping(target = "implicitMods", source = "implicitMods")
  @Mapping(target = "explicitMods", source = "explicitMods")
  @Mapping(target = "craftedMods", source = "craftedMods")
  @Mapping(target = "fracturedMods", source = "fracturedMods")
  @Mapping(target = "utilityMods", source = "utilityMods")
  @Mapping(target = "flavourText", source = "flavourText")
  ItemDto toDtoFromGgg(ItemGgg itemGggSource);

  /////
  /////
  /////

  @Named(value = "toItemEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toItemEntityFromDto")
  List<Item> toEntityListFromDtoList(List<ItemDto> listOfItemDtoSource,
      @Context EnchantRepository enchantRepository);

  @Named(value = "toItemEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "typeLine", source = "typeLine")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "frameType", source = "frameType")
  @Mapping(target = "ilvl", source = "ilvl")
  @Mapping(target = "inventoryId", source = "inventoryId")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "verified", source = "verified")
  @Mapping(target = "identified", source = "identified")
  @Mapping(target = "searing", source = "searing")
  @Mapping(target = "tangled", source = "tangled")
  @Mapping(target = "fractured", source = "fractured")
  @Mapping(target = "w", source = "w")
  @Mapping(target = "h", source = "h")
  @Mapping(target = "x", source = "y")
  @Mapping(target = "descrText", source = "descrText")
  // @Mapping(target = "enchantMods", source = "enchantMods") // @AfterMapping
  @Mapping(target = "implicitMods", source = "implicitMods")
  @Mapping(target = "explicitMods", source = "explicitMods")
  @Mapping(target = "craftedMods", source = "craftedMods")
  @Mapping(target = "fracturedMods", source = "fracturedMods")
  @Mapping(target = "utilityMods", source = "utilityMods")
  @Mapping(target = "flavourText", source = "flavourText")
  Item toEntityFromDto(ItemDto itemDtoSource, @Context EnchantRepository enchantRepository);

  /////
  /////
  /////

  @Named(value = "toItemDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toItemDtoFromEntity")
  List<ItemDto> toDtoListFromEntityList(List<Item> listOfItemSource);

  @Named(value = "toItemDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "typeLine", source = "typeLine")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "frameType", source = "frameType")
  @Mapping(target = "ilvl", source = "ilvl")
  @Mapping(target = "inventoryId", source = "inventoryId")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "verified", source = "verified")
  @Mapping(target = "identified", source = "identified")
  @Mapping(target = "searing", source = "searing")
  @Mapping(target = "tangled", source = "tangled")
  @Mapping(target = "fractured", source = "fractured")
  @Mapping(target = "w", source = "w")
  @Mapping(target = "h", source = "h")
  @Mapping(target = "x", source = "y")
  @Mapping(target = "descrText", source = "descrText")
  // @Mapping(target = "enchantMods", source = "enchantMods") // TODO BKE
  @Mapping(target = "implicitMods", source = "implicitMods")
  @Mapping(target = "explicitMods", source = "explicitMods")
  @Mapping(target = "craftedMods", source = "craftedMods")
  @Mapping(target = "fracturedMods", source = "fracturedMods")
  @Mapping(target = "utilityMods", source = "utilityMods")
  @Mapping(target = "flavourText", source = "flavourText")
  ItemDto toDtoFromEntity(Item itemSource);

  @AfterMapping
  default void mapEnchantMods(ItemDto itemDtoSource, @MappingTarget Item target,
      @Context EnchantRepository enchantRepository) {
    CollectionUtils.emptyIfNull(itemDtoSource.getEnchantMods()).stream().forEach(enchantMod -> {
      Integer enchantValue = null;

      Matcher matcher = ENCHANT_PATTERN.matcher(enchantMod);
      if (matcher.matches()) {
        enchantValue = NumberUtils.toInt(matcher.group(2));
        enchantMod = enchantMod.replaceFirst("(\\d+)", "#");
      }

      Enchant enchant = enchantRepository.findById(enchantMod).orElse(null);
      if (enchant != null) {
        target.addEnchantMod(new ItemEnchantMod(target, enchant, enchantValue));
      } else {
        log.warn("ignored unknown enchant mod: {}", enchantMod);
      }
    });
  }
}
