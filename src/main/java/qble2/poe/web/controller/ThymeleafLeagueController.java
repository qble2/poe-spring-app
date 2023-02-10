package qble2.poe.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping
  public String getLeagues(Model model) {
    List<LeagueDto> leagues = this.leagueService.getLeagues();
    model.addAttribute("leagues", leagues);

    return "leagues";
  }

  @PostMapping
  public String reloadLeagues(Model model) {
    List<LeagueDto> leagues = this.leagueService.reloadLeagues();
    model.addAttribute("leagues", leagues);

    return "leagues";
  }

  @GetMapping(path = "/{leagueId}")
  public String getLeague(@PathVariable(name = "leagueId", required = true) String leagueId,
      Model model) {
    LeagueDto league = this.leagueService.getLeague(leagueId);
    model.addAttribute("league", league);

    return "league";
  }

  @PostMapping("/{leagueId}")
  public String reloadLeague(@PathVariable(name = "leagueId", required = true) String leagueId,
      Model model) {
    LeagueDto league = this.leagueService.reloadLeague(leagueId);
    model.addAttribute("league", league);

    return "league";
  }

  @GetMapping(path = "get-leagues-table", headers = "HX-Request")
  public String htmxGetLeaguesListFragment(Model model) {
    List<LeagueDto> leagues = this.leagueService.getLeagues();
    model.addAttribute("leagues", leagues);

    return "fragments/leagues-frags :: leagues-table";
  }

  @PostMapping(path = "reload-leagues-table", headers = "HX-Request")
  public String htmxReloadLeaguesListFragment(Model model) {
    List<LeagueDto> leagues = this.leagueService.reloadLeagues();
    model.addAttribute("leagues", leagues);

    return "fragments/leagues-frags :: leagues-table";
  }

  @GetMapping(path = "/select-league-dropdown-menu-options", headers = "HX-Request")
  public String htmxGetLeagueSelectOptionsFragment(Model model) {
    List<LeagueDto> leagues = this.leagueService.getLeagues();
    model.addAttribute("leagues", leagues);

    return "fragments/leagues-frags :: select-league-dropdown-menu-options";
  }

  @PostMapping(path = "/select-league-dropdown-menu-options", headers = "HX-Request")
  public String htmxReloadLeagueSelectOptionsFragment(Model model) {
    List<LeagueDto> leagues = this.leagueService.reloadLeagues();
    model.addAttribute("leagues", leagues);

    return "fragments/leagues-frags :: select-league-dropdown-menu-options";
  }

}
