package qble2.poe.league;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qble2.poe.ladder.LeagueDto;

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
    return this.leagueMapper.toDtoFromEntity(this.leagueRepository.findById(leagueId)
        .orElseThrow(() -> new RuntimeException("League does not exist")));
  }

  public List<LeagueDto> reloadLeagues() {
    List<LeagueDto> listOfLeagueDto = this.leagueWebClientGgg.retrieveLeagues();
    this.leagueRepository.saveAll(this.leagueMapper.toEntityListFromDtoList(listOfLeagueDto));

    return this.leagueMapper.toDtoListFromEntityList(this.leagueRepository.findAll());
  }

}
