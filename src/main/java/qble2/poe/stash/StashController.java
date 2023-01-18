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

  @GetMapping("/tabs/{tabIndex}")
  public List<ItemDto> getStashTabItems(
      @PathVariable(name = "tabIndex", required = true) int tabIndex,
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "isRetrieveTabHeaders", required = false,
          defaultValue = "false") boolean isRetrieveTabHeaders) {
    return this.stashService.getStashTabItems(accountName, leagueId, tabIndex,
        isRetrieveTabHeaders);
  }

  @PostMapping("/tabs/{tabIndex}")
  public List<ItemDto> reloadStashTabItems(
      @PathVariable(name = "tabIndex", required = true) int tabIndex,
      @RequestParam(name = "accountName", required = true) String accountName,
      @RequestParam(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "isRetrieveTabHeaders", required = false,
          defaultValue = "false") boolean isRetrieveTabHeaders) {
    return this.stashService.reloadStashTabItems(accountName, leagueId, tabIndex,
        isRetrieveTabHeaders);
  }

}
