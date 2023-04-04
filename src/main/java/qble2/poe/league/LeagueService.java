package qble2.poe.league;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import qble2.poe.exception.LeagueNotFoundException;

@Service
@Transactional
// @AllArgsConstructor needed to be able to inject mocked dependencies for unit testing
@AllArgsConstructor
public class LeagueService {

  @Autowired
  private LeagueRepository leagueRepository;

  @Autowired
  private LeagueMapper leagueMapper;

  @Autowired
  private LeagueWebClientGgg leagueWebClientGgg;

  public List<LeagueDto> getLeagues() {
    List<League> leagues = this.leagueRepository.findAllByOrderByStartAtDesc();

    return this.leagueMapper.toDtoListFromEntityList(leagues);
  }

  public LeagueDto getLeague(@NotNull String leagueId) {
    League league = findLeagueByIdOrThrow(leagueId);

    return this.leagueMapper.toDtoFromEntity(league);
  }

  public List<LeagueDto> reloadLeagues() {
    List<LeagueDto> listOfLeagueDto = this.leagueWebClientGgg.retrieveLeagues();
    List<League> entityListFromDtoList = this.leagueMapper.toEntityListFromDtoList(listOfLeagueDto);

    // we do directly map and return the saved data, because we will perform a custom find query
    this.leagueRepository.saveAll(entityListFromDtoList);

    List<League> findAllByOrderByStartAtDesc = this.leagueRepository.findAllByOrderByStartAtDesc();

    return this.leagueMapper.toDtoListFromEntityList(findAllByOrderByStartAtDesc);
  }

  public LeagueDto reloadLeague(@NotNull String leagueId) {
    findLeagueByIdOrThrow(leagueId);

    LeagueDto leagueDto = this.leagueWebClientGgg.retrieveLeague(leagueId);
    League entityFromDto = this.leagueMapper.toEntityFromDto(leagueDto);

    entityFromDto = this.leagueRepository.save(entityFromDto);

    return this.leagueMapper.toDtoFromEntity(entityFromDto);
  }

  /////
  /////
  /////

  public League findLeagueByIdOrThrow(@NotNull String leagueId) {
    return this.leagueRepository.findById(leagueId)
        .orElseThrow(() -> new LeagueNotFoundException(leagueId));
  }

}
