package qble2.poe.league;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import qble2.poe.ladder.LeagueDto;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface LeagueMapper {

  @Named(value = "toLeagueDtoListFromGggList")
  @IterableMapping(qualifiedByName = "toLeagueDtoFromGgg")
  List<LeagueDto> toDtoListFromGggList(List<LeagueGgg> listOfLeagueGggSource);

  @Named(value = "toLeagueDtoFromGgg")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "realm", source = "realm")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "startAt", source = "startAt")
  @Mapping(target = "endAt", source = "endAt")
  LeagueDto toDtoFromGgg(LeagueGgg leagueGggSource);

  /////
  /////
  /////

  @Named(value = "toLeagueDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toLeagueDtoFromEntity")
  List<LeagueDto> toDtoListFromEntityList(List<League> listOfLeagueSource);

  @Named(value = "toLeagueDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "realm", source = "realm")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "startAt", source = "startAt")
  @Mapping(target = "endAt", source = "endAt")
  LeagueDto toDtoFromEntity(League leagueSource);

  /////
  /////
  /////

  @Named(value = "toLeagueEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toLeagueEntityFromDto")
  List<League> toEntityListFromDtoList(List<LeagueDto> listOfLeagueDtoSource);

  @Named(value = "toLeagueEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "realm", source = "realm")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "startAt", source = "startAt")
  @Mapping(target = "endAt", source = "endAt")
  League toEntityFromDto(LeagueDto leagueDtoSource);

}
