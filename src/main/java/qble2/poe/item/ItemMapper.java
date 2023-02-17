package qble2.poe.item;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ItemMapper {

  @Named("toItemDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toItemDtoFromGgg")
  List<ItemDto> toDtoListFromGggList(List<ItemGgg> sourceList);

  @Named("toItemDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "leagueId", source = "league")
  @Mapping(target = "name", source = "name")
  // @Mapping(target = "typeLine", source = "typeLine")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "frameType", source = "frameType")
  @Mapping(target = "ilvl", source = "ilvl")
  @Mapping(target = "inventoryId", source = "inventoryId")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "verified", source = "verified")
  @Mapping(target = "identified", source = "identified")
  @Mapping(target = "corrupted", source = "corrupted")
  @Mapping(target = "searing", source = "searing")
  @Mapping(target = "tangled", source = "tangled")
  @Mapping(target = "fractured", source = "fractured")
  @Mapping(target = "isRelic", source = "isRelic")
  @Mapping(target = "foilVariation", source = "foilVariation")
  @Mapping(target = "w", source = "w")
  @Mapping(target = "h", source = "h")
  @Mapping(target = "x", source = "x")
  @Mapping(target = "y", source = "y")
  @Mapping(target = "descrText", source = "descrText")
  @Mapping(target = "stackSize", source = "stackSize")
  @Mapping(target = "maxStackSize", source = "maxStackSize")
  @Mapping(target = "enchantMods", source = "enchantMods")
  @Mapping(target = "implicitMods", source = "implicitMods")
  @Mapping(target = "explicitMods", source = "explicitMods")
  @Mapping(target = "craftedMods", source = "craftedMods")
  @Mapping(target = "fracturedMods", source = "fracturedMods")
  @Mapping(target = "utilityMods", source = "utilityMods")
  @Mapping(target = "flavourText", source = "flavourText")
  ItemDto toDtoFromGgg(ItemGgg source);

  /////
  /////
  /////

  @Named(value = "toItemEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toItemEntityFromDto")
  List<Item> toEntityListFromDtoList(List<ItemDto> sourceList);

  @Named(value = "toItemEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "name", source = "name")
  // @Mapping(target = "typeLine", source = "typeLine")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "frameType", source = "frameType")
  @Mapping(target = "ilvl", source = "ilvl")
  @Mapping(target = "inventoryId", source = "inventoryId")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "verified", source = "verified")
  @Mapping(target = "identified", source = "identified")
  @Mapping(target = "corrupted", source = "corrupted")
  @Mapping(target = "searing", source = "searing")
  @Mapping(target = "tangled", source = "tangled")
  @Mapping(target = "fractured", source = "fractured")
  @Mapping(target = "isRelic", source = "isRelic")
  @Mapping(target = "foilVariation", source = "foilVariation")
  @Mapping(target = "w", source = "w")
  @Mapping(target = "h", source = "h")
  @Mapping(target = "x", source = "x")
  @Mapping(target = "y", source = "y")
  @Mapping(target = "descrText", source = "descrText")
  @Mapping(target = "stackSize", source = "stackSize")
  @Mapping(target = "maxStackSize", source = "maxStackSize")
  @Mapping(target = "enchantMods", source = "enchantMods")
  @Mapping(target = "implicitMods", source = "implicitMods")
  @Mapping(target = "explicitMods", source = "explicitMods")
  @Mapping(target = "craftedMods", source = "craftedMods")
  @Mapping(target = "fracturedMods", source = "fracturedMods")
  @Mapping(target = "utilityMods", source = "utilityMods")
  @Mapping(target = "flavourText", source = "flavourText")
  // calculated fields
  @Mapping(target = "category", source = "category")
  @Mapping(target = "poeNinjaDetailsId", source = "poeNinjaDetailsId")
  @Mapping(target = "chaosValue", source = "chaosValue")
  Item toEntityFromDto(ItemDto source);

  /////
  /////
  /////

  @Named(value = "toItemDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toItemDtoFromEntity")
  List<ItemDto> toDtoListFromEntityList(List<Item> sourceList);

  @Named(value = "toItemDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "name", source = "name")
  // @Mapping(target = "typeLine", source = "typeLine")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "frameType", source = "frameType")
  @Mapping(target = "ilvl", source = "ilvl")
  @Mapping(target = "inventoryId", source = "inventoryId")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "verified", source = "verified")
  @Mapping(target = "identified", source = "identified")
  @Mapping(target = "corrupted", source = "corrupted")
  @Mapping(target = "searing", source = "searing")
  @Mapping(target = "tangled", source = "tangled")
  @Mapping(target = "fractured", source = "fractured")
  @Mapping(target = "isRelic", source = "isRelic")
  @Mapping(target = "foilVariation", source = "foilVariation")
  @Mapping(target = "w", source = "w")
  @Mapping(target = "h", source = "h")
  @Mapping(target = "x", source = "x")
  @Mapping(target = "y", source = "y")
  @Mapping(target = "descrText", source = "descrText")
  @Mapping(target = "stackSize", source = "stackSize")
  @Mapping(target = "maxStackSize", source = "maxStackSize")
  @Mapping(target = "enchantMods", source = "enchantMods")
  @Mapping(target = "implicitMods", source = "implicitMods")
  @Mapping(target = "explicitMods", source = "explicitMods")
  @Mapping(target = "craftedMods", source = "craftedMods")
  @Mapping(target = "fracturedMods", source = "fracturedMods")
  @Mapping(target = "utilityMods", source = "utilityMods")
  @Mapping(target = "flavourText", source = "flavourText")
  // calculated fields
  @Mapping(target = "category", source = "category")
  @Mapping(target = "poeNinjaDetailsId", source = "poeNinjaDetailsId")
  @Mapping(target = "chaosValue", source = "chaosValue")
  ItemDto toDtoFromEntity(Item source);

}
