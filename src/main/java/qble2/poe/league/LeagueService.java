package qble2.poe.league;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.exception.LeagueNotFoundException;

@Service
public class LeagueService {

  @Autowired
  private LeagueRepository leagueRepository;

  @Autowired
  private LeagueWebClientGgg leagueWebClientGgg;

  @Autowired
  private LeagueMapper leagueMapper;

  public List<LeagueDto> getLeagues() {
    return this.leagueMapper
        .toDtoListFromEntityList(this.leagueRepository.findAllByOrderByStartAtDesc());
  }

  public LeagueDto getLeague(String leagueId) {
    League league = findLeagueByIdOrThrow(leagueId);

    return this.leagueMapper.toDtoFromEntity(league);
  }

  public List<LeagueDto> reloadLeagues() {
    List<LeagueDto> listOfLeagueDto = this.leagueWebClientGgg.retrieveLeagues();
    this.leagueRepository.saveAll(this.leagueMapper.toEntityListFromDtoList(listOfLeagueDto));

    return this.leagueMapper
        .toDtoListFromEntityList(this.leagueRepository.findAllByOrderByStartAtDesc());
  }

  public LeagueDto reloadLeague(String leagueId) {
    findLeagueByIdOrThrow(leagueId);

    LeagueDto leagueDto = this.leagueWebClientGgg.retrieveLeague(leagueId);
    this.leagueRepository.save(this.leagueMapper.toEntityFromDto(leagueDto));

    return this.leagueMapper.toDtoFromEntity(findLeagueByIdOrThrow(leagueId));
  }

  /////
  /////
  /////

  private League findLeagueByIdOrThrow(String leagueId) {
    return this.leagueRepository.findById(leagueId)
        .orElseThrow(() -> new LeagueNotFoundException(leagueId));
  }

}
