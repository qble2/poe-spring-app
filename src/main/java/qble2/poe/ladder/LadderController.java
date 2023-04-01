package qble2.poe.ladder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/ladders", produces = MediaType.APPLICATION_JSON_VALUE)
public class LadderController {

  @Autowired
  private LadderService ladderService;

  @GetMapping(path = "/leagues/{leagueId}")
  public LadderPageDto getLadder(
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "50") int size,
      @PathVariable(name = "leagueId", required = true) String leagueId) {
    Pageable pageable = PageRequest.of(page, size);

    return this.ladderService.getLadder(leagueId, pageable);
  }

  @PostMapping(path = "/leagues/{leagueId}")
  public ResponseEntity<Void> reloadLadder(
      @PathVariable(name = "leagueId", required = true) String leagueId,
      @RequestParam(name = "start", required = false, defaultValue = "0") int start,
      @RequestParam(name = "end", required = false, defaultValue = "15000") int end) {
    this.ladderService.reloadLadder(leagueId, start, end);

    return ResponseEntity.ok().build();
  }

  @PostMapping(path = "/leagues/{leagueId}/items")
  public ResponseEntity<Void> reloadLadderItems(
      @PathVariable(name = "leagueId", required = true) String leagueId) {
    this.ladderService.reloadLadderItems(leagueId);

    return ResponseEntity.ok().build();
  }

}
