package qble2.poe.thymeleaf;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qble2.poe.ladder.LeagueDto;
import qble2.poe.league.LeagueService;

@Controller
@RequestMapping(path = "leagues")
public class ThymeleafLeagueController {

  @Autowired
  private LeagueService leagueService;

  @GetMapping
  public String getLeagues(Model model, HttpSession session) {
    List<LeagueDto> leagues = this.leagueService.getLeagues();
    model.addAttribute("leagues", leagues);

    return "league-list";
  }

  @GetMapping(path = "/{leagueId}")
  public String getLeague(@PathVariable(name = "leagueId", required = true) String leagueId,
      Model model, HttpSession session) {
    LeagueDto league = this.leagueService.getLeague(leagueId);
    model.addAttribute("league", league);

    return "league";
  }

  @PostMapping
  public String reloadLeagues(Model model, HttpSession session) {
    List<LeagueDto> leagues = this.leagueService.reloadLeagues();
    model.addAttribute("leagues", leagues);

    return "league-list";
  }

  @PostMapping("/{leagueId}")
  public String reloadLeague(@PathVariable(name = "leagueId", required = true) String leagueId,
      Model model, HttpSession session) {
    LeagueDto league = this.leagueService.reloadLeague(leagueId);
    model.addAttribute("league", league);

    return "league";
  }

}
