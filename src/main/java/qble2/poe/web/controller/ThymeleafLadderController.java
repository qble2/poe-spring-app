package qble2.poe.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.ladder.LadderPageDto;
import qble2.poe.ladder.LadderService;
import qble2.poe.league.LeagueService;
import qble2.poe.web.ThymeleafLadderForm;

@Controller
@RequestMapping(path = "ladders")
public class ThymeleafLadderController {

  @Autowired
  private LadderService ladderService;

  @Autowired
  private LeagueService leagueService;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping
  public String getLadder(@ModelAttribute("ladderPage") LadderPageDto ladderPage,
      @ModelAttribute("ladderForm") ThymeleafLadderForm thymeleafLadderForm, Model model) {
    model.addAttribute("leagues", this.leagueService.getLeagues());

    return "ladder";
  }

  @PostMapping
  public String filterLadder(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size,
      @RequestParam(name = "sortBy", required = false, defaultValue = "rank") String sortBy,
      @ModelAttribute("ladderForm") ThymeleafLadderForm thymeleafLadderForm, Model model) {
    model.addAttribute("leagues", this.leagueService.getLeagues());

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    LadderPageDto ladderPageDto =
        ladderService.getLadderBySpecification(pageable, thymeleafLadderForm);
    model.addAttribute("ladderPage", ladderPageDto);

    return "ladder";
  }

}
