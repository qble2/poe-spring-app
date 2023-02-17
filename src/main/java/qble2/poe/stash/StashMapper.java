package qble2.poe.stash;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import qble2.poe.item.ItemMapper;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface StashMapper {

  @Named(value = "toStashTabDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toStashTabDtoFromGgg")
  List<StashTabDto> toDtoListFromGggList(List<StashTabGgg> sourceList, @Context String leagueId);

  @Named(value = "toStashTabDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "index", source = "i")
  @Mapping(target = "name", source = "n")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "color",
      expression = "java(new java.awt.Color(source.getColour().getR(), source.getColour().getG(), source.getColour().getB()).getRGB())")
  @Mapping(target = "leagueId", expression = "java(leagueId)")
  StashTabDto toDtoFromGgg(StashTabGgg source, @Context String leagueId);

  /////
  /////
  /////

  @Named(value = "toStashTabEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toStashTabEntityFromDto")
  List<StashTab> toEntityListFromDtoList(List<StashTabDto> sourceList);

  @Named(value = "toStashTabEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "index", source = "index")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "color", source = "color")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "items", source = "items", qualifiedByName = "toItemEntityListFromDtoList")
  StashTab toEntityFromDto(StashTabDto source);

  /////
  /////
  /////

  @Named(value = "toStashTabDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toStashTabDtoFromEntity")
  List<StashTabDto> toDtoListFromEntityList(List<StashTab> sourceList);

  @Named(value = "toStashTabDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "index", source = "index")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "color", source = "color")
  @Mapping(target = "leagueId", source = "leagueId")
  StashTabDto toDtoFromEntity(StashTab source);

  /////
  /////
  /////

  /**
   * Map stash tab with items.
   */
  @Named(value = "toDetailedStashTabDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toDetailedStashTabDtoFromEntity")
  List<StashTabDto> toDetailedDtoListFromEntityList(List<StashTab> sourceList);

  @Named(value = "toDetailedStashTabDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "index", source = "index")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "color", source = "color")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "items", source = "items", qualifiedByName = "toItemDtoListFromEntityList")
  @Mapping(target = "reloadedAt", source = "reloadedAt")
  @Mapping(target = "priceCheckedAt", source = "priceCheckedAt")
  StashTabDto toDetailedDtoFromEntity(StashTab source);

  /////
  /////
  /////

}
