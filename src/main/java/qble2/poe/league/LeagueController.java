package qble2.poe.league;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/leagues", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeagueController {

  @Autowired
  private LeagueService leagueService;

  @GetMapping
  public List<LeagueDto> getLeagues() {
    return this.leagueService.getLeagues();
  }

  @PostMapping
  public List<LeagueDto> reloadLeagues() {
    return this.leagueService.reloadLeagues();
  }

  @PostMapping("/{leagueId}")
  public LeagueDto reloadLeague(@PathVariable(name = "leagueId", required = true) String leagueId) {
    return this.leagueService.reloadLeague(leagueId);
  }

  @GetMapping(path = "/{leagueId}")
  public LeagueDto getLeague(@PathVariable(name = "leagueId", required = true) String leagueId) {
    return this.leagueService.getLeague(leagueId);
  }

}
