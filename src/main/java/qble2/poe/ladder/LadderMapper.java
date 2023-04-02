package qble2.poe.ladder;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.web.util.UriComponentsBuilder;
import qble2.poe.character.CharacterMapper;
import qble2.poe.ladder.ggg.LadderEntryGgg;
import qble2.poe.ladder.ggg.LadderGgg;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
    uses = {CharacterMapper.class})
public interface LadderMapper {

  static final String OFFICIAL_CHARACTER_PROFILE_HOST = "pathofexile.com";
  static final String ALTERNATIVE_CHARACTER_PROFILE_HOST = "poe-profile.info";

  @Named(value = "toLadderDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "total", source = "total")
  @Mapping(target = "cachedSince", source = "cachedSince")
  @Mapping(target = "entries", source = "entries",
      qualifiedByName = "toLadderEntryDtoListFromGggList")
  @Mapping(target = "leagueId", expression = "java(leagueId)")
  LadderDto toDtoFromGgg(LadderGgg source, @Context String leagueId);

  @Named(value = "toLadderEntryDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toLadderEntryDtoFromGgg")
  List<LadderEntryDto> toDtoListFromGggList(List<LadderEntryGgg> sourceList,
      @Context String leagueId);

  @Named(value = "toLadderEntryDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "rank", source = "rank")
  @Mapping(target = "public", source = "public")
  @Mapping(target = "character", source = "character", qualifiedByName = "toCharacterDtoFromGgg")
  @Mapping(target = "character.accountName", source = "account.name")
  // character.leagueId not returned by ggg ladder api
  @Mapping(target = "character.leagueId", expression = "java(leagueId)")
  @Mapping(target = "lastUpdateAt", expression = "java(java.time.ZonedDateTime.now())")
  @Mapping(target = "leagueId", expression = "java(leagueId)")
  LadderEntryDto toDtoFromGgg(LadderEntryGgg source, @Context String leagueId);

  /////
  /////
  /////

  @Named(value = "toLadderEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toLadderEntryEntityFromDto")
  List<LadderEntry> toListFromDtoList(List<LadderEntryDto> sourceList);

  @Named(value = "toLadderEntryEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "rank", source = "rank")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "public", source = "public")
  @Mapping(target = "character", source = "character", qualifiedByName = "toCharacterEntityFromDto")
  @Mapping(target = "lastUpdateAt", source = "lastUpdateAt")
  LadderEntry toEntityFromDto(LadderEntryDto source);

  /////
  /////
  /////

  @Named(value = "toLadderDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toLadderDtoFromEntity")
  List<LadderEntryDto> toDtoListFromEntityList(List<LadderEntry> sourceList);

  @Named(value = "toLadderDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "rank", source = "rank")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "public", source = "public")
  @Mapping(target = "character", source = "character", qualifiedByName = "toCharacterDtoFromEntity")
  @Mapping(target = "lastUpdateAt", source = "lastUpdateAt")
  LadderEntryDto toDtoFromEntity(LadderEntry source);

  /////
  /////
  /////

  @AfterMapping
  default void addProfileLinks(LadderEntry source, @MappingTarget LadderEntryDto target) {
    // GGG does not provide a direct link to a specific character
    String officialProfileUrl = UriComponentsBuilder.newInstance().scheme("https")
        .host(OFFICIAL_CHARACTER_PROFILE_HOST)
        .path("/account/view-profile/{accountName}/characters")
        .build(source.getCharacter().getAccountName(), source.getCharacter().getName()).toString();
    target.setOfficialProfileUrl(officialProfileUrl);

    String alternativeProfileUrl = UriComponentsBuilder.newInstance().scheme("https")
        .host(ALTERNATIVE_CHARACTER_PROFILE_HOST).path("/profile/{accountName}/{characterName}")
        .build(source.getCharacter().getAccountName(), source.getCharacter().getName()).toString();
    target.setAlternativeProfileUrl(alternativeProfileUrl);
  }

}
