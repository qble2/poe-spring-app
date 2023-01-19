package qble2.poe.stash;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qble2.poe.item.ItemDto;

@RestController
@RequestMapping(path = "api/stashes", produces = MediaType.APPLICATION_JSON_VALUE)
public class StashController {

  @Autowired
  private StashService stashService;

  @GetMapping(path = "/tabs")
  public List<StashTabDto> getStashTabs(
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "leagueId", required = true) String leagueId) {
    return this.stashService.getStashTabs(accountName, leagueId);
  }

  @PostMapping(path = "/tabs")
  public List<StashTabDto> reloadStashTabs(
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "leagueId", required = true) String leagueId) {
    return this.stashService.reloadStashTabs(accountName, leagueId);
  }

  @GetMapping("/tabs/{stashTabId}/items")
  public List<ItemDto> getStashTabItems(
      @PathVariable(name = "stashTabId", required = true) String stashTabId) {
    return this.stashService.getStashTabItems(stashTabId);
  }

  @PostMapping("/tabs/{stashTabId}/items")
  public List<ItemDto> reloadStashTabItems(
      @PathVariable(name = "stashTabId", required = true) String stashTabId,
      @RequestParam(name = "accountName", required = true) String accountName) {
    return this.stashService.reloadStashTabItems(stashTabId, accountName);
  }

}
