package qble2.poe.league;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import qble2.poe.common.ToZonedDateTimeFromUTC;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface LeagueMapper extends ToZonedDateTimeFromUTC {

  @Named(value = "toLeagueDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toLeagueDtoFromGgg")
  List<LeagueDto> toDtoListFromGggList(List<LeagueGgg> sourceList);

  @Named(value = "toLeagueDtoFromGgg")
  @BeanMapping(ignoreByDefault = true,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "realm", source = "realm")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "startAt", source = "startAt", qualifiedByName = "toZonedDateTimeFromUTC")
  @Mapping(target = "endAt", source = "endAt", qualifiedByName = "toZonedDateTimeFromUTC")
  LeagueDto toDtoFromGgg(LeagueGgg source);

  /////
  /////
  /////

  @Named(value = "toLeagueDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toLeagueDtoFromEntity")
  List<LeagueDto> toDtoListFromEntityList(List<League> sourceList);

  @Named(value = "toLeagueDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "realm", source = "realm")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "startAt", source = "startAt")
  @Mapping(target = "endAt", source = "endAt")
  LeagueDto toDtoFromEntity(League source);

  /////
  /////
  /////

  @Named(value = "toLeagueEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toLeagueEntityFromDto")
  List<League> toEntityListFromDtoList(List<LeagueDto> sourceList);

  @Named(value = "toLeagueEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "realm", source = "realm")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "startAt", source = "startAt")
  @Mapping(target = "endAt", source = "endAt")
  League toEntityFromDto(LeagueDto source);

}
