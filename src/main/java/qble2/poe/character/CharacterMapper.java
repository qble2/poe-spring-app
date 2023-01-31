package qble2.poe.character;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import qble2.poe.item.ItemMapper;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
    uses = ItemMapper.class)
public interface CharacterMapper {

  @Named(value = "toCharacterDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toCharacterDtoFromGgg")
  List<CharacterDto> toDtoListFromGggList(List<CharacterGgg> listOfCharacterGggSource,
      @Context String accountName);

  @Named(value = "toCharacterDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "accountName", expression = "java(accountName)")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "classId", source = "classId")
  @Mapping(target = "ascendancyClass", source = "ascendancyClass")
  @Mapping(target = "characterClass", source = "characterClass")
  @Mapping(target = "level", source = "level")
  @Mapping(target = "experience", source = "experience")
  CharacterDto toDtoFromGgg(CharacterGgg characterGggSource, @Context String accountName);

  /////
  /////
  /////

  @Named(value = "toCharacterEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toCharacterEntityFromDto")
  List<Character> toEntityListFromDtoList(List<CharacterDto> listOfCharacterDtoSource);

  @Named(value = "toCharacterEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "accountName", source = "accountName")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "classId", source = "classId")
  @Mapping(target = "ascendancyClass", source = "ascendancyClass")
  @Mapping(target = "characterClass", source = "characterClass")
  @Mapping(target = "level", source = "level")
  @Mapping(target = "experience", source = "experience")
  Character toEntityFromDto(CharacterDto characterDtoSource);

  /////
  /////
  /////

  @Named(value = "toCharacterDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toCharacterDtoFromEntity")
  List<CharacterDto> toDtoListFromEntityList(List<Character> listOfCharacterSource);

  @Named(value = "toCharacterDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "accountName", source = "accountName")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "classId", source = "classId")
  @Mapping(target = "ascendancyClass", source = "ascendancyClass")
  @Mapping(target = "characterClass", source = "characterClass")
  @Mapping(target = "level", source = "level")
  @Mapping(target = "experience", source = "experience")
  CharacterDto toDtoFromEntity(Character characterSource);

  /////
  /////
  /////

  @Named(value = "toDetailedCharacterDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toDetailedCharacterDtoFromEntity")
  List<CharacterDto> toDetailedDtoListFromEntityList(List<Character> listOfCharacterSource);

  /**
   * Map character with items.
   */
  @Named(value = "toDetailedCharacterDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "name")
  @Mapping(target = "accountName", source = "accountName")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "classId", source = "classId")
  @Mapping(target = "ascendancyClass", source = "ascendancyClass")
  @Mapping(target = "characterClass", source = "characterClass")
  @Mapping(target = "level", source = "level")
  @Mapping(target = "experience", source = "experience")
  @Mapping(target = "items", source = "items", qualifiedByName = "toItemDtoListFromEntityList")
  CharacterDto toDetailedDtoFromEntity(Character characterSource);

}
